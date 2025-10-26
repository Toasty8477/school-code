/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 11 - Shapes
 * Name: Alexander Horton
 * Created 11/17/2024
 */

package hortona;

/**
 * A rectangle
 */
public class Rectangle implements Shape {
    private final int length;
    private final int width;

    /**
     * Creates a rectangle with the specified length and width
     * @param length length
     * @param width width
     */
    public Rectangle(int length, int width) {
        this.length = length;
        this.width = width;
    }
    public boolean isSquare() {
        return this.length == this.width;
    }

    @Override
    public double calculateArea() {
        return this.width * this.length;
    }
    @Override
    public double calculatePerimeter() {
        return (this.width * 2) + (this.length * 2);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (isSquare()) {
            sb.append(Color.BLUE);
            sb.append("Square\n");
        } else {
            sb.append(Color.RED);
            sb.append("Rectangle\n");
        }
        sb.append("length = ").append(this.length).append("\n");
        sb.append("width = ").append(this.width).append("\n");
        sb.append("area = ").append((int) calculateArea()).append("\n");
        sb.append("perimeter = ").append((int) calculatePerimeter()).append("\n");
        return sb.toString();
    }
}
