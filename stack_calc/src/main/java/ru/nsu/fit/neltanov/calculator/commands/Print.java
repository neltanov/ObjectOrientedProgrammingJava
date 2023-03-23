package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArithmeticArgumentsException;

import java.util.EmptyStackException;
import java.util.List;

public class Print implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments)
            throws InvalidCountOfArithmeticArgumentsException, EmptyStackException {
        if (arguments.size() != 1) {
            throw new InvalidCountOfArithmeticArgumentsException();
        }
        System.out.println("Current value on top of the stack: " + context.getNumberFromStack());
    }
}
