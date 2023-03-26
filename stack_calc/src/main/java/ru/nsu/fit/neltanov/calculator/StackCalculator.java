package ru.nsu.fit.neltanov.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.fit.neltanov.calculator.commands.Command;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArgumentsException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

public class StackCalculator {
    private final static Logger logger = LogManager.getLogger(StackCalculator.class.getName());

    private final ExecutionContext context = new ExecutionContext();
    private final CommandFactory factory = new CommandFactory("commandFactoryConfig.txt");
    private final InputStream inputStream;

    public StackCalculator() {
        inputStream = System.in;
        logger.info("Input stream initialized from standard input stream");
    }

    public StackCalculator(String commandPath) throws ClassNotFoundException, NullPointerException {
        logger.info("Trying to initialize input stream from file " + commandPath);

        Class<?> myClass = Class.forName(StackCalculator.class.getName());
        inputStream = myClass.getResourceAsStream(commandPath);
        if (inputStream == null) {
            logger.warn("The command file was not found");
            throw new NullPointerException();
        }

        logger.info("Initializing input stream from file is succeed");
    }

    public void run() {
        logger.info("The calculator has started working");

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String commandWithArgs;
            Command command;
            List<String> arguments;
            String commandName;
            String[] commandWithArgsArray;
            String[] argumentsArray;

            logger.info("Starting to read commands with arguments from input stream...");

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
                        logger.info("Executing command: " + command + " with arguments: " + arguments);
                        command.execute(context, arguments);
                    }
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException e) {
                    logger.warn("Problems with getting the command found", e);
                    System.out.println(e.getMessage());
                } catch (InvalidCountOfArgumentsException e) {
                    logger.warn("Incorrect number of arguments entered to the command", e);
                    System.out.println(e.getMessage());
                } catch (EmptyStackException e) {
                    logger.warn("As a result of executing the command, the stack was empty", e);
                    System.out.println("Stack is empty");
                } catch (NumberFormatException e) {
                    logger.warn("Problems were found in using the Define command. " +
                            "Invalid parameter value entered", e);
                    System.out.println(e.getMessage() + ": this symbol cannot define parameter");
                } catch (ArithmeticException e) {
                    logger.warn("Division by zero or taking the square root " +
                            "of a negative number was performed", e);
                    System.out.println("The number must be positive!");
                }
            }
        } catch (IOException e) {
            logger.warn("I/O error occurs", e);
            System.out.println(e.getMessage());
        }
    }
}
