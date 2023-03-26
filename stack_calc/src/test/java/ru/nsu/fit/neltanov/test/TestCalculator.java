package ru.nsu.fit.neltanov.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.mockito.ArgumentCaptor;
import ru.nsu.fit.neltanov.calculator.ExecutionContext;
import ru.nsu.fit.neltanov.calculator.commands.*;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfArithmeticArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfDefineArgumentsException;
import ru.nsu.fit.neltanov.calculator.exceptions.InvalidCountOfOneArgumentException;

import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class TestCalculator {
    @Test
    @DisplayName("Testing push command...")
    public void testPushCommand() {
        ExecutionContext context = new ExecutionContext();
        Push command = new Push();

        assertThrows(InvalidCountOfOneArgumentException.class,
                () -> command.execute(context, List.of()));

        command.execute(context, List.of("3"));
        assertEquals(3, context.getNumberFromStack());
    }

    @Test
    @DisplayName("Testing pop command...")
    public void testPopCommand() {
        ExecutionContext context = new ExecutionContext();
        Pop command = new Pop();

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("")));

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of()));

        context.pushNumberToStack(4.0);
        command.execute(context, List.of());

    }

    @Test
    @DisplayName("Testing define command...")
    public void testDefineCommand() {
        ExecutionContext context = new ExecutionContext();
        Define command = new Define();

        assertThrows(NumberFormatException.class,
                () -> command.execute(context, List.of("b", "g")));

        assertThrows(InvalidCountOfDefineArgumentsException.class,
                () -> command.execute(context, List.of()));

        command.execute(context, List.of("a", "12"));
        assertEquals(12, context.getParameterValue("a"));

    }

    @Test
    @DisplayName("Testing sum command...")
    public void testSumCommand() {
        ExecutionContext context = new ExecutionContext();
        Sum command = new Sum();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of()));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("")));

        Double firstOperand = 5.1;
        Double secondOperand = 10.4;
        Double expectedResult = secondOperand + firstOperand;

        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);

        command.execute(context, List.of());

        assertEquals(expectedResult, context.getNumberFromStack());

    }

    @Test
    @DisplayName("Testing substract command...")
    public void testSubstractCommand() {
        ExecutionContext context = new ExecutionContext();
        Substract command = new Substract();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of()));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("")));

        Double firstOperand = 5.1;
        Double secondOperand = 10.4;
        Double expectedResult = secondOperand - firstOperand;

        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);

        command.execute(context, List.of());

        assertEquals(expectedResult, context.getNumberFromStack());

    }

    @Test
    @DisplayName("Testing multiply command...")
    public void testMultiplyCommand() {
        ExecutionContext context = new ExecutionContext();
        Multiply command = new Multiply();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of()));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("")));

        Double firstOperand = 5.1;
        Double secondOperand = 10.4;
        Double expectedResult = secondOperand * firstOperand;

        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);


        command.execute(context, List.of());

        assertEquals(expectedResult, context.getNumberFromStack());
    }

    @Test
    @DisplayName("Testing divide command...")
    public void testDivideCommand() {
        ExecutionContext context = new ExecutionContext();
        Divide command = new Divide();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of()));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("")));

        Double firstOperand = 5.1;
        Double secondOperand = 10.4;
        Double expectedResult = secondOperand / firstOperand;

        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);

        command.execute(context, List.of());

        assertEquals(expectedResult, context.getNumberFromStack());

        firstOperand = 0.0;
        secondOperand = 1.0;
        context.pushNumberToStack(firstOperand);
        context.pushNumberToStack(secondOperand);
        assertThrows(ArithmeticException.class,
                () -> command.execute(context, List.of()));

    }

    @Test
    @DisplayName("Testing square root command...")
    public void testSqrtCommand() {
        ExecutionContext context = new ExecutionContext();
        Sqrt command = new Sqrt();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of()));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("")));

        double operand = 5.1;
        Double expectedResult = Math.sqrt(operand);

        context.pushNumberToStack(operand);

        command.execute(context, List.of());

        assertEquals(expectedResult, context.getNumberFromStack());

        operand = -1;
        context.pushNumberToStack(operand);
        assertThrows(ArithmeticException.class,
                () -> command.execute(context, List.of()));

    }

    @Test
    @DisplayName("Testing print command...")
    public void testPrintCommand() {
        ExecutionContext context = new ExecutionContext();
        Print command = new Print();

        assertThrows(EmptyStackException.class,
                () -> command.execute(context, List.of()));

        assertThrows(InvalidCountOfArithmeticArgumentsException.class,
                () -> command.execute(context, List.of("")));

        Double number = 1.0;
        context.pushNumberToStack(number);

        PrintStream stream = mock(PrintStream.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        System.setOut(stream);

        command.execute(context, List.of());

        verify(stream).println(captor.capture());
        assertThat(captor.getValue(), containsString(number.toString()));
    }
}
