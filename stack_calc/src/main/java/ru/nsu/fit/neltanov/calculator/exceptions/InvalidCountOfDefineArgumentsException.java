package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidCountOfDefineArgumentsException extends InvalidCountOfArgumentsException {
    @Override
    public String getMessage() {
        return super.getMessage() + " There are 2 arguments needed here";
    }
}
