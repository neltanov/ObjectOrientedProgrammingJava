package ru.nsu.fit.neltanov.calculator;

import java.util.*;

public class ExecutionContext {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> parameters = new HashMap<>();

    public Double popNumberFromStack() {
        return stack.pop();
    }

    public Double getNumberFromStack() {
        Double stackNumber = null;
        try {
            stackNumber = stack.pop();
            stack.push(stackNumber);
        }
        catch (EmptyStackException e) {
            System.out.println(e.getMessage());
        }
        return stackNumber;
    }

    public void pushNumberToStack(Double number) {
        stack.push(number);
    }

    public Double getParameterValue(String parameter) {
        return parameters.get(parameter);
    }

    public void setParameterValue(String parameter, Double value) {
        try {
            parameters.put(parameter, value);
        }
        catch (UnsupportedOperationException | ClassCastException |
               NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
