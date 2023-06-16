package ru.nsu.fit.neltanov;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Timer;

public class ChatClient {
    private MessageListener messageListener;
    private boolean isRunning = true;
    private final String clientName;
    private static final int TIMEOUT = 5000;

    private ObjectOutputStream outputStream;

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
            this.outputStream = writer;

            Thread serverReaderThread = new Thread(() -> {
                try {
                    Message message;
                    while (!Thread.currentThread().isInterrupted()) {
                        message = (Message) reader.readObject();
                        notifyMessageListener(message);
                        System.out.println(message.getSender() + ": " + message.getMessage());
                    }
                } catch (EOFException e) {
                    System.out.println("Reader thread was ended");
                }
                catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            serverReaderThread.start();

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new HeartbeatTask(writer, clientName), TIMEOUT, TIMEOUT);

            sendMessage(new Message(clientName, "Auth message"));

            String stringMessage;
            while (true) {
                stringMessage = scanner.nextLine();
                if ("exit".equals(stringMessage)) {
                    serverReaderThread.interrupt();
                    break;
                }

                sendMessage(new Message(clientName, stringMessage));

                timer.cancel();
                timer = new Timer();
                timer.scheduleAtFixedRate(new HeartbeatTask(writer, clientName), TIMEOUT, TIMEOUT);
            }
            timer.cancel();
//            serverReaderThread.interrupt();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getClientName() {
        return clientName;
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    private void notifyMessageListener(Message message) {
        if (messageListener != null) {
            messageListener.onMessageReceived(message);
        }
    }
}
