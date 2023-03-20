package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfDefineArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidParameterValueExceptionException;

import java.util.List;

public class Define implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) {
        try {
            if (!isNumeric(arguments.get(2))) {
                throw new InvalidParameterValueExceptionException();
            }
            if (arguments.size() != 3) {
                throw new InvalidCountOfDefineArgumentsException();
            }
            context.setParameterValue(arguments.get(1), Double.valueOf(arguments.get(2)));
        }
        catch (InvalidParameterValueExceptionException e) {
            System.out.println(e.getMessage());
        } catch (InvalidCountOfDefineArgumentsException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isNumeric(String word) {
        boolean isOnlyDigits = true;
        for(int i = 0; i < word.length() && isOnlyDigits; i++) {
            if(!Character.isDigit(word.charAt(i)) || word.charAt(i) == '.') {
                isOnlyDigits = false;
            }
        }
        return isOnlyDigits;
    }
}
