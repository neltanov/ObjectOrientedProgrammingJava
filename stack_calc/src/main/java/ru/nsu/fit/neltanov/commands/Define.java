package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

public class Define implements Command {
    @Override
    public void execute(ExecutionContext context) {
        context.setParameterValue(context.getCommandArguments().get(1),
                Double.valueOf(context.getCommandArguments().get(2)));
    }
}
