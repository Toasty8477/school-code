package edu.msoe.swe2721.lab9;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

class ConsoleCalculatorTests {
    private File out;
    private PrintStream originalStream;
    private ConsoleCalculator calc;

    @BeforeMethod(alwaysRun = true)
    public void setup() throws FileNotFoundException {
        originalStream = System.out;
        out = new File("./out.txt");

        System.setOut(new PrintStream(out));
        calc = new ConsoleCalculator();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() throws IOException {
        System.setOut(originalStream);
        out.delete();
    }

    @DataProvider(name = "handleDoubleValueOperationsDP")
    public Object[][] handleDoubleValueOperationsDP() {
        return new Object[][] {
                { "printNumber", 1.500000, 0.0, true, "1.5" },
                { "printNumber", 1.500000, 0.0, false, "1.5" },
                { "printNumber", 1.0, 0.0, false, "1" },
                { "printNumber", 1.2938, 0.0, false, "1.2938" },

                { "printNewline", null, 0.0, false, "\n" },
                { "handleClear", null, 0.0, false, "\n" },

                { "setAndPrintCurrentResult", 100.88, 0.0, false, "100.88" },

                { "handleAdd", -1.0, 3.0, false, "+ -1\n2" },
                { "handleAdd", 0.0, 0.0, true, "+ 0\n0" },
                { "handleAdd", -1000.3, 1.0, false, "+ -1000.3\n-999.3" },

                { "handleSubtract", 0.0, 0.0, false, "- 0\n0" },
                { "handleSubtract", -1.0, 1.0, false, "- -1\n2" },
                { "handleSubtract", 1.0, -1.0, false, "- 1\n-2" },
                { "handleSubtract", 50.6, 100.78, false, "- 50.6\n50.18" },

                { "handleMultiply", 50.0, 10.0, false, "* 50\n500" },
                { "handleMultiply", 0.0, 0.0, false, "* 0\n0" },
                { "handleMultiply", -11.0, -5.0, false, "* -11\n55" },
                { "handleMultiply", 1.0, -5.0, false, "* 1\n-5" },

                { "handleDivide", 1.0, 0.0, false, "/ 1\n0" },
                { "handleDivide", 0.0, 1.0, false, "/ 0\nInfinity" },
                { "handleDivide", 2.0, 5.0, true, "/ 2\n2.5" },
                { "handleDivide", 2.1, 10.5, false, "/ 2.1\n5" },

                { "handleEquals", null, 0.0, false, " = 0\n" },
        };
    }

    @Test(groups = { "all" }, dataProvider = "handleDoubleValueOperationsDP")
    public void testOutputCheck(String methodName, Object value, Double currentResultValue, Boolean allIntegersValue,
            String expected)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException,
            IOException, NoSuchFieldException {

        Method m;

        if (value != null) {
            m = calc.getClass().getDeclaredMethod(methodName, double.class);
        } else {
            m = calc.getClass().getDeclaredMethod(methodName);
        }
        m.setAccessible(true);

        // set currentResult Field to currentResultValue
        Field currentResult = calc.getClass().getDeclaredField("currentResult");
        currentResult.setAccessible(true);
        currentResult.setDouble(calc, currentResultValue);

        // set allIntegers Field to allIntegersValue
        Field allIntegers = calc.getClass().getDeclaredField("allIntegers");
        allIntegers.setAccessible(true);
        allIntegers.setBoolean(calc, allIntegersValue);

        if (value != null) {
            m.invoke(calc, value);
        } else {
            m.invoke(calc);
        }

        String actual = Files.readString(out.toPath());
        assertEquals(actual, expected);
    }

    @DataProvider(name = "processLineDP")
    public Object[][] processLineDP() {
        return new Object[][] {
                { "processLine", "aaaa", true, "" },
                { "processLine", "a + b", true, "+ 0\n0" },

                // test every operand performs the correct operation
                { "processLine", "1 + 1", true, "1 + 1\n2" },
                { "processLine", "1 - 1", true, "1 - 1\n0" },
                { "processLine", "1 * 1", true, "1 * 1\n1" },
                { "processLine", "1 / 1", true, "1 / 1\n1" },
                { "processLine", "C", true, "\n" },
                { "processLine", "=", true, " = 0\n" },

                // test allIntegers is false
                { "processLine", "1 + 1.5", false, "1 + 1.5\n2.5" },
                { "processLine", "1.5 + 1.2", false, "1.5 + 1.2\n2.7" },
        };
    }

    @Test(groups = { "all" }, dataProvider = "processLineDP")
    public void testProcessLine(String methodName, String value, Boolean allIntegersExpected,
            String expected)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException,
            IOException, NoSuchFieldException {

        Method m;

        if (value != null) {
            m = calc.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            m = calc.getClass().getDeclaredMethod(methodName);
        }
        m.setAccessible(true);

        Field allIntegers = calc.getClass().getDeclaredField("allIntegers");
        allIntegers.setAccessible(true);

        if (value != null) {
            m.invoke(calc, value);
        } else {
            m.invoke(calc);
        }

        String actual = Files.readString(out.toPath());
        assertEquals(actual, expected);
        assertEquals(allIntegers.getBoolean(calc), allIntegersExpected);
    }

    // test that handleClear modifies private fields correctly
    @Test(groups = { "all" })
    public void testHandleClearModifiedFields() throws IllegalAccessException, InvocationTargetException,
            NoSuchFieldException, SecurityException, NoSuchMethodException {

        Method m = calc.getClass().getDeclaredMethod("handleClear");
        m.setAccessible(true);

        Field allIntegers = calc.getClass().getDeclaredField("allIntegers");
        Field currentResult = calc.getClass().getDeclaredField("currentResult");
        allIntegers.setAccessible(true);
        currentResult.setAccessible(true);

        m.invoke(calc);

        assertTrue(allIntegers.getBoolean(calc));
        assertEquals(currentResult.getDouble(calc), 0);
    }
}
