package ru.nsu.fit.neltanov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationWindow extends JFrame {
    private JTextField serverIpField;
    private JTextField portField;
    private JTextField nicknameField;

    public RegistrationWindow() {
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel serverIpLabel = new JLabel("Server IP:");
        serverIpField = new JTextField();
        JLabel portLabel = new JLabel("Port:");
        portField = new JTextField();
        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField();

        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String serverIp = serverIpField.getText();
                int port = Integer.parseInt(portField.getText());
                String nickname = nicknameField.getText();

                // Здесь можно выполнить логику регистрации, например, передать значения в модель

                dispose();
            }
        });

        panel.add(serverIpLabel);
        panel.add(serverIpField);
        panel.add(portLabel);
        panel.add(portField);
        panel.add(nicknameLabel);
        panel.add(nicknameField);
        panel.add(registerButton);

        getContentPane().add(panel);

        setVisible(true);
    }
}
