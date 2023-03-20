package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

import java.util.EmptyStackException;
import java.util.List;

public class Multiply implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) {
        try {
            Double a = context.popNumberFromStack();
            Double b = context.popNumberFromStack();
            context.pushNumberToStack(a * b);
        }
        catch (EmptyStackException e) {
            System.out.println(e.getMessage());
        }

    }
}
