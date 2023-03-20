package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidCountOfArgumentsException extends InvalidOperationArgumentException {
    @Override
    public String getMessage() {
        return "Invalid count of arguments in this command.";
    }
}
