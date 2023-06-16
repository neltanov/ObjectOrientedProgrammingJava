package ru.nsu.fit.neltanov;

import javax.swing.*;
import java.awt.*;

public class ChatClientGUI {
    private JFrame frame;
    private JTextField messageInput;
    private JTextArea chatArea;
    private JButton sendButton;

    public ChatClientGUI() {
        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        messageInput = new JTextField();
        inputPanel.add(messageInput, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        inputPanel.add(sendButton, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
//        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void show() {
        frame.setVisible(true);
    }

    public String getMessageInput() {
        return messageInput.getText();
    }

    public void clearMessageInput() {
        messageInput.setText("");
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void displayMessage(String message) {
        chatArea.append(message + "\n");
    }
}
