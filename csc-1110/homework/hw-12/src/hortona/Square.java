/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 12
 * Name: Alexander Horton
 * Last Updated: 11/24/2024
 */

package hortona;

/**
 * a square
 */
public class Square extends Rectangle {
    /**
     * creates a square
     * @param side the side length
     */
    public Square(double side) {
        super(side, side);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Color.CYAN);
        sb.append("Square");
        sb.append("\nSide: ");
        if (sideA - (int)sideA != 0) {
            sb.append(FORMATTER.format(sideA));
        } else {
            sb.append((int)sideA);
        }
        sb.append("\nArea: ");
        if (getArea() - (int)getArea() != 0) {
            sb.append(FORMATTER.format(getArea()));
        } else {
            sb.append((int)getArea());
        }
        sb.append("\n");
        return sb.toString();
    }
}
