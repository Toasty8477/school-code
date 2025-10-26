/*
 * Course: CS1110 - 111
 * Fall 2024
 * Homework 10
 * Name: Alexander Horton
 * Last Updated: 11/7/2024
 */
package hortona;

import java.util.Scanner;

/**
 * Converts numbers to hex and binary and displays them in *color*.
 */
public class ColorDriver {

    /**
     * validates that the users input is an int
     * @param input any String
     * @return -1 if not an int or the passed in string as an int if an int
     */
    public static int validate(String input) {
        boolean isInt = Character.isDigit(input.charAt(0)) || input.charAt(0) == '-';
        int ret = -1;
        for (int i = 1; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isInt = false;
            }
        }
        if (isInt) {
            ret = Integer.parseInt(input);
        }
        return ret;
    }

    /**
     * displays the decimal, hex, and binary representations
     * @param decimal any integer.
     */
    public static void display(int decimal) {
        final int hexBase = 16;
        System.out.println(Color.BLUE + "Decimal: " + decimal);
        System.out.println(Color.RED + "Hexadecimal: 0x" + Integer.toString(decimal, hexBase));
        System.out.println(Color.GREEN + "Binary: " + Integer.toString(decimal, 2));
        System.out.println(Color.RESET);

    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input;
        boolean running = true;

        System.out.println("Welcome to Number Converter 3000!");
        System.out.println("This program will take a decimal number");
        System.out.println("and convert it to binary and hexadecimal representations.");
        System.out.println(Color.BLUE + "Decimal will be displayed in blue");
        System.out.println(Color.RED + "Hexadecimal will be displayed in red");
        System.out.println(Color.GREEN + "Binary will be displayed in green" + Color.RESET);
        do {
            System.out.println("Please enter a number to convert (or q to quit)");
            input = scan.next();
            if (input.equalsIgnoreCase("q")) {
                running = false;
            }
            if (validate(input) != -1) {
                display(validate(input));
            }
        } while (running);
        System.out.println("Thank you for using Number Converter 3000!");
    }
}