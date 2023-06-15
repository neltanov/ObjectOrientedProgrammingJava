package ru.nsu.fit.neltanov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChatServer {
    private final int port;

    private static volatile boolean isRunning = true;
    private static final List<Message> messageHistory = new ArrayList<>();
    private static final List<ClientHandler> clientList = new ArrayList<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 10,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("The server is running...");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                isRunning = false;
                executorService.shutdown();
                System.out.println("Server has been stopped.");
            }));

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client " + clientSocket.getLocalSocketAddress() + " has been connected to server");

                ClientHandler clientHandler = new ClientHandler(clientSocket, messageHistory, clientList);
                clientList.add(clientHandler);
                executorService.execute(clientHandler);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
