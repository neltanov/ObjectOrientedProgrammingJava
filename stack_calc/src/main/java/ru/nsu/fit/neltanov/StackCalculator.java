package ru.nsu.fit.neltanov;

import ru.nsu.fit.neltanov.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class StackCalculator {
    private static Stack<String> stack = new Stack<>();
    public Integer Calculate(List<String> commands) {
        Integer result = 0;

        //while

        return result;
    }
    protected Operation createOperation(OperationType type) {
        Operation operation = null;
        switch (type) {
            case PUSH -> operation = new Push();
            case POP -> operation = new Pop();
            case SUM -> operation = new Sum();
            case SUBSTRACT -> operation = new Substract();
            case MULTIPLY -> operation = new Multiply();
            case DIVIDE -> operation = new Divide();
            case SQRT -> operation = new Sqrt();
            case DEFINE -> operation = new Define();
            case PRINT -> operation = new Print();
            case COMMENT -> operation = new Comment();
        }
        return operation;
    }
}
