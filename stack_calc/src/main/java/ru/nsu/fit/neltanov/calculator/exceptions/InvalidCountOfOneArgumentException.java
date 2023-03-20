package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidCountOfOneArgumentException extends InvalidCountOfArgumentsException{
    @Override
    public String getMessage() {
        return super.getMessage() + " There are 1 argument needed here";
    }
}
