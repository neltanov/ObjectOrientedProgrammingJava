package ru.nsu.fit.neltanov.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final static Logger logger = LogManager.getLogger(CommandFactory.class.getName());

    private final Map<String, Class<?>> commands = new HashMap<>();

    CommandFactory(String configFile) {
        try {
            logger.info("Configuring command factory with file " + configFile);
            Class<?> myClass = Class.forName(CommandFactory.class.getName());
            InputStream inputStream = myClass.getResourceAsStream(configFile);
            if (inputStream == null) {
                logger.error("Configuring file were not found");
                throw new NullPointerException();
            }
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String line, commandName;
                String[] matching;
                Class<?> command;
                while ((line = bufferedReader.readLine()) != null) {
                    matching = line.split(" ");
                    commandName = matching[0];
                    command = Class.forName(matching[1]);
                    commands.put(commandName, command);
                }
            }
        } catch (ClassNotFoundException | NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Command getCommand(String commandName) throws NullPointerException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Command command = null;
        try {
            Class<?> commandMetaInfo = Objects.requireNonNull(commands.get(commandName));
            command = (Command) commandMetaInfo.getDeclaredConstructor().newInstance();
        } catch (NullPointerException e) {
            System.out.println("This command doesn't exist. ");
        }
        return command;
    }
}
