package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

public class Divide implements Command {
    @Override
    public void execute(ExecutionContext context) {
        Double a = context.popNumberFromStack();
        Double b = context.popNumberFromStack();
        try {
            context.pushNumberToStack(a / b);
        }
        catch (ArithmeticException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
