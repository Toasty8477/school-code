/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 10 - StarBlind
 * Name: Alexander Horton
 * Created 11/08/2024
 */
package hortona;

import java.util.ArrayList;

/**
 * handles guessing and winning the game
 */
public class StarBlind {

    private ArrayList<String> guesses;
    private Stars answer;

    /**
     * creates a new game with the specified answer
     * @param ans some pre-made stars object
     */
    public StarBlind(Stars ans) {
        answer = ans;
        guesses = new ArrayList<>();
    }

    /**
     * creates a new game with a random answer
     */
    public StarBlind() {
        this(new Stars());
    }

    /**
     * Adds a guess and its similarity to the guess list, then prints it
     * @param guess user inputted guess
     */
    public void guess(Stars guess) {
        guesses.add(guess + " " + answer.getSimilarity(guess));
        System.out.println(answer.getSimilarity(guess));
    }

    public int getNumberOfGuesses() {
        return guesses.size();
    }

    /**
     * checks through the list of guesses to see if the user has guessed correctly
     * @return whether the user has won or not.
     */
    public boolean hasWon() {
        boolean won = false;
        for (String guess : guesses) {
            if (guess.equals(answer + " " + answer.getSimilarity(answer))) {
                won = true;
            }
        }
        return won;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String guess : guesses) {
            sb.append(guess);
            sb.append("\n");
        }
        return sb.toString();
    }
}
