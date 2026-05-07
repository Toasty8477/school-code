/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 7 - Commanding Calculators
 * Name: Alex Horton
 * Created: 3/24/2026
 */

package calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import calculator.command.CalculatorCommand;
import calculator.command.ClearMemoryCommand;
import calculator.command.ClearNumbersCommand;
import calculator.command.DivideCommand;
import calculator.command.EnterCommand;
import calculator.command.MacroCommand;
import calculator.command.MinusCommand;
import calculator.command.PlusCommand;
import calculator.command.RecallMemoryCommand;
import calculator.command.SaveMemoryCommand;
import calculator.command.TimesCommand;

/**
 * Manages the operations of the Calculator
 */
public class CalculatorManager {
    private final Calculator calculator;
    private boolean errorState;

    private ArrayDeque<CalculatorCommand> history;
    private ArrayDeque<CalculatorCommand> undoHistory;

    /**
     * Creates a CalculatorManager that manages the operation of
     * the passed-in Calculator
     * @param calculator Calculator object to manage
     */
    public CalculatorManager(Calculator calculator) {
        this.calculator = calculator;
        this.history = new ArrayDeque<>();
        this.undoHistory = new ArrayDeque<>();
    }

    /**
     * Creates a String that shows that is being displayed on
     * the calculator
     * @return Values displayed on the calculator.
     */
    public String display() {
        StringBuilder value = new StringBuilder();
        if (errorState) {
            value.append("Error!!!");
        } else {
            value.append("Display: ").append(calculator.result());
            if (calculator.getMemory() != 0) {
                value.append(" [Mem: ").append(calculator.getMemory()).append("]");
            }
        }
        return value.toString();
    }

    /**
     * Enters a number into the calculator
     * @param number Number to enter.
     */
    public void enterNumber(long number) {
        executeOperation("enter", number);
    }

    /**
     * Adds the top two numbers in the calculator
     */
    public void addNumbers() {
        executeOperation("plus");
    }

    /**
     * Subtracts the top two numbers in the calculator
     */
    public void subtractNumbers() {
        executeOperation("minus");
    }

    /**
     * Multiplies the top two numbers in the calculator
     */
    public void multiplyNumbers() {
        executeOperation("times");
    }

    /**
     * Divides the top two numbers in the calculator
     */
    public void divideNumbers() {
        executeOperation("divide");
    }

    /**
     * Save the top number of the calculator to memory
     */
    public void saveMemory() {
        executeOperation("ms");
    }

    /**
     * Loads the number in memory into the calculator
     */
    public void recallMemory() {
        executeOperation("mr");
    }

    /**
     * Clears the number in memory
     */
    public void clearMemory() {
        executeOperation("mc");
    }

    /**
     * Clears all the numbers in the calculator
     */
    public void clearNumbers() {
        errorState = false;
        executeOperation("d");
    }

    /**
     * Clears all the numbers and memory
     */
    public void resetAll() {
        errorState = false;
        executeOperation("a");
    }

    /**
     * Undo the last commands
     */
    public void undo() {
        if (!history.isEmpty()) {
            CalculatorCommand command = history.pop();
            command.undo();
            undoHistory.push(command);
        }
    }

    /**
     * Redo the last undone commands
     */
    public void redo() {
        if (!undoHistory.isEmpty()) {
            CalculatorCommand command = undoHistory.pop();
            command.execute();
            history.push(command);
        }
    }

    private void executeOperation(String operation){
        executeOperation(operation, 0);
    }
    private void executeOperation(String operation, long number){
        if(!errorState) {
            try {
                switch (operation) {
                    case "enter" -> {
                        CalculatorCommand command = new EnterCommand(calculator, number);
                        command.execute();
                        history.push(command);
                        undoHistory.clear();
                    }
                    case "plus" -> {
                        CalculatorCommand command = new PlusCommand(calculator);
                        command.execute();
                        history.push(command);
                    }
                    case "minus" -> {
                        CalculatorCommand command = new MinusCommand(calculator);
                        command.execute();
                        history.push(command);
                    }
                    case "times" -> {
                        CalculatorCommand command = new TimesCommand(calculator);
                        command.execute();
                        history.push(command);
                    }
                    case "divide" -> {
                        CalculatorCommand command = new DivideCommand(calculator);
                        command.execute();
                        history.push(command);
                    }
                    case "ms" -> {
                        CalculatorCommand command = new SaveMemoryCommand(calculator);
                        command.execute();
                        history.push(command);
                    }
                    case "mr" -> {
                        CalculatorCommand command = new RecallMemoryCommand(calculator);
                        command.execute();
                        history.push(command);
                    }
                    case "mc" -> {
                        CalculatorCommand command = new ClearMemoryCommand(calculator);
                        command.execute();
                        history.push(command);
                    }
                    case "d" -> {
                        CalculatorCommand command = new ClearNumbersCommand(calculator);
                        command.execute();
                        history.push(command);
                    }
                    case "a" -> {
                        List<CalculatorCommand> list = new ArrayList<>(); 
                        list.add(new ClearMemoryCommand(calculator));
                        list.add(new ClearNumbersCommand(calculator));
                        CalculatorCommand command = new MacroCommand(calculator, list);
                        command.execute();
                        history.push(command);
                    }
                }
            } catch (CalculatorDivideByZeroException e){
                errorState = true;
            } catch (IllegalStateException e){
                System.out.println("Not enough values in the calculator");
                System.out.println(e.getMessage());
            }
        }
    }
}
