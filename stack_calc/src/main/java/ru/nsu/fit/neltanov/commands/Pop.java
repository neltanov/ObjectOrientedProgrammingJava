package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

public class Pop implements Command {
    @Override
    public void execute(ExecutionContext context) {
        context.popNumberFromStack();
    }
}
