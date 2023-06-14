package ru.nsu.fit.neltanov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private PrintWriter writer;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            writer = new PrintWriter(clientSocket.getOutputStream());
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Client: " + line);
                sendMessageEveryone(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Client has been disconnected");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void sendMessageEveryone(String message) {
        writer.println(message);
    }
}
