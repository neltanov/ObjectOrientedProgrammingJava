package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

public interface Command {
    void execute(ExecutionContext context);
}
