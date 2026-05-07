/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 7 - Commanding Calculators
 * Name: Alex Horton
 * Created: 3/24/2026
 */

package calculator.command;

import calculator.Calculator;
import calculator.CalculatorDivideByZeroException;

/**
 * Command to divide numbers in a calculator.
 */
public class DivideCommand extends CalculatorCommand {

    private long a;
    private long b;

    /**
     * Creates a command to divide the top numbers of the calculator's stack
     * @param calculator Receiver for this command.
     */
    public DivideCommand(Calculator calculator) {
        super(calculator);
    }

    /**
     * Execute this command
     */
    public void execute() {
        if (hasTwoNumbers()) {
            a = calculator.removeNumber();
            b = calculator.removeNumber();
            // Divide by zero check
            if (a == 0) {
                throw new CalculatorDivideByZeroException("Error, division by 0");
            } else {
                calculator.enterNumber(b / a);
            }
        } else {
            throw new IllegalStateException("Calculator needs at least two values for multiplication.");
        }
    }

    /**
     * Execute this command
     */
    public void undo() {
        calculator.removeNumber();
        calculator.enterNumber(b);
        calculator.enterNumber(a);
    }
}
