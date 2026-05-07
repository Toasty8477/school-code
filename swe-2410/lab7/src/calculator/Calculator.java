/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 7 - Commanding Calculators
 * Name: Alex Horton
 * Created: 3/24/2026
 */

package calculator;

import java.util.ArrayDeque;

/**
 * Implements a basic calculator with add, subtract, multiplication, and division.
 * The operations produce whole numbers. Includes a memory that can be saved,
 * recalled, and cleared.
 *
 * @author R. Hasker, A.Velez
 */
public class Calculator {
    private ArrayDeque<Long> numbers;
    private long memory;

    /**
     * Creates a new calculator with no numbers or memory loaded.
     */
    public Calculator() {
        numbers = new ArrayDeque<>();
        memory = 0;
    }

    public long getMemory() {
        return memory;
    }

    /**
     * Currently computed result
     * @return Result as a long
     */
    public long result() {
        if(!numbers.isEmpty()) {
            return numbers.peek();
        } else {
            return 0;
        }
    }

    /**
     * Enters a number into the calculator that will be used
     * in a calculation
     * @param number Number to put on the stack
     */
    public void enterNumber(long number){
        numbers.push(number);
    }

    /**
     * Removes a number from the stop of the stack
     * @return Number from the top of the stack
     * @throws IllegalStateException if the stack is empty.
     */
    public long removeNumber(){
        if(!numbers.isEmpty()){
            return numbers.pop();
        } else {
            throw new IllegalStateException("Can't remove a number from an empty Calculator.");
        }
    }

    private boolean hasTwoNumbers(){
        if(!numbers.isEmpty()){
            long a = numbers.pop();
            if(!numbers.isEmpty()){
                numbers.push(a);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * Pops the top two numbers off the stacks, adds them, and
     * then pushes the result back on the stack
     * @throws IllegalStateException if there are less than two numbers in the calculator
     */
    public void plus() {
        if(hasTwoNumbers()){
            numbers.push(numbers.pop() + numbers.pop());
        } else {
            throw new IllegalStateException("Calculator needs at least two values for addition.");
        }
    }

    /**
     * Pops the top two numbers off the stacks, subtracts them, and
     * then pushes the result back on the stack
     * @throws IllegalStateException if there are less than two numbers in the calculator
     */
    public void minus() {
        if(hasTwoNumbers()) {
            numbers.push(-numbers.pop() + numbers.pop());
        } else {
            throw new IllegalStateException("Calculator needs at least two values for subtraction.");
        }
    }

    /**
     * Pops the top two numbers off the stacks, multiplies them, and
     * then pushes the result back on the stack
     * @throws IllegalStateException if there are less than two numbers in the calculator
     */
    public void times() {
        if(hasTwoNumbers()) {
            numbers.push(numbers.pop() * numbers.pop());
        } else {
            throw new IllegalStateException("Calculator needs at least two values for multiplication.");
        }
    }

    /**
     * Pops the top two numbers off the stacks, divides them, and
     * then pushes the result back on the stack
     * @throws CalculatorDivideByZeroException if the divisor (topmost number) is zero.
     * @throws IllegalStateException if there are less than two numbers in the calculator
     */
    public void divide() {
        if(hasTwoNumbers()){
            if (!numbers.isEmpty() && numbers.peek() == 0) {
                throw new CalculatorDivideByZeroException("Error, division by 0");
            } else {
                long d = numbers.pop();
                long n = numbers.pop();
                numbers.push(n / d);
            }
        } else {
            throw new IllegalStateException("Calculator needs at least two values for multiplication.");
        }
    }

    /**
     * Saves the value at the top of the stack to memory
     */
    public void saveToMemory() {
        if(!numbers.isEmpty()) {
            memory = numbers.peek();
        }
    }
    /**
     * Pull saved value from memory and pushes it onto the stack
     */
    public void recallFromMemory() {
        if(memory != 0){
            numbers.push(memory);
        }
    }

    /**
     * Clears the stored number if any
     */
    public void clearMemory(){
        memory = 0;
    }

    /**
     * Clears the numbers in the calculator
     */
    public void clearNumbers() {
        numbers = new ArrayDeque<>();
    }
}
