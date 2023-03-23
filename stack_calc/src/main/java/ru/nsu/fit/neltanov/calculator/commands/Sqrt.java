package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArithmeticArgumentsException;

import java.util.EmptyStackException;
import java.util.List;

public class Sqrt implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments)
            throws InvalidCountOfArithmeticArgumentsException, EmptyStackException, ArithmeticException {
        if (arguments.size() != 1) {
            throw new InvalidCountOfArithmeticArgumentsException();
        }
        Double a = context.popNumberFromStack();
        if (a < 0) {
            throw new ArithmeticException();
        }
        context.pushNumberToStack(Math.sqrt(a));
    }
}
