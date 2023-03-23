package ru.nsu.fit.neltanov.calculator.exceptions;

public class InvalidOperationArgumentException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Invalid arguments! Please check the arguments";
    }
}
