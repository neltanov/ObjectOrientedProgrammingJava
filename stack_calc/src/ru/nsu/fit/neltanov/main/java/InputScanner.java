package ru.nsu.fit.neltanov.main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

public class InputScanner {
    static Stack<String> stack = new Stack<>();

    InputScanner(String filename) throws FileNotFoundException {
        try {
            FileReader fileReader = new FileReader(filename);
            Scanner input = new Scanner(fileReader);
            stack = scan(input);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    InputScanner() {
        try {
            Scanner input = new Scanner(System.in);
            stack = scan(input);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Stack<String> scan(Scanner input) {
        String buf;
        while (input.hasNext()) {
            buf = input.next();
            if (buf.equals("PRINT"))
                break;
            stack.push(buf);
        }
        return stack;
    }

    public void printStack() {
        System.out.println(stack.pop());
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public final Stack<String> getInput() {
        return stack;
    }
}
