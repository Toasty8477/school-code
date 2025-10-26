/*
 * Course: CSC1110 - 110
 * Fall 2024
 * Homework 8
 * Name: Alexander Horton
 * Last Updated: 10/24/2024
 */
package hortona;

import java.util.Scanner;

/**
 * Driver for the elevator class
 */
public class ElevatorDriver {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Elevator elevator = new Elevator();
        boolean run = true;
        System.out.println("Welcome to Alex's elevator simulator!");
        do {
            String option;
            System.out.println("Options: (s)elect a floor, (f)ire alarm, (q)uit");
            System.out.print("Enter s, f, or q ==> ");
            option = scan.next();
            if (option.equalsIgnoreCase("s")) {
                elevator.selectFloor(scan);
            } else if (option.equalsIgnoreCase("f")) {
                elevator.fireAlarm();
            } else if (option.equalsIgnoreCase("q")) {
                run = false;
            } else {
                System.out.println("Invalid Selection.");
            }
            scan.nextLine();
        } while (run);
    }
}
