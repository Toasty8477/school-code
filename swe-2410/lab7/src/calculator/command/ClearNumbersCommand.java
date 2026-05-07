/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 7 - Commanding Calculators
 * Name: Alex Horton
 * Created: 3/24/2026
 */

package calculator.command;

import java.util.ArrayDeque;

import calculator.Calculator;

/**
 * Command to clear the numbers in a calculator.
 */
public class ClearNumbersCommand extends CalculatorCommand {

    private ArrayDeque<Long> oldNumbers;

    /**
     * Creates a command to clear the passed-in calculator's numbers
     * @param calculator Receiver for this command.
     */
    public ClearNumbersCommand(Calculator calculator) {
        super(calculator);
        oldNumbers = new ArrayDeque<>();
    }

    /**
     * Executes this command
     */
    public void execute() {
        // Remove all numbers one at a time
        // Save them to an array
        boolean numbersLeft = true;
        while (numbersLeft) {
            try {
                oldNumbers.push(calculator.removeNumber());
            } catch (IllegalStateException e) {
                numbersLeft = false;
            }
        }
    }

    /**
     * Undo this command
     */
    public void undo() {
        // Enter all the numbers again
        while (oldNumbers.peek() != null) {
            calculator.enterNumber(oldNumbers.pop());
        }
    }
}
