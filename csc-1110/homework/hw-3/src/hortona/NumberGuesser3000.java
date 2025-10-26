/***************************
 * Course: CSC1110 - 110
 * Fall 2024
 * Homework 3 - Word Guesser
 * Name: Alexander Horton
 * Created 09/18/2024
 **************************/

package hortona;

import java.util.Scanner;

public class NumberGuesser3000 {
    public static void main(String[] args) {
        // Generate a random number
        int randomNumber = (int)(Math.random()*100)+1;
        // All the variables
        int guess;
        int tries = 1;
        boolean correct = false;
        // Make Scanner
        Scanner in = new Scanner(System.in);
        // Explanation
        System.out.println("A random number has been generated from 1 to 100.");
        System.out.println("You have 10 tries to guess the number.\n");

        // Actual guessing part. IntelliJ complains but it works fine.
        do {
            System.out.println("Guess Number: " + tries);
            System.out.print("Enter a guess: ");
            guess = in.nextInt();
            if (guess < 1 || guess > 100) {
                System.out.println("Please input a number between 1 and 100.");
                System.out.println();
            } else if (guess == randomNumber) {
                System.out.println("Congratulations! You guessed the number in " + tries + " tries.");
                tries = 11;
                correct = true;
            } else if (guess > randomNumber) {
                System.out.println("Guess is too high.");
                tries++;
                System.out.println();
            } else if (guess < randomNumber) {
                System.out.println("Guess is too low.");
                tries++;
                System.out.println();
            }
        } while (tries != 11); // Keeps going until you hit 11 tries because I indexed from 1 not 0.

        if (tries == 11 && !correct) { // Program no longer calls you a failure if you really aren't.
            System.out.println("Sorry, you ran out of tries. The secret number was " + randomNumber + ".");
        }
    }
}
