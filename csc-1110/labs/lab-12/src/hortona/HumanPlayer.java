/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 12 - The Game of Pig
 * Name: Alexander Horton
 * Created: 11/22/2024
 */

package hortona;

import java.util.Scanner;

/**
 * a human player
 */
public class HumanPlayer extends Player {
    private final Scanner in;

    /**
     * Creates a human player
     * @param name name for the player
     * @param in a scanner for user input
     */
    public HumanPlayer(String name, Scanner in) {
        super(name);
        this.in = in;
    }
    @Override
    public boolean chooseToHold(int turnScore) {
        System.out.print("Hold? [y/n]");
        String choice = in.next();
        return choice.equalsIgnoreCase("y");
    }
}
