package ru.nsu.fit.neltanov.commands;

import ru.nsu.fit.neltanov.ExecutionContext;

public class Comment implements Command {
    @Override
    public void execute(ExecutionContext context) {
        System.out.println(context.getCommandArguments().get(1));
    }
}
