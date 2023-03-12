package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

import java.util.List;

public class Comment implements Command {
    @Override
    public void execute(ExecutionContext context) {
        List<String> commandArguments = context.getCommandArguments();
        for (String commandArgument : commandArguments) {
            System.out.print(commandArgument + " ");
        }
        System.out.println("\n");
    }
}
