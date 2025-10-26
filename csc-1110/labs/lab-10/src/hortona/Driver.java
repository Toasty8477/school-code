/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 10 - StarBlind
 * Name: Alexander Horton
 * Created 11/08/2024
 */
package hortona;

import java.util.Scanner;

/**
 * driver for StarBlind that handles the intro and user input
 */
public class Driver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean playAgain = true;
        do {
            displayIntro();
            StarBlind game = new StarBlind();
            do {
                Stars guess = getGuess(in);
                System.out.print(guess + " ");
                game.guess(guess);
            } while (!game.hasWon());
            System.out.println("Congratulations. You took "+game.getNumberOfGuesses()+" guesses.");
            System.out.println(game);
            System.out.println();
            System.out.println("Enter Y or y to play again.");
            if (!in.next().equalsIgnoreCase("y")) {
                playAgain = false;
            }
        } while (playAgain);
    }

    private static void displayIntro() {
        System.out.println("Welcome to StarBlind where you guess the colors of the stars.");
        System.out.println("After each guess you will receive " +
                "a hint about how close your guess was.");
        System.out.println("You will see one green bar ("+Color.GREEN + "|" + Color.RESET +
                ") for each star that matches in color and location");
        System.out.println("and a yellow bar (" + Color.YELLOW + "|" + Color.RESET +
                ") for each star that matches in color but not location.");
        System.out.print("The following colors are available: ");
        System.out.print(Color.RED + "R" + Color.RESET + ", ");
        System.out.print(Color.GREEN + "G" + Color.RESET + ", ");
        System.out.print(Color.BLUE + "B" + Color.RESET + ", ");
        System.out.print(Color.YELLOW + "Y" + Color.RESET + ", ");
        System.out.print(Color.MAGENTA + "M" + Color.RESET + ", ");
        System.out.print(Color.CYAN + "C" + Color.RESET + "\n");
    }

    private static Stars getGuess(Scanner in) {
        String guess;
        boolean validGuess;
        do {
            System.out.println("Please enter your guess in the form: RGGB");
            guess = in.next().toLowerCase();
            validGuess = isValidEntry(guess);
        } while (!validGuess);

        return new Stars(guess);
    }

    private static boolean isValidEntry(String entry) {
        int correctChars = 0;
        for (int i = 0; i < entry.length(); i++) {
            if (entry.charAt(i) == 'r' || entry.charAt(i) == 'g' || entry.charAt(i) == 'b' ||
                    entry.charAt(i) == 'm' || entry.charAt(i) == 'y' || entry.charAt(i) == 'c') {
                correctChars++;
            }
        }
        return correctChars == 4 && entry.length() == 4;
    }
}
