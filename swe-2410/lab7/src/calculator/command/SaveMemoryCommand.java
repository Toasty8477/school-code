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
 * Represent a command to save the calculator's memory
 */
public class SaveMemoryCommand extends CalculatorCommand {
    /**
     * Creates a command to save the passed-in calculator's memory
     * @param calculator Receiver for this command.
     */
    public SaveMemoryCommand(Calculator calculator) {
        super(calculator);
    }

    /**
     * Execute this command
     */
    public void execute() {
        calculator.saveToMemory();
    }

    /**
     * Undo this command
     */
    public void undo() {
        calculator.clearMemory();
    }
}
