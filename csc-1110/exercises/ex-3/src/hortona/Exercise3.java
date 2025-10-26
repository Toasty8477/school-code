/********************************
 * Course: CSC1110 - 110
 * Fall 2024
 * Exercise 3 - Loops and Methods
 * Name: Alexander Horton
 * Created 09/19/2024
 *******************************/

package hortona;

import java.util.Scanner;

/**
 * Starter code for Exercise3. Fill in the stub out methods
 * based on the provided javadoc for each.
 */
public class Exercise3 {

    /**
     * Asks the user for a guess and compares it to the password.
     * Print an appropriate response based on whether the guess
     * matches the password or not.
     * @param scan Scanner object to get user input
     */
    public static void passwordVerify1(Scanner scan){
        String guess;
        String password = "1234";
        System.out.println("\nPassword Verify 1");

        System.out.print("Enter a guess for the password: ");
        guess = scan.next();
        if (guess.equals(password)) {
            System.out.println("Access Granted.");
        } else {
            System.out.println("Access denied. This incident will be reported.");
        }
        //TODO

    }

    /**
     * Asks the user for a guess and compares it to the password.
     * Print an appropriate response based on whether the guess
     * matches the password or not. Keeps asking the user for
     * the password until they get it correct.
     * @param scan Scanner object to get user input
     */
    public static void passwordVerify2(Scanner scan){
        String guess;
        String password = "1234";
        boolean correct = false;
        System.out.println("\nPassword Verify 2");

        do {
            System.out.print("Enter a guess for the password: ");
            guess = scan.next();
            if (guess.equals(password)) {
                System.out.println("Access Granted.");
                correct = true;
            } else {
                System.out.println("Access denied. This incident will be reported.");
            }
        } while (!correct);
        //TODO
    }

    /**
     * Asks the user for a word and then prints that word backwards.
     * @param scan Scanner object to get user input
     */
    public static void printBackwards(Scanner scan){
        String word;
        System.out.println("\nPrint Backwards");

        System.out.print("Enter a word: ");
        word = scan.next();

        System.out.print(word + " backwards is ");
        for (int i = word.length()-1; i>=0; i--) {
            System.out.print(word.charAt(i));
        }

        //TODO
    }

    /**
     * Asks the user for a word and then a letter. Prints
     * the number of occurrences of that letter in the word.
     * @param scan Scanner object to get user input
     */
    public static void countLetter(Scanner scan){
        String word;
        String letter;
        char checkLetter;
        int count = 0;
        System.out.println("\nCount Letter");

        System.out.print("Enter a word: ");
        word = scan.next();
        System.out.print("Enter a letter: ");
        letter = scan.next();
        checkLetter = letter.charAt(0);

        for(int i = 0; i < word.length(); i++){

           if (checkLetter == word.charAt(i)){
               count++;
            }
        }
        System.out.println("There are "+count+" "+letter+" in "+word);
        //TODO
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        passwordVerify1(scan);
        printBackwards(scan);
        countLetter(scan);
        passwordVerify2(scan);
    }
}