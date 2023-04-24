package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidParameterDefinitionException;

import java.util.List;

public class Define implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments)
            throws NumberFormatException, InvalidCountOfArgumentsException, InvalidParameterDefinitionException {
        if (arguments.size() != 2) {
            throw new InvalidCountOfArgumentsException(2);
        }

        if (Character.isDigit(arguments.get(0).charAt(0))) {
            throw new InvalidParameterDefinitionException();
        }

        context.setParameterValue(arguments.get(0), Double.valueOf(arguments.get(1)));
    }
}
