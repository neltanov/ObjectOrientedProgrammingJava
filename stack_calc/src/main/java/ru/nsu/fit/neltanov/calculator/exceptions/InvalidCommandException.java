package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidCommandException extends RuntimeException {
    private final String className;
    private final String configFile;

    public InvalidCommandException(String className, String configFile) {
        this.className = className;
        this.configFile = configFile;
    }

    @Override
    public String getMessage() {
        return "Command '" + className + "' from file '" + configFile + "' is not a calculator command";
    }
}
