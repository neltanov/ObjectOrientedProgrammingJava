package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;

import java.util.List;

public class Sum implements Command {
    @Override
    public void execute(ExecutionContext context, List<String> arguments) {
        Double a = context.popNumberFromStack();
        Double b = context.popNumberFromStack();
        context.pushNumberToStack(a + b);
    }
}
