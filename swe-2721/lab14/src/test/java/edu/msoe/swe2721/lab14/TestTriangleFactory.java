package edu.msoe.swe2721.lab14;

import static org.testng.Assert.assertEquals;

import java.util.Scanner;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestTriangleFactory {

    private TriangleFactory tf;

    @BeforeMethod(groups = {"triangleFactory"})
    public void setup() {
        tf = new TriangleFactory();
    }

    @AfterMethod
    public void byebye() {
        tf = null;
    }

    @DataProvider
    public Object[][] parseStreamDP() {
        return new Object[][] {
                // Valid triangles
                {"1 2 3", 1},
                {"2 2 2", 1},
                {"two 2 2", 1},
                {"2 two 2", 1},
                {"2 2 two", 1},
                {"two two 2", 1},
                {"2 two two", 1},
                {"two 2 two", 1},
                {"two two two", 1},
                // Multiple triangles
                {"1 1 1\n2 2 2", 2},
                // Invalid triangles
                {"1 2", 0},
                {"hello world", 0},
                {"2 2 2 2", 0},
                {"invalid 2 2", 0},
                {"2 invalid 2", 0},
                {"2 2 invalid", 0},
                // quit stuff
                {"quit", 0}
        };
    }

    @Test(groups = { "triangleFactory" }, dataProvider = "parseStreamDP")
    public void testParseStream(String stream, int expected) {
        // Arrange
        Scanner scanner = new Scanner(stream);
        // Act
        tf.parseStream(scanner);
        // Assert
        assertEquals(tf.getCount(), expected);
    }

    @Test(groups = { "triangleFactory" })
    public void testGetCount() {
        // Arrange
        Scanner scanner = new Scanner("2 2 2");
        int noneAdded;
        int someAdded;
        // Act
        noneAdded = tf.getCount();
        tf.parseStream(scanner);
        someAdded = tf.getCount();
        // Assert
        assertEquals(noneAdded, 0);
        assertEquals(someAdded, 1);
    }

    @DataProvider
    public Object[][] obtainTextDP() {
        return new Object[][] {
            // No triangles
            {"", ""},
            // one Triangle
            {"2 2 2", "a=2.000 b=2.000 c=2.000 area=1.732 EQUILATERAL"},
            // multiple triangles
            {"2 2 2\n4 5 6", "a=2.000 b=2.000 c=2.000 area=1.732 EQUILATERAL\na=4.000 b=5.000 c=6.000 area=9.922 SCALENE"}
        };
    }

    @Test(groups = { "triangleFactory" }, dataProvider = "obtainTextDP")
    public void testObtainText(String trianglesToAdd, String expected) {
        // Arrange
        Scanner scanner = new Scanner(trianglesToAdd);
        String actual;
        // Act
        tf.parseStream(scanner);
        actual = tf.obtainTriangleText();
        // Assert
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] obtainTotalAreaDP() {
        return new Object[][] {
            // No triangles
            {"", 0},
            // one Triangle
            {"2 2 2", 1.732},
            // multiple triangles
            {"2 2 2\n4 5 6", 1.732 + 9.922}
        };
    }

    @Test(groups = { "triangleFactory" }, dataProvider = "obtainTotalAreaDP")
    public void testObtainTotalArea(String trianglesToAdd, double expected) {
        // Arrange
        Scanner scanner = new Scanner(trianglesToAdd);
        double actual;
        // Act
        tf.parseStream(scanner);
        actual = tf.obtainTotalArea();
        // Assert
        assertEquals(actual, expected);
    }
}
