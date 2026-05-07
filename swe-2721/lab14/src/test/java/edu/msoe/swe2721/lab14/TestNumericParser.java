package edu.msoe.swe2721.lab14;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNumericParser {

    @DataProvider
    public Object[][] parseDP() {
        return new Object[][] {
                {"zero", 0, false},
                {"one", 1, false},
                {"two", 2, false},
                {"three", 3, false},
                {"four", 4, false},
                {"five", 5, false},
                {"six", 6, false},
                {"seven", 7, false},
                {"eight", 8, false},
                {"nine", 9, false},
                {"ten", 10, false},
                {"Zero", 0, false},
                {"zERo", 0, false},
                {"ZERO", 0, false},
                {"eleven", 0, true},
                {"AjfiorjospcjfE", 0, true},
                {"    zero", 0, false},
                {"zero    ", 0, false},
                {"ze    ro", 0, true},
                {null, 0, true}
        };
    }

    @Test(groups = { "numericParser" }, dataProvider = "parseDP")
    public void testParseString(String input, int expected, boolean exception) {
        // Arrange
        int actual = -1;
        // Act
        if (!exception) {
            try {
                actual = NumericParser.parseString(input);
            } catch (NumericParseException e) {
                fail(e.getMessage());
            }
            // Assert
            assertEquals(actual, expected);
        } else {
            assertThrows(NumericParseException.class, () -> NumericParser.parseString(input));
        }
    }
}
