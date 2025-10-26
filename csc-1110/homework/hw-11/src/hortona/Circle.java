/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 11 - Shapes
 * Name: Alexander Horton
 * Created 11/17/2024
 */

package hortona;

/**
 * A circle
 */
public class Circle implements Shape {

    private final int radius;

    /**
     * creates a circle with the specified radius
     * @param radius radius
     */
    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }
    @Override
    public double calculatePerimeter() {
        return 2*Math.PI*this.radius;
    }
    @Override
    public String toString() {
        return Color.GREEN +
                "Circle\n" +
                "radius = " + this.radius + "\n" +
                "area = " + FORMATTER.format(calculateArea()) + "\n" +
                "perimeter = " + FORMATTER.format(calculatePerimeter()) + "\n" +
                Color.RESET;
    }
}
