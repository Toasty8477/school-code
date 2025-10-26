/*
 * Course: CSC1110 - FIXME
 * Fall 2023
 * Homework 12 - Shapes with Inheritance
 * Name: FIXME
 * Created: FIXME
 */
package test;

import hortona.*;

import java.util.ArrayList;

/**
 * Test Driver for the Shapes Homework
 */
public class TestDriver {
    public static void main(String[] args) {
        // CHECKSTYLE: OFF
        // Create shapes (ignoring magic numbers)
        Shape s1 = new Parallelogram(24, 16, 65);
        Shape s2 = new RightTriangle(3, 4);
        Shape s3 = new Rectangle(6.8, 4.3);
        Shape s4 = new Triangle(5, 7, 9);
        Shape s5 = new Circle(6);
        Shape s6 = new Square(5);

        // Add the shapes to a list
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(s1);
        shapes.add(s2);
        shapes.add(s3);
        shapes.add(s4);
        shapes.add(s5);
        shapes.add(s6);

        // Print all the shapes
        for(Shape s : shapes) {
            System.out.println(s);
            System.out.print(Color.RESET);
        }

        // Shapes with the same area from the same inheritance hierarchy
        Shape s7 = new Rectangle(8, 8);
        Shape s8 = new Square(8);
        Shape s9 = new Rectangle(4, 16);

        String sameArea = s7.getArea() == s8.getArea() ? "the same area." : Color.RED + "different areas.";
        System.out.println("Shape s7 and s8 have " + sameArea + Color.RESET);
        sameArea = s8.getArea() == s9.getArea() ? "the same area." : Color.RED + "different areas.";
        System.out.println("Shape s8 and s9 have " + sameArea + Color.RESET);
        sameArea = s3.getArea() == s9.getArea() ? "the same area." : Color.RED + "different areas.";
        System.out.println("Shape s3 and s9 have " + sameArea + Color.RESET);
        String equal = s7.equals(s8) ? "" : Color.RED + "not ";
        System.out.println("Shape s7 and s8 are " + equal + Color.RESET + "the same.");
        equal = s8.equals(s9) ? "" : Color.RED + "not ";
        System.out.println("Shape s8 and s9 are " + equal + Color.RESET + "the same.");
        equal = s5.equals(s9) ? "" : Color.RED + "not ";
        System.out.println("Shape s5 and s9 are " + equal + Color.RESET + "the same.");
        // CHECKSTYLE: ON
    }
}
