/*****************************
* Course: CSC1110 - 111
* Fall 2024
* Lab 1 - Two truths and a lie
* Name: Alexander Horton
* Created 09/06/2024
*****************************/

import java.util.Scanner; // Import scanner

public class TruthsAndLie {
	public static void main(String[] args) {
		// Init Scanner as in
		Scanner in = new Scanner(System.in);
		// Truths
		String truth1 = "I am left handed.";
		String truth2 =	"I play an instrument.";
		// Lie
		String lie = "I have lived in Wisconsin my whole life.";
		// More variables
		int userChoice;

		// Print the opening and statements
		System.out.println("Two of the following statements is true and one is a lie. You must choose the lie.");
		System.out.println(" "); // Blank Line
		System.out.println("1. " + truth1);
		System.out.println("2. " + lie);
		System.out.println("3. " + truth2);
		System.out.println(" "); // Blank Line
		System.out.print("Which statement is the lie? (1, 2, 3): ");
		userChoice = in.nextInt();
		if (userChoice == 2) {
			System.out.println("Correct!");
		} else {
			System.out.println("Incorrect.");
		}
	}
}

// Partner was Jaffar
// Jaffar took 3 guesses
// Took me 1 guess
