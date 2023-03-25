package ru.nsu.fit.neltanov.calculator;

import ru.nsu.fit.neltanov.calculator.commands.Command;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArgumentsException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

public class StackCalculator {
    private final ExecutionContext context = new ExecutionContext();
    private final CommandFactory factory = new CommandFactory();
    private InputStream inputStream;

    public StackCalculator() {
        inputStream = System.in;
    }

    public StackCalculator(String commandPath) throws ClassNotFoundException, NullPointerException {
        Class<?> myClass = Class.forName(StackCalculator.class.getName());
        InputStream inputStream = myClass.getResourceAsStream(commandPath);
        if (inputStream == null) {
            throw new NullPointerException();
        }
    }

    public void run() {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String commandWithArgs;
            Command command;
            List<String> arguments;
            String commandName;
            String[] commandWithArgsArray;
            String[] argumentsArray;
            while ((commandWithArgs = bufferedReader.readLine()) != null) {
                try {
                    if (commandWithArgs.equals("")) {
                        break;
                    }
                    commandWithArgsArray = commandWithArgs.split(" ");
                    argumentsArray = Arrays.copyOfRange(commandWithArgsArray, 1, commandWithArgsArray.length);

                    commandName = commandWithArgsArray[0];
                    arguments = List.of(argumentsArray);
                    if ((command = factory.getCommand(commandName)) != null) {
                        command.execute(context, arguments);
                    }
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidCountOfArgumentsException e) {
                    System.out.println(e.getMessage());
                } catch (EmptyStackException e) {
                    System.out.println("Stack is empty");
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage() + ": this symbol cannot define parameter");
                } catch (ArithmeticException e) {
                    System.out.println("The number must be positive!");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
