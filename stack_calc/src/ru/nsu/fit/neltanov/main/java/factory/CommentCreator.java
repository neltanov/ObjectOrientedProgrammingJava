package ru.nsu.fit.neltanov.main.java.factory;

import ru.nsu.fit.neltanov.main.java.commands.Commands;
import ru.nsu.fit.neltanov.main.java.commands.Comment;

public class CommentCreator extends Creator {
    @Override
    public Commands createCommand() {
        return new Comment();
    }
}
