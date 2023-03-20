package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfOneArgumentException;

import java.util.List;

public class Push implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) {
        try {
            if (arguments.size() != 2) {
                throw new InvalidCountOfOneArgumentException();
            }
            String pushValue = arguments.get(1);
            Double value;
            if ((value = context.getParameterValue(pushValue)) != null) {
                context.pushNumberToStack(value);
            }
            else {
                context.pushNumberToStack(Double.valueOf(arguments.get(1)));
            }
        }
        catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (InvalidCountOfOneArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
