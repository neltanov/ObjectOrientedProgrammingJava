package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

public class Print implements Command {
    @Override
    public void execute(ExecutionContext context) {
        System.out.println("Current value on top of the stack: " + context.getNumberFromStack());
    }
}
