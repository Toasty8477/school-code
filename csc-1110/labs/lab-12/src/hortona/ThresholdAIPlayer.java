/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 12 - The Game of Pig
 * Name: Alexander Horton
 * Created: 11/22/2024
 */

package hortona;

/**
 * An AI Player who holds based on whether a certain turn score has been reached
 */
public class ThresholdAIPlayer extends AIPlayer {
    private final int threshold;

    /**
     * creates the player
     * @param threshold what score the player should choose to hold at
     */
    public ThresholdAIPlayer(int threshold) {
        this.threshold = threshold;
    }
    @Override
    public boolean chooseToHold(int turnScore) {
        return turnScore >= threshold;
    }
}
