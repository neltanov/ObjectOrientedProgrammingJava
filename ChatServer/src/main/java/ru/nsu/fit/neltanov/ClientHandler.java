package ru.nsu.fit.neltanov;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Objects;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private static final int TIMEOUT = 10000;

    private final List<Message> messageHistory;
    private final List<ClientHandler> clientList;

    private ObjectOutputStream outputStream;

    public ClientHandler(Socket clientSocket, List<Message> messageHistory, List<ClientHandler> clientList) {
        this.clientSocket = clientSocket;
        this.messageHistory = messageHistory;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        try (ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream())) {

            this.outputStream = writer;

            sendHistoryToClient();

            while (true) {
                clientSocket.setSoTimeout(TIMEOUT);
                Message message = (Message) reader.readObject();
                if (Objects.equals(message.getMessage(), "Heartbeat")) {
                    continue;
                }
                System.out.println(message.getSender() + ": " + message.getMessage());

                addToMessageHistory(message);
                broadcastMessage(message);
            }
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
        catch (SocketTimeoutException e) {
            System.out.println("The timeout has been exceeded. Disabling the client "
                    + clientSocket.getLocalSocketAddress());
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            disconnectClient();
        }
    }

    private void sendHistoryToClient() throws IOException {
        synchronized (messageHistory) {
            for (Message message : messageHistory) {
                outputStream.writeObject(message);
                outputStream.flush();
            }
        }
    }

    private void addToMessageHistory(Message message) {
        synchronized (messageHistory) {
            messageHistory.add(message);
            if (messageHistory.size() > 20) {
                messageHistory.remove(0);
            }
        }
    }

    private void broadcastMessage(Message message) throws IOException {
        synchronized (clientList) {
            for (ClientHandler client : clientList) {
                    client.sendMessage(message);
            }
        }
    }

    public void sendMessage(Message message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    private void disconnectClient() {
        try {
            System.out.println(clientSocket.getLocalSocketAddress()
                    + ":" + clientSocket.getPort() + " disconnected.");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            synchronized (clientList) {
                clientList.remove(this);
            }
        }
    }
}
