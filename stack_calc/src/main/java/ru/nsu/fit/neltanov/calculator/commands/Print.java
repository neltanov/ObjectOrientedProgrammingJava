package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArgumentsException;

import java.util.EmptyStackException;
import java.util.List;

public class Print implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments)
            throws InvalidCountOfArgumentsException, EmptyStackException {
        if (arguments.size() != 0) {
            throw new InvalidCountOfArgumentsException(0);
        }
        System.out.println("Current value on top of the stack: " + context.getNumberFromStack());
    }
}
