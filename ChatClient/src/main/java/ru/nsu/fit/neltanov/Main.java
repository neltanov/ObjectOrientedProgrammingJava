package ru.nsu.fit.neltanov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {
            ChatClient client = new ChatClient(inputReader.readLine());
            client.start(InetAddress.getLocalHost(), 21209);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}