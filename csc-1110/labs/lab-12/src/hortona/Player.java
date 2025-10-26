/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 12 - The Game of Pig
 * Name: Alexander Horton
 * Created: 11/22/2024
 */

package hortona;

/**
 * A player for the game
 */
public abstract class Player {
    private final String name;
    private int score;

    /**
     * creates a player with a given name
     * @param name name for the player
     */
    public Player(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public int getScore() {
        return this.score;
    }

    /**
     * adds to the players score
     * @param turnScore the players score for their turn
     */
    public void addToScore(int turnScore) {
        this.score += turnScore;
    }

    /**
     * whether the player holds
     * @param turnScore the players score for their turn
     * @return if the player chooses to hold
     */
    public abstract boolean chooseToHold(int turnScore);

    @Override
    public String toString() {
        return name;
    }
}
