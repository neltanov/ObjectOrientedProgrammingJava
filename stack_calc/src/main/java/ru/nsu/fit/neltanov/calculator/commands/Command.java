package ru.nsu.fit.neltanov.calculator.commands;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArithmeticArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfDefineArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfOneArgumentException;

import java.util.List;

public interface Command {
    void execute(ExecutionContext context, List<String> arguments) throws InvalidCountOfDefineArgumentsException, InvalidCountOfArithmeticArgumentsException, InvalidCountOfOneArgumentException;
}
