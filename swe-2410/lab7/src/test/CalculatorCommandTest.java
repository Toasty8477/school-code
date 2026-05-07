package test;

import calculator.Calculator;
import calculator.CalculatorDivideByZeroException;
import calculator.command.*;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

public class CalculatorCommandTest {
    private Calculator calculator;

    @BeforeEach
    public void setupCalculator() {
        calculator = new Calculator();
    }

    @DisplayName("Basic operations")
    @Nested
    class TestBasicOperations {
        @DisplayName("Test entering two numbers")
        @Test
        public void testEnteringTwoNumbers() {
            new EnterCommand(calculator, 2).execute();
            new EnterCommand(calculator, 3).execute();

            Assertions.assertEquals(3, calculator.result(),
                    "After entering 2 and then 3, expected top of stack to be 3, but was " + calculator.result());

            calculator.removeNumber();
            Assertions.assertEquals(2, calculator.result(),
                    "After removing the top value (3), expected top of stack to be 2, but was " + calculator.result());

            calculator.removeNumber();
            Assertions.assertEquals(0, calculator.result(),
                    "After removing all entered values, expected calculator result to be 0, but was " + calculator.result());
        }

        @DisplayName("Test adding two numbers")
        @Test
        public void testAddingTwoNumbers() {
            new EnterCommand(calculator, 2).execute();
            new EnterCommand(calculator, 3).execute();
            new PlusCommand(calculator).execute();

            Assertions.assertEquals(5, calculator.result(),
                    "After adding 2 and 3, expected result to be 5, but was " + calculator.result());
        }

        @DisplayName("Test subtracting two numbers")
        @Test
        public void testSubtractingTwoNumbers() {
            new EnterCommand(calculator, 2).execute();
            new EnterCommand(calculator, 3).execute();
            new MinusCommand(calculator).execute();

            Assertions.assertEquals(-1, calculator.result(),
                    "After subtracting 3 from 2, expected result to be -1, but was " + calculator.result());
        }

        @DisplayName("Test multiplying two numbers")
        @Test
        public void testTimesTwoNumbers() {
            new EnterCommand(calculator, 2).execute();
            new EnterCommand(calculator, 3).execute();
            new TimesCommand(calculator).execute();

            Assertions.assertEquals(6, calculator.result(),
                    "After multiplying 2 by 3, expected result to be 6, but was " + calculator.result());
        }

        @DisplayName("Test dividing two numbers")
        @Test
        public void testDivideTwoNumbers() {
            new EnterCommand(calculator, 6).execute();
            new EnterCommand(calculator, 2).execute();

            new DivideCommand(calculator).execute();
            Assertions.assertEquals(3, calculator.result(),
                    "After dividing 6 by 2, expected result to be 3, but was " + calculator.result());

            new EnterCommand(calculator, 4).execute();
            new DivideCommand(calculator).execute();
            Assertions.assertEquals(0, calculator.result(),
                    "After dividing 3 by 4 using integer division, expected result to be 0, but was " + calculator.result());

            new EnterCommand(calculator, 0).execute();
            Assertions.assertThrows(CalculatorDivideByZeroException.class,
                    () -> new DivideCommand(calculator).execute(),
                    "Expected dividing by zero to throw CalculatorDivideByZeroException, but no exception was thrown.");
        }

        @DisplayName("Test 5,2,12,3,1+/-* -> 5 * (2 - (12 / (1+3)))")
        @Test
        public void testMultipleOperations() {
            new EnterCommand(calculator, 5).execute();
            new EnterCommand(calculator, 2).execute();
            new EnterCommand(calculator, 12).execute();
            new EnterCommand(calculator, 3).execute();
            new EnterCommand(calculator, 1).execute();

            //(1 + 3)
            new PlusCommand(calculator).execute();
            Assertions.assertEquals(4, calculator.result(),
                    "After computing (1 + 3), expected top of stack to be 4, but was " + calculator.result());

            //(12 / 4)
            new DivideCommand(calculator).execute();
            Assertions.assertEquals(3, calculator.result(),
                    "After computing (12 / 4), expected top of stack to be 3, but was " + calculator.result());

            //(2 - 3)
            new MinusCommand(calculator).execute();
            Assertions.assertEquals(-1, calculator.result(),
                    "After computing (2 - 3), expected top of stack to be -1, but was " + calculator.result());

            //(5 * -1)
            new TimesCommand(calculator).execute();
            Assertions.assertEquals(-5, calculator.result(),
                    "After computing 5 * (2 - (12 / (1 + 3))), expected final result to be -5, but was " + calculator.result());
        }

        @DisplayName("Clearing numbers")
        @Test
        public void clearNumbers() {
            new EnterCommand(calculator, 1).execute();
            new EnterCommand(calculator, 2).execute();
            new EnterCommand(calculator, 3).execute();
            new EnterCommand(calculator, 4).execute();
            new EnterCommand(calculator, 5).execute();

            new ClearNumbersCommand(calculator).execute();

            Assertions.assertEquals(0, calculator.result(),
                    "After clearing all numbers, expected calculator result to be 0, but was " + calculator.result());
        }
    }

    @Nested
    class MemoryTests {

        @DisplayName("Testing saving to memory")
        @Test
        public void testSaveToMemory() throws NoSuchFieldException, IllegalAccessException {
            new EnterCommand(calculator, 42).execute();
            new SaveMemoryCommand(calculator).execute();

            Field memoryField = Calculator.class.getDeclaredField("memory");
            memoryField.setAccessible(true);
            long memoryValue = memoryField.getLong(calculator);

            Assertions.assertEquals(42, memoryValue,
                    "Expected private field 'memory' to equal 42 after SaveMemoryCommand, but was " + memoryValue);
        }

        @DisplayName("Testing recall from memory")
        @Test
        public void testRecallFromMemory() throws NoSuchFieldException, IllegalAccessException {
            Field memoryField = Calculator.class.getDeclaredField("memory");
            memoryField.setAccessible(true);
            memoryField.setLong(calculator, 42);

            new RecallMemoryCommand(calculator).execute();

            Assertions.assertEquals(42, calculator.result(),
                    "After recalling memory value 42, expected top of stack to be 42, but was " + calculator.result());
        }

        @DisplayName("Testing clear memory")
        @Test
        public void testClear() throws NoSuchFieldException, IllegalAccessException {
            new EnterCommand(calculator, 67).execute();

            Field memoryField = Calculator.class.getDeclaredField("memory");
            memoryField.setAccessible(true);
            memoryField.setLong(calculator, 42);

            new ClearMemoryCommand(calculator).execute();

            Assertions.assertEquals(0, memoryField.getLong(calculator),
                    "After clearing memory, expected private field 'memory' to be 0, but was " + memoryField.getLong(calculator));

            // make sure the numbers in the calculator were not cleared
            Assertions.assertEquals(67, calculator.result(),
                    "Clearing memory should not clear the calculator stack; expected top of stack to remain 67, but was " + calculator.result());
        }
    }

    @DisplayName("Undo operations")
    @Nested
    class TestUndoOperations {
        @DisplayName("Test entering two numbers and undo")
        @Test
        public void testEnteringTwoNumbers() {
            CalculatorCommand c1 = new EnterCommand(calculator, 2);
            c1.execute();
            CalculatorCommand c2 = new EnterCommand(calculator, 3);
            c2.execute();

            Assertions.assertEquals(3, calculator.result(),
                    "After entering 2 and then 3, expected top of stack to be 3 before undo, but was " + calculator.result());

            c2.undo();
            Assertions.assertEquals(2, calculator.result(),
                    "After undoing the second EnterCommand, expected top of stack to be 2, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(0, calculator.result(),
                    "After undoing both EnterCommands, expected calculator result to be 0, but was " + calculator.result());
        }

        @DisplayName("Test adding two numbers and undoing")
        @Test
        public void testAddingTwoNumbersUndo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 2);
            c1.execute();
            CalculatorCommand c2 = new EnterCommand(calculator, 3);
            c2.execute();
            CalculatorCommand c3 = new PlusCommand(calculator);
            c3.execute();

            Assertions.assertEquals(5, calculator.result(),
                    "After adding 2 and 3, expected result to be 5 before undo, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing PlusCommand, expected top of stack to be restored to 3, but was " + calculator.result());

            c2.undo();
            Assertions.assertEquals(2, calculator.result(),
                    "After undoing the second EnterCommand, expected top of stack to be 2, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(0, calculator.result(),
                    "After undoing both EnterCommands, expected calculator result to be 0, but was " + calculator.result());
        }

        @DisplayName("Test subtracting two numbers and undoing")
        @Test
        public void testSubtractingTwoNumbersUndo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 2);
            c1.execute();
            CalculatorCommand c2 = new EnterCommand(calculator, 3);
            c2.execute();
            CalculatorCommand c3 = new MinusCommand(calculator);
            c3.execute();

            Assertions.assertEquals(-1, calculator.result(),
                    "After subtracting 3 from 2, expected result to be -1 before undo, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing MinusCommand, expected top of stack to be restored to 3, but was " + calculator.result());

            c2.undo();
            Assertions.assertEquals(2, calculator.result(),
                    "After undoing the second EnterCommand, expected top of stack to be 2, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(0, calculator.result(),
                    "After undoing both EnterCommands, expected calculator result to be 0, but was " + calculator.result());
        }

        @DisplayName("Test multiplying two numbers and undo")
        @Test
        public void testTimesTwoNumbersUndo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 2);
            c1.execute();
            CalculatorCommand c2 = new EnterCommand(calculator, 3);
            c2.execute();
            CalculatorCommand c3 = new TimesCommand(calculator);
            c3.execute();

            Assertions.assertEquals(6, calculator.result(),
                    "After multiplying 2 by 3, expected result to be 6 before undo, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing TimesCommand, expected top of stack to be restored to 3, but was " + calculator.result());

            c2.undo();
            Assertions.assertEquals(2, calculator.result(),
                    "After undoing the second EnterCommand, expected top of stack to be 2, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(0, calculator.result(),
                    "After undoing both EnterCommands, expected calculator result to be 0, but was " + calculator.result());
        }

        @DisplayName("Test dividing two numbers and undo")
        @Test
        public void testDivideTwoNumbersUndo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 2);
            c1.execute();
            CalculatorCommand c2 = new EnterCommand(calculator, 3);
            c2.execute();
            CalculatorCommand c3 = new DivideCommand(calculator);
            c3.execute();

            Assertions.assertEquals(0, calculator.result(),
                    "After dividing 2 by 3 using integer division, expected result to be 0 before undo, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing DivideCommand, expected top of stack to be restored to 3, but was " + calculator.result());

            c2.undo();
            Assertions.assertEquals(2, calculator.result(),
                    "After undoing the second EnterCommand, expected top of stack to be 2, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(0, calculator.result(),
                    "After undoing both EnterCommands, expected calculator result to be 0, but was " + calculator.result());
        }

        @DisplayName("Test undo of 5,2,12,3,1+/-* -> 5 * (2 - (12 / (1+3)))")
        @Test
        public void testMultipleOperationsUndo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 5);
            c1.execute();
            CalculatorCommand c2 = new EnterCommand(calculator, 2);
            c2.execute();
            CalculatorCommand c3 = new EnterCommand(calculator, 12);
            c3.execute();
            CalculatorCommand c4 = new EnterCommand(calculator, 3);
            c4.execute();
            CalculatorCommand c5 = new EnterCommand(calculator, 1);
            c5.execute();

            // stack: 5, 2, 12, 3, 1

            //(1 + 3)
            CalculatorCommand c6 = new PlusCommand(calculator);
            c6.execute();
            // stack: 5, 2, 12, 4

            //(12 / 4)
            CalculatorCommand c7 = new DivideCommand(calculator);
            c7.execute();
            Assertions.assertEquals(3, calculator.result(),
                    "After computing (12 / (1 + 3)), expected top of stack to be 3, but was " + calculator.result());
            // stack: 5, 2, 3

            //(2 - 3)
            CalculatorCommand c8 = new MinusCommand(calculator);
            c8.execute();
            // stack: 5, -1

            //(5 * -1)
            CalculatorCommand c9 = new TimesCommand(calculator);
            c9.execute();
            // stack: -5

            c9.undo();
            // stack: 5, -1
            Assertions.assertEquals(-1, calculator.result(),
                    "After undoing final TimesCommand, expected top of stack to be -1, but was " + calculator.result());

            c8.undo();
            // stack: 5, 2, 3
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing MinusCommand, expected top of stack to be 3, but was " + calculator.result());

            c7.undo();
            // stack: 5, 2, 12, 4
            Assertions.assertEquals(4, calculator.result(),
                    "After undoing DivideCommand, expected top of stack to be 4, but was " + calculator.result());

            c6.undo();
            // stack: 5, 2, 12, 3, 1
            Assertions.assertEquals(1, calculator.result(),
                    "After undoing PlusCommand, expected top of stack to be 1, but was " + calculator.result());

            c5.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing EnterCommand(1), expected top of stack to be 3, but was " + calculator.result());

            c4.undo();
            Assertions.assertEquals(12, calculator.result(),
                    "After undoing EnterCommand(3), expected top of stack to be 12, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(2, calculator.result(),
                    "After undoing EnterCommand(12), expected top of stack to be 2, but was " + calculator.result());

            c2.undo();
            Assertions.assertEquals(5, calculator.result(),
                    "After undoing EnterCommand(2), expected top of stack to be 5, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(0, calculator.result(),
                    "After undoing all commands, expected calculator result to be 0, but was " + calculator.result());
        }

        @DisplayName("Clearing numbers undo")
        @Test
        public void clearNumbersUndo() {
            new EnterCommand(calculator, 1).execute();
            new EnterCommand(calculator, 2).execute();
            new EnterCommand(calculator, 3).execute();
            new EnterCommand(calculator, 4).execute();
            new EnterCommand(calculator, 5).execute();

            CalculatorCommand c1 = new ClearNumbersCommand(calculator);
            c1.execute();

            Assertions.assertEquals(0, calculator.result(),
                    "After executing ClearNumbersCommand, expected calculator result to be 0, but was " + calculator.result());

            c1.undo();

            Assertions.assertEquals(5, calculator.result(),
                    "After undoing ClearNumbersCommand, expected top of stack to be restored to 5, but was " + calculator.result());

            calculator.removeNumber();
            Assertions.assertEquals(4, calculator.result(),
                    "After removing restored top value 5, expected top of stack to be 4, but was " + calculator.result());

            calculator.removeNumber();
            Assertions.assertEquals(3, calculator.result(),
                    "After removing restored top value 4, expected top of stack to be 3, but was " + calculator.result());

            calculator.removeNumber();
            Assertions.assertEquals(2, calculator.result(),
                    "After removing restored top value 3, expected top of stack to be 2, but was " + calculator.result());

            calculator.removeNumber();
            Assertions.assertEquals(1, calculator.result(),
                    "After removing restored top value 2, expected top of stack to be 1, but was " + calculator.result());

            calculator.removeNumber();
            Assertions.assertEquals(0, calculator.result(),
                    "After removing all restored values, expected calculator result to be 0, but was " + calculator.result());
        }
    }

    @DisplayName("Redo operations")
    @Nested
    class TestRedoOperations {

        @DisplayName("Test enter command redo")
        @Test
        public void testEnterRedo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 7);

            c1.execute();
            Assertions.assertEquals(7, calculator.result(),
                    "After executing EnterCommand(7), expected top of stack to be 7, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(0, calculator.result(),
                    "After undoing EnterCommand(7), expected calculator result to be 0, but was " + calculator.result());

            c1.execute();
            Assertions.assertEquals(7, calculator.result(),
                    "After re-executing EnterCommand(7), expected top of stack to be 7, but was " + calculator.result());
        }

        @DisplayName("Test plus command redo")
        @Test
        public void testPlusRedo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 2);
            CalculatorCommand c2 = new EnterCommand(calculator, 3);
            CalculatorCommand c3 = new PlusCommand(calculator);

            c1.execute();
            c2.execute();
            c3.execute();

            Assertions.assertEquals(5, calculator.result(),
                    "After executing PlusCommand on 2 and 3, expected result to be 5, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing PlusCommand, expected top of stack to be restored to 3, but was " + calculator.result());

            c3.execute();
            Assertions.assertEquals(5, calculator.result(),
                    "After re-executing PlusCommand, expected result to be 5, but was " + calculator.result());
        }

        @DisplayName("Test minus command redo")
        @Test
        public void testMinusRedo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 2);
            CalculatorCommand c2 = new EnterCommand(calculator, 3);
            CalculatorCommand c3 = new MinusCommand(calculator);

            c1.execute();
            c2.execute();
            c3.execute();

            Assertions.assertEquals(-1, calculator.result(),
                    "After executing MinusCommand on 2 and 3, expected result to be -1, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing MinusCommand, expected top of stack to be restored to 3, but was " + calculator.result());

            c3.execute();
            Assertions.assertEquals(-1, calculator.result(),
                    "After re-executing MinusCommand, expected result to be -1, but was " + calculator.result());
        }

        @DisplayName("Test times command redo")
        @Test
        public void testTimesRedo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 2);
            CalculatorCommand c2 = new EnterCommand(calculator, 3);
            CalculatorCommand c3 = new TimesCommand(calculator);

            c1.execute();
            c2.execute();
            c3.execute();

            Assertions.assertEquals(6, calculator.result(),
                    "After executing TimesCommand on 2 and 3, expected result to be 6, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing TimesCommand, expected top of stack to be restored to 3, but was " + calculator.result());

            c3.execute();
            Assertions.assertEquals(6, calculator.result(),
                    "After re-executing TimesCommand, expected result to be 6, but was " + calculator.result());
        }

        @DisplayName("Test divide command redo")
        @Test
        public void testDivideRedo() {
            CalculatorCommand c1 = new EnterCommand(calculator, 6);
            CalculatorCommand c2 = new EnterCommand(calculator, 2);
            CalculatorCommand c3 = new DivideCommand(calculator);

            c1.execute();
            c2.execute();
            c3.execute();

            Assertions.assertEquals(3, calculator.result(),
                    "After executing DivideCommand on 6 and 2, expected result to be 3, but was " + calculator.result());

            c3.undo();
            Assertions.assertEquals(2, calculator.result(),
                    "After undoing DivideCommand, expected top of stack to be restored to 2, but was " + calculator.result());

            c3.execute();
            Assertions.assertEquals(3, calculator.result(),
                    "After re-executing DivideCommand, expected result to be 3, but was " + calculator.result());
        }

        @DisplayName("Test clear numbers command redo")
        @Test
        public void testClearNumbersRedo() {
            new EnterCommand(calculator, 1).execute();
            new EnterCommand(calculator, 2).execute();
            new EnterCommand(calculator, 3).execute();

            CalculatorCommand c1 = new ClearNumbersCommand(calculator);
            c1.execute();

            Assertions.assertEquals(0, calculator.result(),
                    "After executing ClearNumbersCommand, expected calculator result to be 0, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(3, calculator.result(),
                    "After undoing ClearNumbersCommand, expected top of stack to be restored to 3, but was " + calculator.result());

            c1.execute();
            Assertions.assertEquals(0, calculator.result(),
                    "After re-executing ClearNumbersCommand, expected calculator result to be 0, but was " + calculator.result());
        }

        @DisplayName("Test save memory command redo")
        @Test
        public void testSaveMemoryRedo() throws NoSuchFieldException, IllegalAccessException {
            CalculatorCommand c1 = new EnterCommand(calculator, 42);
            c1.execute();

            CalculatorCommand c2 = new SaveMemoryCommand(calculator);
            c2.execute();

            Field memoryField = Calculator.class.getDeclaredField("memory");
            memoryField.setAccessible(true);

            Assertions.assertEquals(42, memoryField.getLong(calculator),
                    "After executing SaveMemoryCommand, expected memory to be 42, but was " + memoryField.getLong(calculator));

            c2.undo();
            Assertions.assertEquals(0, memoryField.getLong(calculator),
                    "After undoing SaveMemoryCommand, expected memory to be restored to 0, but was " + memoryField.getLong(calculator));

            c2.execute();
            Assertions.assertEquals(42, memoryField.getLong(calculator),
                    "After re-executing SaveMemoryCommand, expected memory to be 42, but was " + memoryField.getLong(calculator));
        }

        @DisplayName("Test recall memory command redo")
        @Test
        public void testRecallMemoryRedo() throws NoSuchFieldException, IllegalAccessException {
            Field memoryField = Calculator.class.getDeclaredField("memory");
            memoryField.setAccessible(true);
            memoryField.setLong(calculator, 42);

            CalculatorCommand c1 = new RecallMemoryCommand(calculator);
            c1.execute();

            Assertions.assertEquals(42, calculator.result(),
                    "After executing RecallMemoryCommand, expected top of stack to be 42, but was " + calculator.result());

            c1.undo();
            Assertions.assertEquals(0, calculator.result(),
                    "After undoing RecallMemoryCommand, expected calculator result to be 0, but was " + calculator.result());

            c1.execute();
            Assertions.assertEquals(42, calculator.result(),
                    "After re-executing RecallMemoryCommand, expected top of stack to be 42, but was " + calculator.result());
        }

        @DisplayName("Test clear memory command redo")
        @Test
        public void testClearMemoryRedo() throws NoSuchFieldException, IllegalAccessException {
            Field memoryField = Calculator.class.getDeclaredField("memory");
            memoryField.setAccessible(true);
            memoryField.setLong(calculator, 42);

            CalculatorCommand c1 = new ClearMemoryCommand(calculator);
            c1.execute();

            Assertions.assertEquals(0, memoryField.getLong(calculator),
                    "After executing ClearMemoryCommand, expected memory to be 0, but was " + memoryField.getLong(calculator));

            c1.undo();
            Assertions.assertEquals(42, memoryField.getLong(calculator),
                    "After undoing ClearMemoryCommand, expected memory to be restored to 42, but was " + memoryField.getLong(calculator));

            c1.execute();
            Assertions.assertEquals(0, memoryField.getLong(calculator),
                    "After re-executing ClearMemoryCommand, expected memory to be 0, but was " + memoryField.getLong(calculator));
        }
    }
}
