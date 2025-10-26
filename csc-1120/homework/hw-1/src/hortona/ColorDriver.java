/*
 * Course: CSC1120A - 131
 * Homework 1 - Colors
 * Color Driver
 * Name: Alexander Horton
 * Last Updated: 1/22/2025
 */

package hortona;

import java.util.Scanner;

/**
 * Driver for the Color enum
 */
public class ColorDriver {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean quit = false;
        String input;

        System.out.println("Welcome to Number Converter 3000!");
        System.out.println("This program will take a decimal number");
        System.out.println("and convert it to binary and hexadecimal representations.");
        System.out.println(Color.BLUE + "Decimal will be displayed in blue.");
        System.out.println(Color.RED + "Hexadecimal will be displayed in red.");
        System.out.println(Color.GREEN + "Binary will be displayed in green.");
        System.out.println(Color.RESET);
        while (!quit) {
            System.out.println("Please enter a number to convert (or q to quit)");
            input = scan.nextLine();
            if (input.equalsIgnoreCase("q")) {
                quit = true;
            } else if (validate(input)) {
                display(Integer.parseInt(input));
            }
        }
        System.out.println("Thank you for using Number Converter 3000!");
    }

    private static boolean validate(String input) {
        boolean isInt = Character.isDigit(input.charAt(0)) || input.charAt(0) == '-';
        for (int i = 1; i < input.length() && isInt; i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isInt = false;
            }
        }
        return isInt;
    }
    private static void display(int input) {
        final int hexBase = 16;
        final int binaryBase = 2;

        System.out.println(Color.BLUE + "Decimal: "
                + input + "\n" +
                Color.RED + "Hexadecimal: 0x" +
                Integer.toString(input, hexBase).toUpperCase() + "\n" +
                Color.GREEN + "Binary: " +
                Integer.toString(input, binaryBase) + "\n" + Color.RESET);
    }
}
