package ru.nsu.fit.neltanov.calculator.exceptions;

public class DivisionByZeroException extends ArithmeticException {
    @Override
    public String getMessage() {
        return "Division by zero";
    }
}
