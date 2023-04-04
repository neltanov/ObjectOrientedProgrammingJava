package ru.nsu.fit.neltanov.calculator.exceptions;

public class NegativeSquareRootException extends ArithmeticException {
    @Override
    public String getMessage() {
        return "Taking the square root of a negative number";
    }
}
