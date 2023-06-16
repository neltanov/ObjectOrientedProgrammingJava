package ru.nsu.fit.neltanov;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatController {
    private ChatClient model;
    private ChatClientGUI view;

    public ChatController(ChatClient model, ChatClientGUI view) {
        this.model = model;
        this.view = view;

        model.setMessageListener(new MessageListener() {
            @Override
            public void onMessageReceived(Message message) {
                view.displayMessage(message.getSender() + ": " + message.getMessage());
            }
        });
        // Установка слушателя на кнопку отправки сообщения
        view.getSendButton().addActionListener(new SendButtonListener());
    }

    public void start() {
        view.show();
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = view.getMessageInput();
            model.sendMessage(new Message(model.getClientName(), message));
            view.clearMessageInput();
        }
    }
}
