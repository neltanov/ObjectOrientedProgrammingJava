package ru.nsu.fit.neltanov.main.java.factory;

import ru.nsu.fit.neltanov.main.java.commands.Commands;
import ru.nsu.fit.neltanov.main.java.commands.Push;

public class PushCreator extends Creator {
    @Override
    public Commands createCommand() {
        return new Push();
    }
}
