package ru.nsu.fit.neltanov;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private static final int TIMEOUT = 10000;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream())) {
            while (true) {
                clientSocket.setSoTimeout(TIMEOUT);
                Message message = (Message) reader.readObject();
                System.out.println("Client " + message.getSender() + ": " + message.getMessage());
                writer.writeObject(message);
                writer.flush();
            }
        } catch (SocketTimeoutException e) {
            System.out.println("The timeout has been exceeded. Disabling the client "
                    + clientSocket.getLocalSocketAddress());

        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Client " + clientSocket.getLocalSocketAddress() + " has been disconnected");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
