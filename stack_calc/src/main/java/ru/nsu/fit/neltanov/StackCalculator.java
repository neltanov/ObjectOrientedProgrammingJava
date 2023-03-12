package ru.nsu.fit.neltanov;

import ru.nsu.fit.neltanov.commands.Command;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class StackCalculator {
    private final ExecutionContext context = new ExecutionContext();
    private final CommandFactory factory = new CommandFactory();

    StackCalculator() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        executeCommands(bufferedReader);
    }

    StackCalculator(String commandPath) {
        try {
            Class<?> myClass = Class.forName("ru.nsu.fit.neltanov.Main");
            InputStream inputStream = myClass.getResourceAsStream(commandPath);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                executeCommands(bufferedReader);
            } else {
                throw new NullPointerException();
            }
        } catch (ClassNotFoundException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void executeCommands(BufferedReader bufferedReader) {
        try {
            String contextCommand;
            Command command;
            while ((contextCommand = bufferedReader.readLine()) != null) {
                context.setContextCommand(contextCommand);
                command = factory.getCommand(context.getCommandName());
                command.execute(context);
            }
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }
}
