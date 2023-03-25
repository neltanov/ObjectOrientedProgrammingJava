package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfOneArgumentException;

import java.util.List;

public class Push implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments)
            throws InvalidCountOfOneArgumentException, NumberFormatException {
        if (arguments.size() != 1) {
            throw new InvalidCountOfOneArgumentException();
        }
        String pushValue = arguments.get(0);
        Double value;
        if ((value = context.getParameterValue(pushValue)) != null) {
            context.pushNumberToStack(value);
        } else {
            context.pushNumberToStack(Double.valueOf(pushValue));
        }
    }
}
