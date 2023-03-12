package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

public class Sqrt implements Command {
    @Override
    public void execute(ExecutionContext context) {
        Double a = context.popNumberFromStack();
        context.pushNumberToStack(Math.sqrt(a));
    }
}
