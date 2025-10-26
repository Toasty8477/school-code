/**************************
 * Course: CSC1110 - 110
 * Fall 2024
 * ASSIGNMENT # - REPLACE ME
 * Name: Alexander Horton
 * Created 9/23/2024
 **************************/

package hortona;

import java.util.Scanner;

public class VariableNameChecker {

    private static Boolean isLegal(String variableName) {
        boolean legal = false;
        boolean loop = true;

        if (Character.getType(variableName.charAt(0)) == 9 ) {
            loop = false;
        }

        for (int i = 0; i < variableName.length(); i++) {
            int charType = Character.getType(variableName.charAt(i));
            // Type 1 = uppercase, Type 2 = lowercase, Type 9 = digit, Type 26 = $, Type 23 = _ .
            if ((charType == 1 || charType == 2 || charType == 9 || charType == 26 || charType == 23) && loop){
                legal = true;
            } else {
                legal = false;
                loop = false;
            }
        }
        return legal;
    }

    private static Boolean isGoodStyle(String variableName) {
        boolean goodStyle = false;
        boolean loop = true;

        if (Character.getType(variableName.charAt(0)) != 2 ) {
            loop = false;
        }

        for (int i = 0; i < variableName.length(); i++) {
            int charType = Character.getType(variableName.charAt(i));
            if ((charType == 1 || charType == 2 || charType == 9) && loop) {
                goodStyle = true;
            } else {
                goodStyle = false;
                loop = false;
            }
        }
        return goodStyle;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        boolean running = true;

        System.out.println("This program checks the properness of a proposed java variable name.");
        do {
            System.out.print("Enter a variable name (q to quit): ");
            String input = in.nextLine();

            if (input.equalsIgnoreCase("q")){
                running = false;
            } else {
                boolean legal = isLegal(input);
                boolean goodStyle = isGoodStyle(input);

                if (legal && goodStyle) {
                    System.out.println("Good");
                } else if ( legal && !goodStyle) {
                    System.out.println("Legal, but poor style");
                } else {
                    System.out.println("Illegal");
                }
            }
        } while (running);
    }
}