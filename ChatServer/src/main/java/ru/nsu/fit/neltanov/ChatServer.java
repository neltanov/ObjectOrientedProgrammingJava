package ru.nsu.fit.neltanov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChatServer {
    public ChatServer() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 10,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        try (ServerSocket serverSocket = new ServerSocket(21209)) {
            System.out.println("The server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client has been connected to server");
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
