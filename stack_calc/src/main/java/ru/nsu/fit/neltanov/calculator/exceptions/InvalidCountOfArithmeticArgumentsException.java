package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidCountOfArithmeticArgumentsException extends InvalidCountOfArgumentsException {
    @Override
    public String getMessage() {
        return super.getMessage() + " No arguments are needed here";
    }
}
