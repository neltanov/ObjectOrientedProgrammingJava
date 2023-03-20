package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidParameterValueExceptionException extends InvalidParameterDefinitionException {
    @Override
    public String getMessage() {
        return super.getMessage() + " Parameter value must be only a digital";
    }
}
