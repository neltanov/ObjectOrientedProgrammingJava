package ru.nsu.fit.neltanov.test;

import org.junit.jupiter.api.Test;

import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.commands.*;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArithmeticArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfDefineArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfOneArgumentException;

import java.util.EmptyStackException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCalculator {
    @Test
    public void testPushCommand() {
        ExecutionContext context = new ExecutionContext();
        Push command = new Push();

        assertThrows(InvalidCountOfOneArgumentException.class,
                () -> command.execute(context, List.of("PUSH")));

        command.execute(context, List.of("PUSH", "3"));
        assertEquals(3, context.getNumberFromStack());
    }

    @Test
    public void testPopCommand() {
        ExecutionContext context = new ExecutionContext();
        Pop command = new Pop();

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("POP", "")));

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of("POP")));

        context.pushNumberToStack(4.0);
        command.execute(context, List.of("POP"));

    }

    @Test
    public void testDefineCommand() {
        ExecutionContext context = new ExecutionContext();
        Define command = new Define();

        assertThrows(NumberFormatException.class,
                () -> command.execute(context, List.of("DEFINE", "b", "g")));

        assertThrows(InvalidCountOfDefineArgumentsException.class,
                () -> command.execute(context, List.of("DEFINE", "")));

        command.execute(context, List.of("DEFINE", "a", "12"));
        assertEquals(12, context.getParameterValue("a"));

    }

    @Test
    public void testSumCommand() {
        ExecutionContext context = new ExecutionContext();
        Sum command = new Sum();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of("+")));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("+", "")));

        Double firstOperand = 5.1;
        Double secondOperand = 10.4;
        Double expectedResult = secondOperand + firstOperand;

        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);

        command.execute(context, List.of("+"));

        assertEquals(expectedResult, context.getNumberFromStack());

    }

    @Test
    public void testSubstractCommand() {
        ExecutionContext context = new ExecutionContext();
        Substract command = new Substract();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of("-")));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("-", "")));

        Double firstOperand = 5.1;
        Double secondOperand = 10.4;
        Double expectedResult = secondOperand - firstOperand;

        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);

        command.execute(context, List.of("-"));

        assertEquals(expectedResult, context.getNumberFromStack());

    }

    @Test
    public void testMultiplyCommand() {
        ExecutionContext context = new ExecutionContext();
        Multiply command = new Multiply();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of("*")));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("*", "")));

        Double firstOperand = 5.1;
        Double secondOperand = 10.4;
        Double expectedResult = secondOperand * firstOperand;

        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);


        command.execute(context, List.of("*"));

        assertEquals(expectedResult, context.getNumberFromStack());

    }

    @Test
    public void testDivideCommand() {
        ExecutionContext context = new ExecutionContext();
        Divide command = new Divide();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of("/")));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("/", "")));

        Double firstOperand = 5.1;
        Double secondOperand = 10.4;
        Double expectedResult = secondOperand / firstOperand;

        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);

        command.execute(context, List.of("/"));

        assertEquals(expectedResult, context.getNumberFromStack());

        firstOperand = 0.0;
        secondOperand = 1.0;
        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);
        assertThrows(ArithmeticException.class,
                () -> command.execute(context, List.of("/")));

    }

    @Test
    public void testSqrtCommand() {
        ExecutionContext context = new ExecutionContext();
        Sqrt command = new Sqrt();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of("SQRT")));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("SQRT", "")));

        double operand = 5.1;
        Double expectedResult = Math.sqrt(operand);

        context.pushNumberToStack(operand);

        command.execute(context, List.of("SQRT"));

        assertEquals(expectedResult, context.getNumberFromStack());

        operand = -1;
        context.pushNumberToStack(operand);
        assertThrows(ArithmeticException.class,
                () -> command.execute(context, List.of("SQRT")));

    }

}
