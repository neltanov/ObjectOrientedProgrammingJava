package ru.nsu.fit.neltanov;

import java.util.*;

public class ExecutionContext {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> parameters = new HashMap<>();
    private List<String> command = new ArrayList<>();

    public Double popNumberFromStack() {
        return stack.pop();
    }

    public Double getNumberFromStack() {
        return stack.get(0);
    }

    public void pushNumberToStack(Double number) {
        stack.push(number);
    }

    public Double getParameterValue(String parameter) {
        return parameters.get(parameter);
    }

    public void setParameterValue(String parameter, Double value) {
        parameters.put(parameter, value);
    }

    public void setContextCommand(String command) {
        this.command = List.of(command.split(" "));
    }

    public String getCommandName() {
        return command.get(0);
    }

    public ArrayList<String> getCommandArguments() {
        return (ArrayList<String>) command;
    }
}
