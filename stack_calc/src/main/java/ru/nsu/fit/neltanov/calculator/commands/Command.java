package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

import java.util.List;

public interface Command {
    void execute(ExecutionContext context, List<String> arguments);
}
