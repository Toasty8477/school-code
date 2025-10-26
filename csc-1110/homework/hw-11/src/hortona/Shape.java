/*
 * Course: CS1110 - 111
 * Fall 2024
 * Homework 11 - Shapes
 * Name: Alexander Horton
 * Created: 11/17/2024
 */
package hortona;

import java.text.DecimalFormat;

/**
 * A base interface for different types of shapes
 */
public interface Shape {
    /**
     * formats decimals to two digits after the decimal place
     */
    DecimalFormat FORMATTER = new DecimalFormat("0.##");

    /**
     * calculate the area of the shape
     * @return area
     */
    double calculateArea();

    /**
     * calculates the perimeter of the shape
     * @return perimeter
     */
    double calculatePerimeter();
    @Override
    String toString();
}
