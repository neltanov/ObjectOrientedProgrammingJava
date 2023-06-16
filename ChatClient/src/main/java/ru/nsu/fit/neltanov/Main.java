package ru.nsu.fit.neltanov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {
            RegistrationWindow registrationWindow = new RegistrationWindow();

            ChatClient client = new ChatClient(inputReader.readLine());
            ChatClientGUI clientGUI = new ChatClientGUI();
            ChatController controller = new ChatController(client, clientGUI);
            client.start(InetAddress.getLocalHost(), 21209);
            controller.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}