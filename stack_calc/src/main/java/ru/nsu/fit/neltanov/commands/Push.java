package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

public class Push implements Command {
    @Override
    public void execute(ExecutionContext context) {
        try {
            String pushValue = context.getCommandArguments().get(1);
            Double value;
            if ((value = context.getParameterValue(pushValue)) != null) {
                context.pushNumberToStack(value);
            }
            else {
                context.pushNumberToStack(Double.valueOf(context.getCommandArguments().get(1)));
            }
        }
        catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
