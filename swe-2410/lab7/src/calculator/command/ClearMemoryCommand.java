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
 * Represent a command to clear the calculator's memory
 */
public class ClearMemoryCommand extends CalculatorCommand {

    private long num;

    /**
     * Creates a command to clear the passed-in calculator's memory
     * @param calculator Receiver for this command.
     */
    public ClearMemoryCommand(Calculator calculator) {
        super(calculator);
    }

    /**
     * Executes this command.
     */
    public void execute() {
        num = calculator.getMemory();
        calculator.clearMemory();
    }

    /**
     * Undoes this command
     */
    public void undo() {
        calculator.enterNumber(num);
        calculator.saveToMemory();
        calculator.removeNumber();
    }
}
