/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 12 - The Game of Pig
 * Name: Alexander Horton
 * Created: 11/22/2024
 */

package hortona;

/**
 * the basis of an AI Player
 */
public abstract class AIPlayer extends Player {
    private static int numberOfAIPlayers = 0;

    /**
     * Creates the basis for an AI Player
     */
    public AIPlayer() {
        super("Bot #"+numberOfAIPlayers);
        numberOfAIPlayers++;
    }
}
