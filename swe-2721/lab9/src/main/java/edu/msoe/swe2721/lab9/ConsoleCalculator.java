package edu.msoe.swe2721.lab9;

import java.util.Scanner;

public class ConsoleCalculator {
    private boolean allIntegers;
    private double currentResult;
    private boolean printSpace = false;

    ConsoleCalculator() {
        allIntegers = true;
        currentResult = 0;
    }

    public void processLine(String line) {
        Scanner in = new Scanner(line);
        String operand;
        boolean keepProcessing = true;

        while (in.hasNext() && keepProcessing) {
            if (in.hasNextInt()) {
                int num = in.nextInt();
                allIntegers = true;
                setAndPrintCurrentResult(num);
                printSpace = true;
            } else if (in.hasNextDouble()) {
                double num = in.nextDouble();
                allIntegers = false;
                setAndPrintCurrentResult(num);
                printSpace = true;
            } else {
                operand = in.next();
                double num = 0;

                if (in.hasNextInt()) {
                    num = in.nextInt();
                } else if (in.hasNextDouble()) {
                    num = in.nextDouble();
                    allIntegers = false;
                }

                switch (operand) {
                    case "+" -> handleAdd(num);
                    case "-" -> handleSubtract(num);
                    case "*" -> handleMultiply(num);
                    case "/" -> handleDivide(num);
                    case "C" -> {
                        keepProcessing = false;
                        handleClear();
                    }
                    case "=" -> {
                        keepProcessing = false;
                        handleEquals();
                    }
                }
            }
        }

        in.close();
    }

    private void printNewline() {
        System.out.print("\n");
    }

    private void printNumber(double value) {
        if (value == (int) value) {
            System.out.print((int) value);
        } else {
            System.out.printf("%s", value);
        }
    }

    private void setAndPrintCurrentResult(double currentResult) {
        this.currentResult = currentResult;
        printNumber(currentResult);
    }

    private void handleAdd(double addend) {
        double num = currentResult + addend;
        if (printSpace) {
            System.out.print(" ");
            printSpace = false;
        }
        System.out.print("+ ");
        printNumber(addend);
        printNewline();
        setAndPrintCurrentResult(num);
    }

    private void handleSubtract(double subtractend) {
        double num = currentResult - subtractend;
        if (printSpace) {
            System.out.print(" ");
            printSpace = false;
        }
        System.out.print("- ");
        printNumber(subtractend);
        printNewline();
        setAndPrintCurrentResult(num);
    }

    private void handleMultiply(double multiplier) {
        double num = currentResult * multiplier;
        if (printSpace) {
            System.out.print(" ");
            printSpace = false;
        }
        System.out.print("* ");
        printNumber(multiplier);
        printNewline();
        setAndPrintCurrentResult(num);
    }

    private void handleDivide(double dividend) {
        if (currentResult % dividend != 0) {
            allIntegers = false;
        }
        double num = currentResult / dividend;
        if (printSpace) {
            System.out.print(" ");
            printSpace = false;
        }
        System.out.print("/ ");
        printNumber(dividend);
        printNewline();
        setAndPrintCurrentResult(num);
    }

    private void handleEquals() {
        System.out.print(" = ");
        setAndPrintCurrentResult(currentResult);
        printNewline();
    }

    private void handleClear() {
        currentResult = 0;
        allIntegers = true;
        printNewline();
    }
}
