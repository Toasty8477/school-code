/*
 * Course: CSC1110 - FIXME
 * Fall 2024
 * Lab 12 - The Game of Pig
 * Name: FIXME
 * Created: FIXME
 */
package hortona;

import java.util.Scanner;

/**
 * Driver for playing a game of Pig, with a mix of human
 * players and bots.
 */
public class Pig {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        PigGame theGame = new PigGame();
        addPlayers(theGame, in);
        Player winner = theGame.playGame();
        System.out.println(winner.getName() + " is the winner!");
    }

    /**
     * Add all the players for the game.
     * @param theGame The game of Pig!
     * @param in Input source for player info.
     */
    private static void addPlayers(PigGame theGame, Scanner in){
        System.out.print("How many players? ");
        int numberOfPlayers = Integer.parseInt(in.nextLine());
        for(int i = 0; i < numberOfPlayers; ++i){
            addPlayer(theGame, in);
        }
    }

    /**
     * Add one player to the game
     *
     * Prompts for info depending on the player type.
     *
     * @param theGame The game of Pig!
     * @param in Input source for player info.
     */
    private static void addPlayer(PigGame theGame, Scanner in) {
        final String human = "1";
        final String dumb = "2";
        final String smart = "3";
        String input;

        do {
            System.out.print("1) Human\n2) Dumb AI\n3) Threshold AI\n-> ");
            input = in.nextLine();
        } while(!(input.equals(human) || input.equals(dumb) || input.equals(smart)));

        Player player;
        if(input.equals(human)){
            System.out.print("Name? ");
            String name = in.nextLine();
            player = new HumanPlayer(name, in);
        } else if(input.equals(dumb)){
            player = new DumbAIPlayer();
        } else {
            System.out.print("What threshold? ");
            int threshold = Integer.parseInt(in.nextLine());
            player = new ThresholdAIPlayer(threshold);
        }
        theGame.addPlayer(player);
    }
}
