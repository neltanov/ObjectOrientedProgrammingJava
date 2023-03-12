package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("test.txt");
            System.out.println(fileReader.read());
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}