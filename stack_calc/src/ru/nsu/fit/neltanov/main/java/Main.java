package ru.nsu.fit.neltanov.main.java;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        InputScanner scanner;
        if (args.length > 0) {
            scanner = new InputScanner(args[0]);
        }
        else {
            scanner = new InputScanner();
        }
//        while (!scanner.isEmpty()) {
//
//        }
        scanner.printStack();
    }
}