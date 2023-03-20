package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

import java.util.List;

public class Define implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) {
        context.setParameterValue(arguments.get(1),
                Double.valueOf(arguments.get(2)));
    }
}
