package ru.nsu.fit.neltanov;

public class Main {
    public static void main(String[] args) {
        ChatServer server = new ChatServer(21209);
        server.start();
    }
}