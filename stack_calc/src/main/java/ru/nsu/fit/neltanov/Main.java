package ru.nsu.fit.neltanov;

public class Main {
    public static void main(String[] args) {

        InputCommands commands;
        if (args.length > 0) {
            commands = new InputCommands(args[0]);
        }
        else {
            commands = new InputCommands();
        }
        commands.printCommands();

//        StackCalculator calculator = new StackCalculator();
//        calculator.Calculate(InputCommands.commandList);
    }
}