package ru.nsu.fit.neltanov.calculator;

import ru.nsu.fit.neltanov.calculator.commands.Command;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class StackCalculator {
    private final ExecutionContext context = new ExecutionContext();
    private final CommandFactory factory = new CommandFactory();
    private BufferedReader bufferedReader;
    public StackCalculator() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    public StackCalculator(String commandPath) {
        try {
            Class<?> myClass = Class.forName("ru.nsu.fit.neltanov.calculator.Main");
            InputStream inputStream = myClass.getResourceAsStream(commandPath);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
            } else {
                throw new NullPointerException();
            }
        } catch (ClassNotFoundException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        try {
            String contextCommand;
            Command command;
            List<String> arguments;
            while ((contextCommand = bufferedReader.readLine()) != null) {
                arguments = List.of(contextCommand.split(" "));
                command = factory.getCommand(arguments.get(0));
                command.execute(context, arguments);
            }
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }
    //public Optional<Double> exec
}
