package test;

//import calc.*;

import org.junit.jupiter.api.*;
import calculator.*;

public class CalculatorManagerTest {
    private Calculator calculator;
    private CalculatorManager manager;

    @BeforeEach
    public void setupManager() {
        calculator = new Calculator();
        manager = new CalculatorManager(calculator);
    }

    @DisplayName("Basic operations through manager")
    @Nested
    class TestBasicOperations {

        @DisplayName("Test entering two numbers")
        @Test
        public void testEnteringTwoNumbers() {
            manager.enterNumber(2);
            manager.enterNumber(3);

            Assertions.assertEquals("Display: 3", manager.display(),
                    "After entering 2 then 3, the display should show the top of the stack as 3.");

            calculator.removeNumber();
            Assertions.assertEquals("Display: 2", manager.display(),
                    "After removing the top value directly from the calculator, the display should show 2.");

            calculator.removeNumber();
            Assertions.assertEquals("Display: 0", manager.display(),
                    "After removing all values directly from the calculator, the display should show 0.");
        }

        @DisplayName("Test adding two numbers")
        @Test
        public void testAddingTwoNumbers() {
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.addNumbers();

            Assertions.assertEquals("Display: 5", manager.display(),
                    "After entering 2 and 3 and calling addNumbers(), the display should show 5.");
        }

        @DisplayName("Test subtracting two numbers")
        @Test
        public void testSubtractingTwoNumbers() {
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.subtractNumbers();

            Assertions.assertEquals("Display: -1", manager.display(),
                    "After entering 2 and 3 and calling subtractNumbers(), the display should show -1.");
        }

        @DisplayName("Test multiplying two numbers")
        @Test
        public void testMultiplyTwoNumbers() {
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.multiplyNumbers();

            Assertions.assertEquals("Display: 6", manager.display(),
                    "After entering 2 and 3 and calling multiplyNumbers(), the display should show 6.");
        }

        @DisplayName("Test dividing two numbers")
        @Test
        public void testDivideTwoNumbers() {
            manager.enterNumber(6);
            manager.enterNumber(2);
            manager.divideNumbers();

            Assertions.assertEquals("Display: 3", manager.display(),
                    "After entering 6 and 2 and calling divideNumbers(), the display should show 3.");

            manager.enterNumber(4);
            manager.divideNumbers();

            Assertions.assertEquals("Display: 0", manager.display(),
                    "After dividing 3 by 4 using integer division, the display should show 0.");
        }

        @DisplayName("Test 5,2,12,3,1+/-* -> 5 * (2 - (12 / (1+3)))")
        @Test
        public void testMultipleOperations() {
            manager.enterNumber(5);
            manager.enterNumber(2);
            manager.enterNumber(12);
            manager.enterNumber(3);
            manager.enterNumber(1);

            manager.addNumbers();      // 1 + 3 = 4
            Assertions.assertEquals("Display: 4", manager.display(),
                    "After computing (1 + 3), the display should show 4.");

            manager.divideNumbers();   // 12 / 4 = 3
            Assertions.assertEquals("Display: 3", manager.display(),
                    "After computing (12 / 4), the display should show 3.");

            manager.subtractNumbers(); // 2 - 3 = -1
            Assertions.assertEquals("Display: -1", manager.display(),
                    "After computing (2 - 3), the display should show -1.");

            manager.multiplyNumbers(); // 5 * -1 = -5
            Assertions.assertEquals("Display: -5", manager.display(),
                    "After computing 5 * (2 - (12 / (1 + 3))), the display should show -5.");
        }

        @DisplayName("Clearing display")
        @Test
        public void testClearDisplay() {
            manager.enterNumber(1);
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.enterNumber(4);
            manager.enterNumber(5);

            manager.clearNumbers();

            Assertions.assertEquals("Display: 0", manager.display(),
                    "After calling clearDisplay(), the calculator stack should be cleared and the display should show 0.");
        }
    }

    @DisplayName("Memory operations through manager")
    @Nested
    class MemoryTests {

        @DisplayName("Testing save to memory")
        @Test
        public void testSaveToMemory() {
            manager.enterNumber(42);
            manager.saveMemory();

            Assertions.assertEquals("Display: 42 [Mem: 42]", manager.display(),
                    "After saving 42 to memory, the display should show the current value and memory indicator.");
        }

        @DisplayName("Testing recall from memory")
        @Test
        public void testRecallFromMemory() {
            manager.enterNumber(42);
            manager.saveMemory();
            manager.clearNumbers();

            Assertions.assertEquals("Display: 0 [Mem: 42]", manager.display(),
                    "After clearing the display, the stack should be empty but memory should still show 42.");

            manager.recallMemory();

            Assertions.assertEquals("Display: 42 [Mem: 42]", manager.display(),
                    "After recalling memory, the display should show 42 and still indicate memory is 42.");
        }

        @DisplayName("Testing clear memory")
        @Test
        public void testClearMemory() {
            manager.enterNumber(67);
            manager.saveMemory();
            manager.clearMemory();

            Assertions.assertEquals("Display: 67", manager.display(),
                    "After clearing memory, the display should still show the current stack value and no memory indicator.");
        }

        @DisplayName("Testing reset all clears both stack and memory")
        @Test
        public void testResetAll() {
            manager.enterNumber(42);
            manager.saveMemory();
            manager.enterNumber(99);

            manager.resetAll();

            Assertions.assertEquals("Display: 0", manager.display(),
                    "After resetAll(), both the stack and memory should be cleared, so the display should show 0 with no memory indicator.");
        }
    }

    @DisplayName("Undo operations through manager")
    @Nested
    class TestUndoOperations {

        @DisplayName("Test entering two numbers and undo")
        @Test
        public void testEnteringTwoNumbersUndo() {
            manager.enterNumber(2);
            manager.enterNumber(3);

            Assertions.assertEquals("Display: 3", manager.display(),
                    "After entering 2 then 3, the display should show 3.");

            manager.undo();
            Assertions.assertEquals("Display: 2", manager.display(),
                    "After undoing the second enter, the display should show 2.");

            manager.undo();
            Assertions.assertEquals("Display: 0", manager.display(),
                    "After undoing both enters, the stack should be empty and the display should show 0.");
        }

        @DisplayName("Test adding two numbers and undo")
        @Test
        public void testAddingTwoNumbersUndo() {
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.addNumbers();

            Assertions.assertEquals("Display: 5", manager.display(),
                    "After adding 2 and 3, the display should show 5.");

            manager.undo();
            Assertions.assertEquals("Display: 3", manager.display(),
                    "After undoing the addition, the original operands should be restored and the display should show 3.");

            manager.undo();
            Assertions.assertEquals("Display: 2", manager.display(),
                    "After undoing the second enter, the display should show 2.");

            manager.undo();
            Assertions.assertEquals("Display: 0", manager.display(),
                    "After undoing all commands, the display should show 0.");
        }

        @DisplayName("Test subtracting two numbers and undo")
        @Test
        public void testSubtractingTwoNumbersUndo() {
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.subtractNumbers();

            Assertions.assertEquals("Display: -1", manager.display(),
                    "After subtracting 3 from 2, the display should show -1.");

            manager.undo();
            Assertions.assertEquals("Display: 3", manager.display(),
                    "After undoing subtraction, the original operands should be restored and the display should show 3.");
        }

        @DisplayName("Test multiplying two numbers and undo")
        @Test
        public void testMultiplyTwoNumbersUndo() {
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.multiplyNumbers();

            Assertions.assertEquals("Display: 6", manager.display(),
                    "After multiplying 2 and 3, the display should show 6.");

            manager.undo();
            Assertions.assertEquals("Display: 3", manager.display(),
                    "After undoing multiplication, the original operands should be restored and the display should show 3.");
        }

        @DisplayName("Test dividing two numbers and undo")
        @Test
        public void testDivideTwoNumbersUndo() {
            manager.enterNumber(6);
            manager.enterNumber(2);
            manager.divideNumbers();

            Assertions.assertEquals("Display: 3", manager.display(),
                    "After dividing 6 by 2, the display should show 3.");

            manager.undo();
            Assertions.assertEquals("Display: 2", manager.display(),
                    "After undoing division, the original operands should be restored and the display should show 2.");
        }

        @DisplayName("Test clear display undo")
        @Test
        public void testClearDisplayUndo() {
            manager.enterNumber(1);
            manager.enterNumber(2);
            manager.enterNumber(3);

            manager.clearNumbers();
            Assertions.assertEquals("Display: 0", manager.display(),
                    "After clearDisplay(), the display should show 0.");

            manager.undo();
            Assertions.assertEquals("Display: 3", manager.display(),
                    "After undoing clearDisplay(), the original stack should be restored and the display should show 3.");
        }

        @DisplayName("Test reset all undo")
        @Test
        public void testResetAllUndo() {
            manager.enterNumber(42);
            manager.saveMemory();
            manager.enterNumber(7);

            manager.resetAll();
            Assertions.assertEquals("Display: 0", manager.display(),
                    "After resetAll(), the display should show 0 and memory should be cleared.");

            manager.undo();
            Assertions.assertEquals("Display: 7 [Mem: 42]", manager.display(),
                    "After undoing resetAll(), both the stack and memory should be restored.");
        }
    }

    @DisplayName("Redo operations through manager")
    @Nested
    class TestRedoOperations {

        @DisplayName("Test enter redo")
        @Test
        public void testEnterRedo() {
            manager.enterNumber(42);
            Assertions.assertEquals("Display: 42", manager.display(),
                    "After entering 42, the display should show 42.");

            manager.undo();
            Assertions.assertEquals("Display: 0", manager.display(),
                    "After undoing the enter, the display should show 0.");

            manager.redo();
            Assertions.assertEquals("Display: 42", manager.display(),
                    "After redoing the enter, the display should show 42 again.");
        }

        @DisplayName("Test add redo")
        @Test
        public void testAddRedo() {
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.addNumbers();

            Assertions.assertEquals("Display: 5", manager.display(),
                    "After adding 2 and 3, the display should show 5.");

            manager.undo();
            Assertions.assertEquals("Display: 3", manager.display(),
                    "After undoing addition, the display should show the restored top operand 3.");

            manager.redo();
            Assertions.assertEquals("Display: 5", manager.display(),
                    "After redoing addition, the display should show 5 again.");
        }

        @DisplayName("Test redo is cleared after a new command")
        @Test
        public void testRedoClearedAfterNewCommand() {
            manager.enterNumber(2);
            manager.enterNumber(3);
            manager.addNumbers(); // 5

            manager.undo(); // back to 2,3
            Assertions.assertEquals("Display: 3", manager.display(),
                    "After undoing addition, the display should show 3.");

            manager.enterNumber(10); // new command should clear redo history
            Assertions.assertEquals("Display: 10", manager.display(),
                    "After entering a new number after undo, the display should show 10.");

            manager.redo(); // should do nothing
            Assertions.assertEquals("Display: 10", manager.display(),
                    "Redo should do nothing after a new command has been executed because redo history should be cleared.");
        }

        @DisplayName("Test reset all redo")
        @Test
        public void testResetAllRedo() {
            manager.enterNumber(42);
            manager.saveMemory();
            manager.enterNumber(7);

            manager.resetAll();
            manager.undo();

            Assertions.assertEquals("Display: 7 [Mem: 42]", manager.display(),
                    "After undoing resetAll(), the original stack and memory should be restored.");

            manager.redo();
            Assertions.assertEquals("Display: 0", manager.display(),
                    "After redoing resetAll(), both the stack and memory should be cleared again.");
        }
    }

    @DisplayName("Error state behavior")
    @Nested
    class ErrorStateTests {

        @DisplayName("Divide by zero should put manager into error state")
        @Test
        public void testDivideByZeroTriggersErrorState() {
            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            Assertions.assertEquals("Error!!!", manager.display(),
                    "Dividing by zero should place the manager into error state and display should show Error!!!");
        }

        @DisplayName("While in error state, enterNumber should do nothing")
        @Test
        public void testEnterBlockedDuringErrorState() {
            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            manager.enterNumber(5);

            Assertions.assertEquals("Error!!!", manager.display(),
                    "While in error state, enterNumber() should not execute and the display should remain Error!!!");
        }

        @DisplayName("While in error state, addNumbers should do nothing")
        @Test
        public void testAddBlockedDuringErrorState() {
            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            manager.addNumbers();

            Assertions.assertEquals("Error!!!", manager.display(),
                    "While in error state, addNumbers() should not execute and the display should remain Error!!!");
        }

        @DisplayName("While in error state, memory commands should do nothing")
        @Test
        public void testMemoryCommandsBlockedDuringErrorState() {
            manager.enterNumber(42);
            manager.saveMemory();

            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            manager.recallMemory();
            Assertions.assertEquals("Error!!!", manager.display(),
                    "While in error state, recallFromMemory() should not execute and the display should remain Error!!!");

            manager.clearMemory();
            Assertions.assertEquals("Error!!!", manager.display(),
                    "While in error state, clearFromMemory() should not execute and the display should remain Error!!!");

            manager.saveMemory();
            Assertions.assertEquals("Error!!!", manager.display(),
                    "While in error state, saveToMemory() should not execute and the display should remain Error!!!");
        }

        @DisplayName("While in error state, undo and redo should do nothing")
        @Test
        public void testUndoRedoBlockedDuringErrorState() {
            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            manager.undo();
            Assertions.assertEquals("Error!!!", manager.display(),
                    "While in error state, undo() should do nothing and the display should remain Error!!!");

            manager.redo();
            Assertions.assertEquals("Error!!!", manager.display(),
                    "While in error state, redo() should do nothing and the display should remain Error!!!");
        }

        @DisplayName("clearDisplay should clear error state")
        @Test
        public void testClearDisplayClearsErrorState() {
            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            manager.clearNumbers();

            Assertions.assertEquals("Display: 0", manager.display(),
                    "clearDisplay() should clear the error state and clear the stack, so the display should show 0.");
        }

        @DisplayName("resetAll should clear error state")
        @Test
        public void testResetAllClearsErrorState() {
            manager.enterNumber(42);
            manager.saveMemory();

            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            manager.resetAll();

            Assertions.assertEquals("Display: 0", manager.display(),
                    "resetAll() should clear the error state, clear the stack, and clear memory, so the display should show 0.");
        }

        @DisplayName("After clearDisplay clears error state, normal commands should work again")
        @Test
        public void testCommandsWorkAfterClearDisplay() {
            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            manager.clearNumbers();
            manager.enterNumber(7);

            Assertions.assertEquals("Display: 7", manager.display(),
                    "After clearDisplay() clears the error state, normal commands should work again and the display should show 7.");
        }

        @DisplayName("After resetAll clears error state, normal commands should work again")
        @Test
        public void testCommandsWorkAfterResetAll() {
            manager.enterNumber(10);
            manager.enterNumber(0);
            manager.divideNumbers();

            manager.resetAll();
            manager.enterNumber(9);

            Assertions.assertEquals("Display: 9", manager.display(),
                    "After resetAll() clears the error state, normal commands should work again and the display should show 9.");
        }
    }
}