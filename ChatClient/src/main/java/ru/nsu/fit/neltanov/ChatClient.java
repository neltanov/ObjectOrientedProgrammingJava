package ru.nsu.fit.neltanov;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Timer;

public class ChatClient {
    private static boolean isRunning = true;
    private final String clientName;
    private static final int TIMEOUT = 5000;

    public ChatClient(String clientName) {
        this.clientName = clientName;
    }

    public void start(InetAddress serverAddress, int serverPort) {
        try (
                Socket clientSocket = new Socket(serverAddress, serverPort);
                Scanner scanner = new Scanner(System.in);
                ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream())
        ) {

            Thread serverReaderThread = new Thread(() -> {
                try {
                    Message message;
                    while (isRunning) {
                        message = (Message) reader.readObject();
                        System.out.println(message.getSender() + ": " + message.getMessage());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            serverReaderThread.start();

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new HeartbeatTask(writer, clientName), TIMEOUT, TIMEOUT);

            String stringMessage;
            while (isRunning) {
                stringMessage = scanner.nextLine();

                if ("exit".equals(stringMessage)) {
                    isRunning = false;
                }

                writer.writeObject(new Message(clientName, stringMessage));
                writer.flush();

                timer.cancel();
                timer = new Timer();
                timer.scheduleAtFixedRate(new HeartbeatTask(writer, clientName), TIMEOUT, TIMEOUT);
            }
            timer.cancel();
            serverReaderThread.join();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
