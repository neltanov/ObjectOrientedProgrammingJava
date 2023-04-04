package ru.nsu.fit.neltanov.calculator;

public class Main {
    public static void main(String[] args) {
        StackCalculator calculator;
        try {
            if (!args[0].isEmpty()) {
                calculator = new StackCalculator(args[0]);
            } else {
                calculator = new StackCalculator();
            }
        } catch (ArrayIndexOutOfBoundsException | ClassNotFoundException | NullPointerException e) {
            calculator = new StackCalculator();
        }
        calculator.run();
    }
}