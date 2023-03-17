package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

public class Pop implements Command {
    @Override
    public void execute(ExecutionContext context) {
        context.popNumberFromStack();
    }
}
