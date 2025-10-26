/**************************
 * Course: CSC1110 - 110
 * Fall 2024
 * ASSIGNMENT # - REPLACE ME
 * Name: Alexander Horton
 * Created 9/30/2024
 **************************/

package hortona;

/**
 * simple driver the test the square class
 */
public class SquareDriver {
    public static void main(String[] args) {
        Square s1 = new Square();
        Square s2 = new Square();

        // Set the values of s1 with setPerimeter and setColor
        s1.setPerimeter(10);
        s1.setColor("red");
        // Set values of s2 with setValues
        s2.setValues(15, "blue");
        // Print out the color, perimeter, and area of s1 using their respective methods
        System.out.println("Square 1");
        System.out.println("\tColor: "+s1.getColor());
        System.out.println("\tPerimeter: "+s1.getPerimeter());
        System.out.println("\tArea: "+s1.calcArea());
        // Print out the color, perimeter, and area of s2 using printValues method
        System.out.println("Square 2");
        s2.printValues();
        // check if area of s1 is bigger than s2 and print the result
        System.out.println("Is Square 1 bigger than Square 2");
        System.out.println(s1.biggerThan(s2));
        // check if area of s2 is bigger than s1 and print the result
        System.out.println("Is Square 2 bigger than Square 1");
        System.out.println(s2.biggerThan(s1));
    }
}
