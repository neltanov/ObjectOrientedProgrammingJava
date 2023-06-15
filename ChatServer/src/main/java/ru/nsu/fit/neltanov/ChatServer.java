package ru.nsu.fit.neltanov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChatServer {
    private static volatile boolean isRunning = true;

    public ChatServer() {}

    public void start() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 10,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        try (ServerSocket serverSocket = new ServerSocket(21209)) {
            System.out.println("The server is running...");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                isRunning = false;
                executorService.shutdown();
                System.out.println("Server has been stopped.");
            }));

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client " + clientSocket.getLocalSocketAddress() + " has been connected to server");
                executorService.execute(new ClientHandler(clientSocket));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
