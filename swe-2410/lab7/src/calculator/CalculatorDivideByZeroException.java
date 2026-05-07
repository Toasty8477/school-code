/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 7 - Commanding Calculators
 * Name: Alex Horton
 * Created: 3/24/2026
 */

package calculator;

/**
 * Custom exception that is thrown when the calculator divides by zero
 */
public class CalculatorDivideByZeroException extends RuntimeException {
    /**
     * Creates an exception with the passed-in msg
     * @param msg Message to print out for this exception
     */
    public CalculatorDivideByZeroException(String msg){
        super(msg);
    }
}
