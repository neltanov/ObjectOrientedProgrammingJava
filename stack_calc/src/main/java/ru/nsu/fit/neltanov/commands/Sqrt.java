package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

public class Sqrt implements Command {
    @Override
    public void execute(ExecutionContext context) {
        try {
            Double a = context.popNumberFromStack();
            if (a < 0) {
                throw new ArithmeticException();
            }
            context.pushNumberToStack(Math.sqrt(a));
        }
        catch (ArithmeticException e) {
            System.out.println("The number must be positive!");
        }
    }
}
