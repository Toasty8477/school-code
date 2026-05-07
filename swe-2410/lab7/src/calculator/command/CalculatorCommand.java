/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 7 - Commanding Calculators
 * Name: Alex Horton
 * Created: 3/24/2026
 */

package calculator.command;

import calculator.Calculator;

/**
 * Abstract class that represent a Calculator Command
 * Extend this class for the various calculator operations
 */
public abstract class CalculatorCommand {
    protected Calculator calculator;
    /**
     * Creates a CalculatorCommand whose receiver
     * is the passed in calculator object.
     * @param calculator Receiver for this command.
     */
    public CalculatorCommand(Calculator calculator) {
        this.calculator = calculator;
    }

    /**
     * Perform operation on calculator.
     */
    public abstract void execute();

    /**
     * Restore calculator to the state before performing this operation.
     */
    public abstract void undo();

    // Protected to prevent duplicate code across subclasses
    protected boolean hasTwoNumbers(){
        // Evil exception catching to check if numbers is empty
        try {
            long a = calculator.removeNumber();

            try {
                long b = calculator.removeNumber();
                // There must be two numbers, give them back and return true
                calculator.enterNumber(b);
                calculator.enterNumber(a);
                return true;

            } catch (IllegalStateException e) {
                // Put number back on stack and return false
                // because there is only one number in numbers
                calculator.enterNumber(a);
                return false;
            }

        } catch (IllegalStateException e) {
            // Return false because numbers is empty
            return false;
        }
    }
}
