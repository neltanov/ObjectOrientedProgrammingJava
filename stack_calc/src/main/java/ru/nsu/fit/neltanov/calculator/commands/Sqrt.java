package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.NegativeSquareRootException;

import java.util.EmptyStackException;
import java.util.List;

public class Sqrt implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments)
            throws InvalidCountOfArgumentsException, EmptyStackException, ArithmeticException {
        if (arguments.size() != 0) {
            throw new InvalidCountOfArgumentsException(0);
        }
        Double a = context.popNumberFromStack();
        if (a < 0) {
            throw new NegativeSquareRootException();
        }
        context.pushNumberToStack(Math.sqrt(a));
    }
}
