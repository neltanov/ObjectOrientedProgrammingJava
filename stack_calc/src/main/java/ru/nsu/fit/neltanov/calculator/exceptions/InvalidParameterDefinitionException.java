package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidParameterDefinitionException extends InvalidOperationArgumentException {
    @Override
    public String getMessage() {
        return "Parameter defined incorrectly. The parameter cannot start with a digit.";
    }
}
