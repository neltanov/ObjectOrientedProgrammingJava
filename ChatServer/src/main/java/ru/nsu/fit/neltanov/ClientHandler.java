package ru.nsu.fit.neltanov;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private static final int TIMEOUT = 10000;

    private final List<Message> messageHistory;
    private final Map<ClientHandler, String> clientList;

    private ObjectOutputStream outputStream;

    public ClientHandler(Socket clientSocket, List<Message> messageHistory, Map<ClientHandler, String> clientList) {
        this.clientSocket = clientSocket;
        this.messageHistory = messageHistory;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        try (ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream())) {

            this.outputStream = writer;

            Message authMessage = (Message) reader.readObject();
            clientList.put(this, authMessage.getSender());

            sendHistoryToClient();
            Message joinMessage = new Message("Server", clientList.get(this) + " joined the chat");
            addToMessageHistory(joinMessage);
            broadcastMessage(joinMessage);

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
            disconnectClient();
        }
        catch (SocketTimeoutException e) {
            disconnectClient();
            System.out.println("The timeout has been exceeded. Disabling the client "
                    + clientSocket.getLocalSocketAddress());
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
//            disconnectClient();
        }
    }

    private void sendHistoryToClient() throws IOException {
        synchronized (messageHistory) {
            for (Message message : messageHistory) {
                sendMessage(message);
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
            for (ClientHandler client : clientList.keySet()) {
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
            broadcastMessage(new Message("Server", clientList.get(this) + " disconnected."));
//            clientSocket.close();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            synchronized (clientList) {
                clientList.remove(this);
            }
        }
    }
}
