package ru.nsu.fit.neltanov;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;

public class ChatClient {
    private final String clientName;
    private static final int TIMEOUT = 5000;

    public ChatClient(String clientName) {
        this.clientName = clientName;
    }

    public void start(InetAddress serverAddress, int serverPort) {
        try (
                Socket clientSocket = new Socket(serverAddress, serverPort);
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new HeartbeatTask(writer, clientName), TIMEOUT, TIMEOUT);

            String stringMessage;
            while ((stringMessage = inputReader.readLine()) != null) {
                writer.writeObject(new Message(clientName, stringMessage));
                writer.flush();

                Message response = (Message) reader.readObject();
                System.out.println("Server response from " + response.getSender() + ": " + response.getMessage());

                timer.cancel();
                timer = new Timer();
                timer.scheduleAtFixedRate(new HeartbeatTask(writer, clientName), TIMEOUT, TIMEOUT);
            }
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
