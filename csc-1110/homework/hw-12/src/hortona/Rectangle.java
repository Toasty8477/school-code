/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 12
 * Name: Alexander Horton
 * Last Updated: 11/24/2024
 */

package hortona;

/**
 * rectangle
 */
public class Rectangle extends Parallelogram {
    /**
     * creates a rectangle
     * @param longerSide longer side length
     * @param shorterSide shorter side length
     */
    public Rectangle(double longerSide, double shorterSide) {
        super(longerSide, shorterSide, RIGHT_ANGLE);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Color.GREEN);
        sb.append("Rectangle");
        sb.append("\nLonger Side: ");
        if (sideA - (int)sideA != 0) {
            sb.append(FORMATTER.format(sideA));
        } else {
            sb.append((int)sideA);
        }
        sb.append("\nShorter Side: ");
        if (sideB - (int)sideB != 0) {
            sb.append(FORMATTER.format(sideB));
        } else {
            sb.append((int)sideB);
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
