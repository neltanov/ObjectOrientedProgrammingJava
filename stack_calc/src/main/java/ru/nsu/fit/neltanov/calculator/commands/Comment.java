package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

import java.util.List;

public class Comment implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) {
        for (String argument : arguments) {
            System.out.print(argument + " ");
        }
        System.out.println("\n");
    }
}
