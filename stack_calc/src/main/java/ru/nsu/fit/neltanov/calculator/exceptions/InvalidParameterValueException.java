package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidParameterValueException extends InvalidParameterDefinitionException {
    @Override
    public String getMessage() {
        return super.getMessage() + " Parameter value must be only a digital";
    }
}
