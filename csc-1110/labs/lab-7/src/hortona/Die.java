/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 7 - Battle Simulator 3000
 * Name: Alexander Horton
 * Last Updated: 10/18/2024
 */

package hortona;

import java.util.Random;
/**
 * This class creates a die with a specified or default number of sides that can be rolled to get a
 * value between 1 and the number of sides on the die. This number is stored for future use.
 */
public class Die {
    private final int numSides;
    private int currentValue;
    private Random generator;

    /**
     * Creates a die with the number of sides indicated by numSides and sets an initial value.
     * If the die has less than 2 or more than 100 sides
     * then the number of sides is set to the default.
     * @param numSides The number of sides the die should have
     */
    public Die(int numSides) {
        final int defaultSides = 6;
        final int minSides = 2;
        final int maxSides = 100;
        generator = new Random();
        if (numSides >= minSides && numSides <= maxSides) {
            this.numSides = numSides;
        } else {
            this.numSides = defaultSides;
        }
        roll();
    }
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Rolls the die, sets the current value to whatever was rolled and returns that
     */
    public void roll() {
        currentValue = generator.nextInt(1, numSides+1);
    }
}
