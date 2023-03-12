package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

public interface Command {
    void execute(ExecutionContext context);
}
