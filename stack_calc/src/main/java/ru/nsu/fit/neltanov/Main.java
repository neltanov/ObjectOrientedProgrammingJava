package ru.nsu.fit.neltanov;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        InputCommands commands;
        if (args.length > 0) {
            commands = new InputCommands(args[0]);
        }
        else {
            commands = new InputCommands();
        }
        commands.printCommands();

        StackCalculator calculator = new StackCalculator();
        calculator.Calculate(InputCommands.commandList);
    }
}