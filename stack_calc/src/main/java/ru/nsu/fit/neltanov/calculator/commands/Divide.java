package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

import java.util.EmptyStackException;
import java.util.List;

public class Divide implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) {
        try {
            Double a = context.popNumberFromStack();
            Double b = context.popNumberFromStack();
            Double res = a / b;
            if (res.isInfinite()) {
                throw new ArithmeticException();
            }
            context.pushNumberToStack(res);
        }
        catch (ArithmeticException e) {
            System.out.println("Division by zero!");
        }
        catch (EmptyStackException e) {
            System.out.println(e.getMessage());
        }
    }
}
