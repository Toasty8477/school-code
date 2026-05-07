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
 * Represent a command to recall the calculator's memory
 */
public class RecallMemoryCommand extends CalculatorCommand {
    /**
     * Creates a command to recall the passed-in calculator's memory
     * @param calculator Receiver for this command.
     */
    public RecallMemoryCommand(Calculator calculator) {
        super(calculator);
    }

    /**
     * Execute this command
     */
    public void execute() {
        long mem = calculator.getMemory();
        if (mem != 0) {
            calculator.enterNumber(mem);
        }
    }

    /**
     * Undo this command
     */
    public void undo() {
        calculator.saveToMemory();
        calculator.removeNumber();
    }
}
