package msoe.swe2721.lab3;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

import java.security.InvalidParameterException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestA {

    private NumericStringConverterPartA a;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            a = new NumericStringConverterPartA();
        } catch (Exception ex) {
            fail();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        a = null;
    }

    @Test(groups = { "all", "testa" })
    public void noReach() {
        // Act and Assert
        assertThrows(InvalidParameterException.class, () -> a.convertNumbersToText(null));
    }

    @Test(groups = { "all", "testa" })
    public void reach() {
        // Arrange
        int actual;
        int expected = 1;
        // Act
        a.convertNumbersToText("Dr. 1. ABC");
        actual = a.getDigitCount();
        // Assert
        assertEquals(actual, expected);
    }

    @Test(groups = { "all", "testa" })
    public void infect() {
        // Arrange
        int actual;
        int expected = 3;
        // Act
        a.convertNumbersToText("3.14");
        actual = a.getDigitCount();
        // Assert
        assertEquals(actual, expected);
    }

    @Test(groups = { "all", "testa" })
    public void reveal() {
        // Arrange
        int actual;
        int expected = 5;
        // Act
        a.convertNumbersToText("1.5");
        actual = a.getDigitCount();
        // Assert
        assertEquals(actual, expected);
    }
}
