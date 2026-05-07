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
 * Command to enter numbers in a calculator.
 */
public class EnterCommand extends CalculatorCommand {
    private final long number;

    /**
     * Creates a command to enter a number into the calculator.
     * @param calculator Receiver of this command
     * @param number Number to enter
     */
    public EnterCommand(Calculator calculator, long number) {
        super(calculator);
        this.number = number;
    }

    /**
     * Execute this command
     */
    public void execute() {
        calculator.enterNumber(number);
    }

    /**
     * Undo this command
     */
    public void undo() {
        calculator.removeNumber();
    }
}
