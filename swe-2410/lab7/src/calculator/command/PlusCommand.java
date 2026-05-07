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
 * Command to add numbers in a calculator.
 */
public class PlusCommand extends CalculatorCommand {

    private long a;
    private long b;

    /**
     * Creates a command to add the top numbers of the calculator's stack
     * @param calculator Receiver for this command.
     */
    public PlusCommand(Calculator calculator) {
        super(calculator);
    }

    /**
     * Execute this command
     */
    public void execute() {
        if(hasTwoNumbers()){
            a = calculator.removeNumber();
            b = calculator.removeNumber();
            calculator.enterNumber(a+b);
        } else {
            throw new IllegalStateException("Calculator needs at least two values for addition.");
        }
    }

    /**
     * Undo this command
     */
    public void undo() {
        calculator.removeNumber();
        calculator.enterNumber(b);
        calculator.enterNumber(a);
    }
}
