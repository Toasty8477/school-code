/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Exercise 5 - Die
 * Name: Alexander Horton
 * Last Updated: 10/10/2024
 */

package hortona;

/**
 * This class creates a die with a specified or default number of sides that can be rolled to get a
 * value between 1 and the number of sides on the die. This number is stored for future use.
 */
public class Die {
    /**
     * Set the default number of sides for a die
     */
    public static final int DEFAULT_NUM_SIDES = 6;
    private final int numSides;
    private int currentValue;

    /**
     * Creates a die with the default number of sides
     */
    public Die() {
        this(DEFAULT_NUM_SIDES);
    }

    /**
     * creates a die with the number of sides indicated by numSides and sets an initial value
     * @param numSides The number of sides the die should have
     */
    public Die(int numSides) {
        this.numSides = numSides;
        roll();
    }
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Rolls the die, sets the current value to whatever was rolled and returns that
     * @return returns a number between 1 and the number of sides on the die
     */
    public int roll() {
        currentValue = (int)(Math.random()*numSides)+1;
        return currentValue;
    }
}
