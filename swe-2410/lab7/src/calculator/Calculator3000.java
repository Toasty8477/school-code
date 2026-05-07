/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 7 - Commanding Calculators
 * Name: Alex Horton
 * Created: 3/24/2026
 */

package calculator;

import java.util.Scanner;

/**
 * Interface to a calculator that takes in numbers, operations, and special commands.
 *
 * @author R. Hasker, A.Velez
 */

public class Calculator3000 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Calculator3000!!!");

        Calculator calculator = new Calculator();
        CalculatorManager manager = new CalculatorManager(calculator);

        boolean done = false;
        printMenu();

        while (!done) {
            System.out.print("> ");
            String text = in.nextLine().trim();
            if (!text.isEmpty()) {
                switch (text.toLowerCase()) {
                    case "ms" -> {
                        executeAndDisplay("Save", manager::saveMemory, manager);
                    }
                    case "mr" -> {
                        executeAndDisplay("Recall", manager::recallMemory, manager);
                    }
                    case "mc" -> {
                        executeAndDisplay("Clear Memory", manager::clearMemory, manager);
                    }
                    case "u" -> {
                        executeAndDisplay("Undo", manager::undo, manager);
                    }
                    case "r" -> {
                        executeAndDisplay("Redo", manager::redo, manager);
                    }
                    case "d" -> {
                        executeAndDisplay("Clear Display", manager::clearNumbers, manager);
                    }
                    case "a" -> {
                        executeAndDisplay("Reset All", manager::resetAll, manager);
                    }
                    case "q" -> {
                        System.out.println("Quit");
                        done = true;
                    }
                    case "help" -> {
                        printMenu();
                    }
                    case "?" -> {
                        printMenu();
                    }
                    default -> {
                        processExpression(text, manager);
                    }
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("Enter numbers separated by commas and followed by their operators.");
        System.out.println("Example: 5,2,12,3,1+/-* -> 5 * (2 - (12 / (1+3)))");
        System.out.println("Other commands:");
        System.out.println("\tMemory Store (ms)  Memory Recall (mr)  Memory Clear (mc)");
        System.out.println("\tClear Display (d)  Reset All (a)  Quit (q)");
        System.out.println("\tUndo (u)  Redo (r)");
        System.out.println("\tHelp (?)");
    }

    private static void executeAndDisplay(String label, Runnable action, CalculatorManager manager) {
        System.out.println(label);
        action.run();
        System.out.println(manager.display());
    }

    private static void processExpression(String text, CalculatorManager manager) {
        StringBuilder currentNumber = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                currentNumber.append(c);
            } else if ("+-*/,".indexOf(c) >= 0) {

                // Flush any number before processing operator/comma
                if (!currentNumber.isEmpty()) {
                    long number = Long.parseLong(currentNumber.toString());
                    manager.enterNumber(number);
                    currentNumber.setLength(0);
                    System.out.println(manager.display());
                }

                switch (c) {
                    case '+' -> {
                        executeAndDisplay("Add", manager::addNumbers, manager);
                    }
                    case '-' -> {
                        executeAndDisplay("Subtract", manager::subtractNumbers, manager);
                    }
                    case '*' -> {
                        executeAndDisplay("Multiply", manager::multiplyNumbers, manager);
                    }
                    case '/' -> {
                        executeAndDisplay("Divide", manager::divideNumbers, manager);
                    }
                    case ',' -> {
                        // separator only
                    }
                    default -> {
                        // unreachable
                    }
                }
            } else if (!Character.isWhitespace(c)) {
                System.out.println("Invalid character: " + c);
            }
        }

        // flush trailing number
        if (!currentNumber.isEmpty()) {
            long number = Long.parseLong(currentNumber.toString());
            manager.enterNumber(number);
            System.out.println(manager.display());
        }
    }
}