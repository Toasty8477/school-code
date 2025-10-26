/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 12
 * Name: Alexander Horton
 * Last Updated: 11/24/2024
 */

package hortona;

/**
 * right triangle
 */
public class RightTriangle extends Triangle {
    /**
     * creates a right triangle
     * @param height height of the triangle
     * @param base base of the triangle
     */
    public RightTriangle(double height, double base) {
        super(height, base, Math.sqrt(Math.pow(height, 2) + Math.pow(base, 2)));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
