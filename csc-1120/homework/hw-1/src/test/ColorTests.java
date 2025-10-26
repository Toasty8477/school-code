/*
 * Course: CSC-1120
 * Homework 1 - Enumerations
 */
package test;

import hortona.Color;
import hortona.ColorDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Basic tests for the Color and ColorDriver classes
 */
public class ColorTests {

    @Test
    @DisplayName("Testing Color enum")
    @Order(1)
    void color() {
        Assertions.assertAll(
                () -> Assertions.assertEquals("\u001B[0m", Color.RESET.toString()),
                () -> Assertions.assertEquals("\u001B[30m", Color.BLACK.toString()),
                () -> Assertions.assertEquals("\u001B[31m", Color.RED.toString()),
                () -> Assertions.assertEquals("\u001B[32m", Color.GREEN.toString()),
                () -> Assertions.assertEquals("\u001B[33m", Color.YELLOW.toString()),
                () -> Assertions.assertEquals("\u001B[34m", Color.BLUE.toString()),
                () -> Assertions.assertEquals("\u001B[35m", Color.MAGENTA.toString()),
                () -> Assertions.assertEquals("\u001B[36m", Color.CYAN.toString()),
                () -> Assertions.assertEquals("\u001B[37m", Color.WHITE.toString())
        );
    }

    @Test
    @DisplayName("Testing validate")
    @Order(2)
    void validate() throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException {
        Method validate = ColorDriver.class.getDeclaredMethod("validate", String.class);
        validate.setAccessible(true);
        Assertions.assertAll(
                () -> Assertions.assertTrue((boolean)validate.invoke(null, "1"),
                        "'1' should be a valid input."),
                () -> Assertions.assertTrue((boolean)validate.invoke(null, "214"),
                        "'214' should be a valid input."),
                () -> Assertions.assertTrue((boolean)validate.invoke(null, "111"),
                        "'111' should be a valid input."),
                () -> Assertions.assertFalse((boolean)validate.invoke(null, "1a"),
                        "'1a' should be an invalid input."),
                () -> Assertions.assertFalse((boolean)validate.invoke(null, "1 1"),
                        "'1 1' should be an invalid input."),
                () -> Assertions.assertFalse((boolean)validate.invoke(null, "3.2"),
                        "'3.2' should be an invalid input."),
                () -> Assertions.assertFalse((boolean)validate.invoke(null, "3$"),
                        "3$ should be an invalid input.")
        );
    }


    @Test
    @DisplayName("Testing display")
    @Order(3)
    void display() throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException, IOException {
        final int test1 = 39;
        Method display = ColorDriver.class.getDeclaredMethod("display", int.class);
        display.setAccessible(true);
        PrintStream oldOS = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        System.setOut(ps);
        display.invoke(null, test1);
        System.setOut(oldOS);
        File student = Paths.get("src", "test", "output.txt").toFile();
        try (PrintWriter pw = new PrintWriter(student)) {
            pw.println(bos);
        }
        StringBuilder sb = new StringBuilder();
        try(Scanner in = new Scanner(student)) {
            while(in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        }
        Assertions.assertEquals("""
                [34mDecimal: 39
                [31mHexadecimal: 0x27
                [32mBinary: 100111
                [0m
                
                """, sb.toString());
        if(!student.delete()) {
            System.err.println("Temporary file not deleted");
        }
    }
}
