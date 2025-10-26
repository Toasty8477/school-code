/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 12
 * Name: Alexander Horton
 * Last Updated: 11/24/2024
 */

package hortona;


import java.text.DecimalFormat;

/**
 * Any type of shape
 */
public interface Shape {
    /**
     * Formats to two decimal places
     */
    DecimalFormat FORMATTER = new DecimalFormat("#.##");
    /**
     * a 90 degree angle
     */
    double RIGHT_ANGLE = 90;

    /**
     * finds the area of a shape
     * @return area
     */
    double getArea();
}
