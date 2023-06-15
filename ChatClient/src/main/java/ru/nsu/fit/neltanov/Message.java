package ru.nsu.fit.neltanov;

import java.io.Serializable;

public class Message implements Serializable {
    private final String sender;
    private final String text;

    Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getMessage() {
        return text;
    }
    public String getSender() {
        return sender;
    }
}
