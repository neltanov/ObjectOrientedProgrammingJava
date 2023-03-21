package ru.nsu.fit.neltanov.calculator;

import ru.nsu.fit.neltanov.calculator.commands.Command;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class StackCalculator {
    private final ExecutionContext context = new ExecutionContext();
    private final CommandFactory factory = new CommandFactory();
    private InputStream inputStream;

    public StackCalculator() {
        inputStream = System.in;
    }

    public StackCalculator(String commandPath) {
        try {
            Class<?> myClass = Class.forName(StackCalculator.class.getName());
            InputStream inputStream = myClass.getResourceAsStream(commandPath);
            if (inputStream == null) {
                throw new NullPointerException();
            }
        } catch (ClassNotFoundException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String commandWithArgs;
            Command command;
            List<String> arguments;
            while ((commandWithArgs = bufferedReader.readLine()) != null) {
                if (commandWithArgs.equals("")) {
                    break;
                }
                arguments = List.of(commandWithArgs.split(" "));
                command = factory.getCommand(arguments.get(0));
                command.execute(context, arguments);
            }
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("This command doesn't exist");
        }
    }
}
