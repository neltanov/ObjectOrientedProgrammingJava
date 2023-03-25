package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfDefineArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidParameterDefinitionException;

import java.util.List;

public class Define implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments)
            throws NumberFormatException, InvalidCountOfDefineArgumentsException, InvalidParameterDefinitionException {
        if (arguments.size() != 3) {
            throw new InvalidCountOfDefineArgumentsException();
        }

        if (Character.isDigit(arguments.get(1).charAt(0))) {
            throw new InvalidParameterDefinitionException();
        }

        context.setParameterValue(arguments.get(1), Double.valueOf(arguments.get(2)));
    }
}
