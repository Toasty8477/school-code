/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 7 - Commanding Calculators
 * Name: Alex Horton
 * Created: 3/24/2026
 */

package calculator.command;

import calculator.Calculator;

import java.util.List;

/**
 * Command that contains multiple calculator commands
 */
public class MacroCommand extends CalculatorCommand {
    private final List<CalculatorCommand> commandList;

    /**
     * Creates a Commands that is composed of multiple commands
     * @param calculator Receiver of the commands
     * @param commandList List of calculator commands.
     */
    public MacroCommand(Calculator calculator, List<CalculatorCommand> commandList) {
        super(calculator);
        this.commandList = commandList;
    }

    /**
     * Execute this command
     */
    @Override
    public void execute() {
        for (CalculatorCommand command : commandList) {
            command.execute();
        }
    }

    /**
     * Undo this command
     */
    @Override
    public void undo() {
        for (int i = commandList.size() - 1; i >= 0; i--) {
            commandList.get(i).undo();
        }
    }
}
