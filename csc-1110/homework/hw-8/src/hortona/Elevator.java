/*
 * Course: CSC1110 - 110
 * Fall 2024
 * Homework 8
 * Name: Alexander Horton
 * Last Updated: 10/24/24
 */
package hortona;

import java.util.Scanner;

/**
 * Class that simulates an elevator going up and down
 * as well as a fire alarm.
 */
public class Elevator {
    private int currentFloor = 1;
    private final int minFloor = 1;
    private final int maxFloor = 100;

    /**
     * Prompts the user to enter a floor number.
     * If the floor is valid the elevator will go up or down to that floor.
     * @param scan Scanner
     */
    public void selectFloor(Scanner scan) {
        int requestedFloor;
        System.out.print("Enter the floor that you'd like to go to ==> ");
        if (scan.hasNextInt()) {
            requestedFloor = scan.nextInt();
            if (requestedFloor <= this.maxFloor && requestedFloor >= minFloor) {
                if (requestedFloor < currentFloor) {
                    goDown(requestedFloor);
                } else if (requestedFloor > currentFloor) {
                    goUp(requestedFloor);
                }
            } else {
                System.out.println("Invalid floor selection - must be between 1 and 100.");
            }
        } else {
            System.out.println("Invalid floor selection - must be between 1 and 100.");
            scan.nextLine();
        }
    }

    /**
     * Simulate pulling a fire alarm. Prints a danger message and
     * sends the elevator to the first floor of the building.
     */
    public void fireAlarm() {
        System.out.println("Danger! You must exit the building now!");
        if (currentFloor > minFloor) {
            goDown(minFloor);
        }
    }

    private void goUp(int requestedFloor) {
        System.out.print("Going up..");
        for (int i = currentFloor+1; i <= requestedFloor; i++) {
            System.out.print(i+"..");
        }
        System.out.println("Ding!");
        currentFloor = requestedFloor;
    }
    private void goDown(int requestedFloor) {
        System.out.print("Going down..");
        for (int i = currentFloor-1; i >= requestedFloor; i--) {
            System.out.print(i+"..");
        }
        System.out.println("Ding!");
        currentFloor = requestedFloor;
    }
}
