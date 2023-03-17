package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

public class Substract implements Command {
    @Override
    public void execute(ExecutionContext context) {
        Double a = context.popNumberFromStack();
        Double b = context.popNumberFromStack();
        context.pushNumberToStack(a - b);
    }
}
