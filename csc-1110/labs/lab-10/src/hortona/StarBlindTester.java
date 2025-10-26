/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 10 - StarBlind
 * Name: Alexander Horton
 * Created 11/08/2024
 */

package hortona;


import java.util.ArrayList;
import java.util.Arrays;

public class StarBlindTester {
    public static void main(String[] args) {
        Stars ans =
                new Stars(Color.GREEN, Color.GREEN, Color.RED, Color.BLUE);
        StarBlind game = new StarBlind(ans);
        //StarBlind game = new StarBlind();
        ArrayList<String> testInputs =
                new ArrayList<>(Arrays.asList("mmgc", "mggc", "mgcc", "bccb",
                        "rrbb", "bbrr", "brgg", "brgg", "ggrb"));

        for(int i = 0; i < testInputs.size(); i++){
            Stars guess = new Stars(testInputs.get(i));
            System.out.printf("%-15s", "Actual answer:");
            System.out.println(ans);
            System.out.printf("%-15s", "Test input "+i);
            System.out.println(guess);
            System.out.print("Similarity: ");
            game.guess(guess);
            System.out.println("Has won: "+game.hasWon());
            System.out.println();
        }
        System.out.println(game);
    }
}
