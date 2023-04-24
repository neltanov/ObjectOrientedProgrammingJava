package ru.nsu.fit.neltanov.calculator;

public class Main {
    public static void main(String[] args) {
        StackCalculator calculator;
        try {
            if (args.length != 0) {
                System.out.println("You have chosen file '" + args[0] + "' for executing commands");
                calculator = new StackCalculator(args[0]);
            } else {
                System.out.println("You haven't chosen any file for executing, so you have to type commands to console");
                calculator = new StackCalculator();
            }
        } catch (ArrayIndexOutOfBoundsException | ClassNotFoundException | NullPointerException e) {
            System.out.println("File for executing was not found. So you have to type commands to console");
            calculator = new StackCalculator();
        }
        calculator.run();
    }
}