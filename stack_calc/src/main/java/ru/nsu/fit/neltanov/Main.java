package ru.nsu.fit.neltanov;

public class Main {
    public static void main(String[] args) {
        StackCalculator calculator;
        if (args.length > 0) {
            calculator = new StackCalculator(args[0]);
        }
        else {
            calculator = new StackCalculator();
        }
    }
}