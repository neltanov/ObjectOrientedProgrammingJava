package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidCountOfArgumentsException extends InvalidOperationArgumentException {
    private final int argumentCount;

    public InvalidCountOfArgumentsException(int argumentCount) {
        this.argumentCount = argumentCount;
    }

    @Override
    public String getMessage() {
        return "Invalid count of arguments in this command. This command requires " + argumentCount + " argument(s)";
    }
}
