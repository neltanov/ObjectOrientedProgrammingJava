package ru.nsu.fit.neltanov;

import java.io.Serializable;

public class Message implements Serializable {
    private final String text;

    Message(String text) {
        this.text = text;
    }

    public String getMessage() {
        return text;
    }
}
