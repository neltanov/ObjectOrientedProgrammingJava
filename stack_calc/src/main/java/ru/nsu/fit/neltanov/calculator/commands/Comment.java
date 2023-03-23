package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfOneArgumentException;

import java.util.List;

public class Comment implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) throws InvalidCountOfOneArgumentException {
        if (arguments.size() != 2) {
            throw new InvalidCountOfOneArgumentException();
        }
        for (String argument : arguments) {
            System.out.print(argument + " ");
        }
        System.out.println("\n");
    }
}
