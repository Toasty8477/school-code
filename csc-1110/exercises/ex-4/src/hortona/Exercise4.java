/*********************************
 * Course: CSC1110 - 110
 * Fall 2024
 * Exercise 4 - Methods Using Math
 * Name: Alexander Horton
 * Created 09/26/2024
 ********************************/

package hortona;

import java.util.Scanner;

/**
 * This class generates two random whole numbers between 1 and 10 and
 * uses those values as the two legs of a right triangle. It then
 * calculates the hypotenuse and the remaining two angles and
 * displays them.
 */
public class Exercise4 {
    private static final double RIGHT_ANGLE = 90.0;
    public static void main(String[] args) {
        boolean running = true;
        Scanner in = new Scanner(System.in);
        do {
            // randomly choose side a
            double a = generateSide();
            // randomly choose side b
            double b = generateSide();
            // calculate hypotenuse
            double h = calculateHypotenuse(a, b);
            // calculate angle ah
            double angleAH = calculateAngle(a, h);
            // calculate angle bh
            double angleBH = RIGHT_ANGLE - angleAH;
            // report using printf
            report(a, b, h, angleAH, angleBH);

            System.out.print("Would you like to go again (y/n): ");
            String again = in.next();
            if (!again.equalsIgnoreCase("y")) {
                running = false;
            }
        } while (running);
        System.out.println("Goodbye");
    }

    private static int generateSide() {
        return (int)(Math.random() * 10) + 1;
    }

    private static double calculateHypotenuse(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

    }

    private static double calculateAngle(double x, double h) {
        return Math.toDegrees(Math.acos(x/h));
    }

    private static void report(double a, double b, double h, double angleAH, double angleBH) {
        System.out.printf("Sides are %.1f and %.1f.\n", a, b);
        System.out.printf("Hypotenuse is %.2f.\n", h);
        System.out.printf("Angle between hypotenuse and side %.1f is %.2f.\n", a, angleAH);
        System.out.printf("Angle between hypotenuse and side %.1f is %.2f.\n", b, angleBH);
    }
}