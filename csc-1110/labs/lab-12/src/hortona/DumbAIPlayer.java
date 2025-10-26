/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 12 - The Game of Pig
 * Name: Alexander Horton
 * Created: 11/22/2024
 */

package hortona;

/**
 * An AI player who holds based on a coin flip
 */
public class DumbAIPlayer extends AIPlayer {
    private final Die coin;

    /**
     * creates the player
     */
    public DumbAIPlayer() {
        coin = new Die(2);
    }
    @Override
    public boolean chooseToHold(int turnScore) {
        coin.roll();
        return coin.getSideUp() == 1;
    }
}
