/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 7 - Call Stack
 * Name: Alexander Horton
 * Updated: 3/11/2025
 */

package hortona;

/**
 * Simulates JVM Stack calls
 */
public class ProgramStack {

    private IntStack stack;

    /**
     * ProgramStack constructor
     */
    public ProgramStack() {
        stack = new IntStack();
    }

    /**
     * Push the stack frame for the called method onto the stack
     * @param name name of the method
     * @param arguments any number of int arguments
     */
    public void callMethod(String name, int... arguments) {
        int stackSize = 1;
        stack.push(methodToProgramCounter(name, arguments));
        for (int i = 0; i < arguments.length; i++) {
            stack.push(arguments[i]);
            stackSize++;
        }
        stack.push(stackSize);
    }

    /**
     * Removes the top stack frame from the stack and adds the return value the next stack frame
     * @param returnValue return value from the removed stack frame
     */
    public void returnFromMethod(int returnValue) {
        returnFromMethod();
        int stackFrameSize = stack.pop();
        stack.push(returnValue);
        stack.push(stackFrameSize + 1);
    }

    /**
     * Removes the top stack frame from the stack
     */
    public void returnFromMethod() {
        int toRemove = stack.pop();
        while (toRemove > 0) {
            stack.pop();
            toRemove--;
        }
    }

    /**
     * Calculates a program counter value based on the name and arguments of a method
     * @param name name of the method
     * @param arguments method arguments
     * @return calculated program counter value
     */
    public int methodToProgramCounter(String name, int... arguments) {
        int counter;
        counter = name.charAt(0) * 2;
        for (int i = 1; i < name.length(); i++) {
            counter += name.charAt(i);
            counter *= 2;
        }
        if (arguments.length > 0) {
            counter++;
        }
        return counter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|          |\n");
        sb.append("|----------|\n");
        sb.append(stack.toString());
        sb.append("+----------+");
        return sb.toString();
    }
}
