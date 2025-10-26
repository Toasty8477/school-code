/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 11 - Shapes
 * Name: Alexander Horton
 * Created 11/17/2024
 */
package hortona;

import java.util.ArrayList;

/**
 * Driver for Shape class
 */
public class Driver {
    public static void main(String[] args) {
        final int circleRadius = 4;
        final int rectangleLength = 4;
        final int rectangleWidth = 5;
        final int squareSide = 6;
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(circleRadius));
        shapes.add(new Rectangle(rectangleLength, rectangleWidth));
        shapes.add(new Rectangle(squareSide, squareSide));

        for(Shape s: shapes) {
            System.out.println(s.toString());
        }
    }
}
