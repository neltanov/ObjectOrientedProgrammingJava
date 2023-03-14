package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

import java.util.EmptyStackException;

public class Multiply implements Command {
    @Override
    public void execute(ExecutionContext context) {
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
