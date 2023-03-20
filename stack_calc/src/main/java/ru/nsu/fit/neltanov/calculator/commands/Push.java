package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

import java.util.List;

public class Push implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) {
        try {
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
        }
    }
}
