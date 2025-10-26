/**************************
 * Course: CSC1110 - 111
 * Fall 2024
 * ASSIGNMENT # - REPLACE ME
 * Name: Alexander Horton
 * Created 11/22/2024
 **************************/

package hortona;

import java.util.Random;

public class Die {
    private static final int MIN_SIDES = 2;
    private static final int MAX_SIDES = 100;
    private static final int DEFAULT_SIDES = 6;
    private static final Random generator = new Random();
    private final int numSides;
    private int currentValue;

    public Die() {
        this(DEFAULT_SIDES);
    }
    public Die(int numSides) {
        if (numSides <= MAX_SIDES && numSides >= MIN_SIDES) {
            this.numSides = numSides;
        } else {
            this.numSides = DEFAULT_SIDES;
        }
    }
    public int getSideUp() {
        return currentValue;
    }
    public void roll(){
        currentValue = generator.nextInt(1, numSides+1);
    }
}
