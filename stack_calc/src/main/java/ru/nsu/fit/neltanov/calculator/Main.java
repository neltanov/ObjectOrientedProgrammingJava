package ru.nsu.fit.neltanov.calculator;

public class Main {
    public static void main(String[] args) {
        StackCalculator calculator;
        try {
            calculator = new StackCalculator(args[0]);
        } catch (ArrayIndexOutOfBoundsException | ClassNotFoundException | NullPointerException e) {
            calculator = new StackCalculator();
        }
        calculator.run();
    }
}