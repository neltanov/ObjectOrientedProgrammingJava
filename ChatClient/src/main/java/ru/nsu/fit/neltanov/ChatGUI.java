package ru.nsu.fit.neltanov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatGUI {

    private JFrame loginFrame;
    private JFrame chatFrame;
    private JTextField serverIpField;
    private JTextField portField;
    private JTextField nicknameField;
    private JTextArea chatArea;
    private JTextField messageField;

    private ChatClient client;

    public ChatGUI() {
        // Создание и настройка окна регистрации
        loginFrame = new JFrame("Chat Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(4, 2));

        JLabel serverIpLabel = new JLabel("Server IP:");
        serverIpField = new JTextField();
        JLabel portLabel = new JLabel("Port:");
        portField = new JTextField();
        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InetAddress serverIp = InetAddress.getByName(serverIpField.getText());
                    int port = Integer.parseInt(portField.getText());
                    String nickname = nicknameField.getText();

                    login(serverIp, port, nickname);
                } catch (UnknownHostException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        loginFrame.add(serverIpLabel);
        loginFrame.add(serverIpField);
        loginFrame.add(portLabel);
        loginFrame.add(portField);
        loginFrame.add(nicknameLabel);
        loginFrame.add(nicknameField);
        loginFrame.add(loginButton);
        loginFrame.setVisible(true);

        // Создание и настройка окна чата
        chatFrame = new JFrame("Chat");
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setSize(400, 400);
        chatFrame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                client.sendMessage(new Message(client.getClientName(), message));
                messageField.setText("");
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatFrame.add(scrollPane, BorderLayout.CENTER);
        chatFrame.add(inputPanel, BorderLayout.SOUTH);
    }

    private void login(InetAddress serverIp, int port, String nickname) {
        System.out.println("Connected to server: " + serverIp + ":" + port);
        System.out.println("Nickname: " + nickname);

        client = new ChatClient(nickname);
        loginFrame.setVisible(false);
        chatFrame.setVisible(true);
        client.start(serverIp, port);
        client.setMessageListener(new MessageListener() {
            @Override
            public void onMessageReceived(Message message) {
                sendMessage(message.getSender() + ": " + message.getMessage());
            }
        });
    }

    public void sendMessage(String message) {
        // Здесь можно добавить логику отправки сообщения на сервер
        // В данном примере, просто добавляем сообщение в окно чата
        chatArea.append(message + "\n");
    }
}