/************************
 * Course: CSC1110 - 110
 * Fall 2024
 * Lab 4 - Calculating Pi
 * Name: Alexander Horton
 * Created 9/27/2024
 ************************/

package hortona;

import java.util.Scanner;

public class PiEstimate {

    public static void drawCircle(int radius){
        int x;
        int y;
        StringBuilder sb = new StringBuilder();

        for(y = radius; y>0; y--){
            for(x = 1; x<=radius; x++){
                double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                if (distance - 0.5 < radius) {
                    sb.append("*");
                } else {
                    sb.append("-");
                }
            }
            sb.append("\n");

        }
        System.out.println(sb.toString());

    }

    public static double calculatePi(int radius, boolean print){
        int x = radius;
        int y = radius;
        int filled = 0;
        int total = (int) Math.pow(radius, 2);

        for(x = 0; x<(radius); x++){
            for(y = 0; y<(radius); y++){
                double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                if (distance < (radius - 0.5)) {
                    filled++;
                }
            }
        }
        if (print) {
            System.out.printf("Calculated pi is 4 * (%d/%d) = %.16f\n", filled, total, 4 * ((double) filled / total));
        }
        return 4 * ((double) filled / total);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int radius;
        double error;
        boolean withinError = false;
        int i = 0;

        System.out.print("Enter the number of pixels for the width of your circle: ");
        while (!scan.hasNextInt()) {
            System.out.println("Not an integer.");
            scan.nextLine();
            System.out.print("Enter an integer radius: ");
        }
        radius = scan.nextInt();

        drawCircle(radius);
        calculatePi(radius, true);

        System.out.print("Enter the minimum desired error for the estimate of PI: ");
        while (!scan.hasNextDouble()) {
            System.out.println("Not an double.");
            scan.nextLine();
            System.out.print("Enter the minimum desired error for the estimate of PI: ");
        }
        error = scan.nextDouble();

        while (!withinError) {
            if (calculatePi(i, false) < Math.PI + error) {
                withinError = true;
            } else {
                i++;
            }
        }
        System.out.printf("An estimate of %.16f was achieved with a radius of %d", calculatePi(i,false), i);
    }
}