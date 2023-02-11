package ru.nsu.fit.neltanov.main.java.factory;

import ru.nsu.fit.neltanov.main.java.commands.Commands;
import ru.nsu.fit.neltanov.main.java.commands.Plus;

public class PlusCreator extends Creator {
    @Override
    public Commands createCommand() {
        return new Plus();
    }
}
