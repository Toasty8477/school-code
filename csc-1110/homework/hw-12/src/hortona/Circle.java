/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 12
 * Name: Alexander Horton
 * Last Updated: 11/24/2024
 */

package hortona;

/**
 * circle
 */
public class Circle implements Shape {
    private final double radius;

    /**
     * creates a circle
     * @param radius radius of the circle
     */
    public Circle(double radius) {
        this.radius = radius;
    }
    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Color.BLACK);
        sb.append("Circle");
        sb.append("\nRadius: ");
        if (radius - (int)radius != 0) {
            sb.append(FORMATTER.format(radius));
        } else {
            sb.append((int)radius);
        }
        sb.append("\nArea: ");
        sb.append(FORMATTER.format(getArea()));
        sb.append("\n");
        return sb.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Triangle) {
            return this.getArea() == ((Triangle)o).getArea();
        } else {
            return false;
        }
    }
}
