package ru.nsu.fit.neltanov;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ChatClient {
    public ChatClient() {
        try (
                Socket clientSocket = new Socket(InetAddress.getLocalHost(), 21209);
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String input;
            while ((input = inputReader.readLine()) != null) {
                writer.println(input);
                String response = reader.readLine();
                System.out.println("Server response: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
