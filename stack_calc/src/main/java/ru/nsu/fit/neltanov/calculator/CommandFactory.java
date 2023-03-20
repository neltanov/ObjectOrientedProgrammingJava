package ru.nsu.fit.neltanov.calculator;

import ru.nsu.fit.neltanov.calculator.commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandFactory {
    private final Map<String, Class<?>> commands = new HashMap<>();

    CommandFactory() {
        try {
            Class<?> myClass = Class.forName(CommandFactory.class.getName());
            InputStream inputStream = myClass.getResourceAsStream("commandFactoryConfig.txt");
            if (inputStream == null) {
                throw new NullPointerException();
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line, commandName;
            String[] matching;
            Class<?> command;
            while ((line = bufferedReader.readLine()) != null) {
                matching = line.split(" ");
                commandName = matching[0];
                command = Class.forName(matching[1]);
                commands.put(commandName, command);
            }
        } catch (ClassNotFoundException | NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Command getCommand(String commandName) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<?> commandMetaInfo = Objects.requireNonNull(commands.get(commandName));
        return (Command) commandMetaInfo.getDeclaredConstructor().newInstance();
    }
}
