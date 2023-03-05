package ru.nsu.fit.neltanov;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class InputCommands {
    public static ArrayList<String> commandList = new ArrayList<>();

    InputCommands(String filename) {
        try {
            FileReader fileReader = new FileReader(filename);
            Scanner input = new Scanner(fileReader);
            commandList = scan(input);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    InputCommands() {
        try {
            Scanner input = new Scanner(System.in);
            commandList = scan(input);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<String> scan(Scanner input) {
        String buf;
        while (input.hasNext()) {
            buf = input.next();
            commandList.add(buf);
        }
        return commandList;
    }

    public void printCommands() {
        for (String command : commandList) {
            System.out.println(command);
        }
    }
}