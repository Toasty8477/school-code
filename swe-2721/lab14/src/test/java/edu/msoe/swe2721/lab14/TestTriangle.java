package edu.msoe.swe2721.lab14;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.lang.reflect.Field;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestTriangle {
    private Triangle triangle;

    @AfterMethod
    public void byebye() {
        triangle = null;
    }

    @DataProvider
    public Object[][] constructorDP() {
        return new Object[][] {
                { 1.0, 2.0, 3.0, false },

                { 0.0, 2.0, 3.0, true },
                { 5.5, 0.0, 3.0, true },
                { 5.5, 1.0, 0.0, true },
                { 5.5, 0.0, 0.0, true },
                { 0.0, 0.0, 0.0, true },
                { -1.0, 1.0, 1.0, true },
                { 1.0, -1.0, 1.0, true },
                { 1.0, 1.0, -1.0, true },
                { 1.0, -1.0, -1.0, true },
                { -1.0, -1.0, -1.0, true },
        };
    }

    @Test(groups = { "triangle" }, dataProvider = "constructorDP")
    public void testConstructor(double a, double b, double c, boolean exception) {
        // Assert
        if (exception) {
            assertThrows(TriangleConstructionException.class, () -> new Triangle(a, b, c));
        } else {
            try {
                new Triangle(a, b, c);
            } catch (TriangleConstructionException e) {
                Assert.fail("Exception was thrown but not expected");
            }
        }
    }

    @DataProvider
    public Object[][] obtainTextualRepresentationDP() {
        return new Object[][] {
                { 1.0, 1.0, 1.0, "a=1.000 b=1.000 c=1.000 area=0.433 EQUILATERAL" },
                { 1.0, 1.0, 6.0, "a=1.000 b=1.000 c=6.000 area=0.000 INVALID" },
                { 3.0, 4.0, 5.0, "a=3.000 b=4.000 c=5.000 area=6.000 SCALENE" },
                { 2.0, 2.0, 1.0, "a=2.000 b=2.000 c=1.000 area=0.968 ISOSCELES" },
        };
    }

    @Test(groups = { "triangle" }, dataProvider = "obtainTextualRepresentationDP")
    public void testObtainTextualRepresentation(double a, double b, double c, String expected)
            throws TriangleConstructionException, NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        // Arrange
        triangle = new Triangle(a, b, c);

        // Assert
        assertEquals(triangle.obtainTextualRepresentation(), expected);
    }

    @Test(groups = { "triangle" }, dataProvider = "obtainTextualRepresentationDP")
    public void testObtainTextualRepresentationReflection(double a, double b, double c, String expected)
            throws TriangleConstructionException, NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        // Arrange
        triangle = new Triangle(a, b, c);

        // grab the three side fields
        Field A = Triangle.class.getDeclaredField("a");
        Field B = Triangle.class.getDeclaredField("b");
        Field C = Triangle.class.getDeclaredField("c");
        A.setAccessible(true);
        B.setAccessible(true);
        C.setAccessible(true);
        double aValue = A.getDouble(triangle);
        double bValue = B.getDouble(triangle);
        double cValue = C.getDouble(triangle);

        // generate the expected textual representation
        String expectedInternal = String.format("a=%.3f b=%.3f c=%.3f area=%.3f %s", aValue, bValue, cValue,
                triangle.calculateArea(),
                triangle.determineTriangleType());

        // Assert
        assertEquals(triangle.obtainTextualRepresentation(), expectedInternal);
    }

    @DataProvider
    public Object[][] calculateAreaDP() {
        return new Object[][] {
                { 1.0, 1.0, 1.0, 0.4330 },
                { 1.0, 1.0, 100.0, 0.0 },
                { 3.0, 4.0, 5.0, 6.0 },
                { 0.011, 0.011, 0.011, 0.0001}
        };
    }

    @Test(groups = { "triangle" }, dataProvider = "calculateAreaDP")
    public void testCalculateArea(double a, double b, double c, double expected)
            throws TriangleConstructionException {
        // Arrange
        triangle = new Triangle(a, b, c);

        // Act
        String formattedActual = String.format("%.4f", triangle.calculateArea());
        String formatedExpected = String.format("%.4f", expected);

        // Assert
        assertEquals(formattedActual, formatedExpected);
    }

    @DataProvider
    public Object[][] calculatePerimeterDP() {
        return new Object[][] {
                { 1.0, 1.0, 1.0, 3.0000 },
                { 1.0, 1.9128, 100.0, 0.0 },
                { 0.001, 0.001, 0.001, 0.003}
        };
    }

    @Test(groups = { "triangle" }, dataProvider = "calculatePerimeterDP")
    public void testCalculatePerimeter(double a, double b, double c, double expected)
            throws TriangleConstructionException {
        // Arrange
        triangle = new Triangle(a, b, c);

        // Act
        String formattedActual = String.format("%.4f", triangle.calculatePerimeter());
        String formatedExpected = String.format("%.4f", expected);

        // Assert
        assertEquals(formattedActual, formatedExpected);
    }

    @DataProvider
    public Object[][] determineTriangleTypeDP() {
        return new Object[][] {
                { 10.0, 10.0, 10.0, TriangleType.EQUILATERAL },
                { 2.2, 3.3, 4.4, TriangleType.SCALENE },
                { 1.0, 2.0, 1.0, TriangleType.ISOSCELES},
                { 10.0, 0.5, 1.0, TriangleType.INVALID },
        };
    }

    @Test(groups = { "triangle" }, dataProvider = "determineTriangleTypeDP")
    public void testDetermineTriangleType(double a, double b, double c, TriangleType expected)
            throws TriangleConstructionException {
        // Arrange
        triangle = new Triangle(a, b, c);

        // Assert
        assertEquals(triangle.determineTriangleType(), expected);
    }

    @DataProvider
    public Object[][] determineIfValidDP() {
        return new Object[][] {
                // a + b > c
                { 10.0, 10.0, 1.0, true },
                // a + b < c
                { 10.0, 10.0, 300.0, false },
                // a + c > b
                { 2.5, 0.0011, 0.016, false },
                // a + c < b
                { 2.5, 3.5, 0.016, false },
                // b + c > a
                { 0.1, 0.5, 0.5, true },
                // b + c < a
                { 1.001, 0.5, 0.5, false },
        };
    }

    @Test(groups = { "triangle" }, dataProvider = "determineIfValidDP")
    public void testDetermineIfValid(double a, double b, double c, boolean expected)
            throws TriangleConstructionException {
        // Arrange
        triangle = new Triangle(a, b, c);

        // Assert
        assertEquals(triangle.determineIfValid(), expected);
    }
}
