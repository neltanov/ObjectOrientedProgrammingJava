package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.DivisionByZeroException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArgumentsException;

import java.util.EmptyStackException;
import java.util.List;

public class Divide implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments)
            throws InvalidCountOfArgumentsException, ArithmeticException, EmptyStackException {
        if (arguments.size() != 0) {
            throw new InvalidCountOfArgumentsException(0);
        }
        Double a = context.popNumberFromStack();
        Double b = context.popNumberFromStack();
        Double res = a / b;
        if (res.isInfinite()) {
            throw new DivisionByZeroException();
        }
        context.pushNumberToStack(res);
    }
}
