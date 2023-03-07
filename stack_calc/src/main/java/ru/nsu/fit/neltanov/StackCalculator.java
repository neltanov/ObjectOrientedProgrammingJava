package ru.nsu.fit.neltanov;

import ru.nsu.fit.neltanov.commands.*;

import java.util.*;

public class StackCalculator {
    private Stack<String> stack = new Stack<>();
    private Map<String, Double> defineValues = new HashMap<>();
    public Integer Calculate(List<String> commands) {
        Integer result = 0;

        while (!stack.empty()) {
            Operation operation = createOperation("PUSH");
        }

        return result;
    }
    public Operation createOperation(String type) {
        Operation operation = null;
        switch (type) {
            case "PUSH" -> operation = new Push();
            case "POP" -> operation = new Pop();
            case "SUM" -> operation = new Sum();
            case "SUBSTRACT" -> operation = new Substract();
            case "MULTIPLY" -> operation = new Multiply();
            case "DIVIDE" -> operation = new Divide();
            case "SQRT" -> operation = new Sqrt();
            case "DEFINE" -> operation = new Define();
            case "PRINT" -> operation = new Print();
            case "COMMENT" -> operation = new Comment();
        }
        return operation;
    }
}
