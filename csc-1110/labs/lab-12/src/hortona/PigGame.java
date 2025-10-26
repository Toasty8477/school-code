/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 12 - The Game of Pig
 * Name: Alexander Horton
 * Created: 11/22/2024
 */
package hortona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The game of Pig
 */
public class PigGame {
    private final Die die;
    private final List<Player> players;

    /**
     * Creates a new game
     */
    public PigGame() {
        die = new Die();
        players = new ArrayList<>();
    }

    /**
     * adds a player to the game
     * @param player any type of player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * handles the logic for turn taking and scoring
     * @return the winner of the game
     */
    public Player playGame() {
        final int winThreshold = 100;
        ArrayList<Player> scoreOrder = new ArrayList<>();
        for(Player p: players) {
            scoreOrder.add(p);
        }
        Die startingPlayer = new Die(players.size());
        startingPlayer.roll();
        Collections.rotate(players, startingPlayer.getSideUp());
        Player winner = null;
        do {
            for(int i = 0; i < players.size() && winner == null; i++) {
                for(Player p: scoreOrder) {
                    System.out.printf("%10s : %3d\n", p.getName(), p.getScore());
                }
                takeTurn(players.get(i));
                for(Player q: players) {
                    if (q.getScore() >= winThreshold) {
                        winner = q;
                    }
                }
            }
        } while (winner == null);
        for(Player q: scoreOrder) {
            System.out.printf("%10s : %3d\n", q.getName(), q.getScore());
        }
        return winner;
    }
    @Override
    public String toString() {
        return "game";
    }

    private void takeTurn(Player player) {
        int turnScore = 0;
        boolean busted = false;
        do {
            die.roll();
            System.out.println(player.getName() + " rolled a " + die.getSideUp());
            if (die.getSideUp() == 1) {
                busted = true;
            } else {
                turnScore += die.getSideUp();
            }
        } while (!busted && !player.chooseToHold(turnScore));
        if (!busted) {
            player.addToScore(turnScore);
            System.out.println(player.getName() + " holds.");
        } else {
            System.out.println(player.getName() + " busts.");
        }
    }
}
