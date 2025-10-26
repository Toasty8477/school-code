/********************************************
* Course: CSC1110 - 111
* Fall 2024
* Excercise 2 - Scanner and String operations
* Name: Alexander Horton
* Created 09/06/2024
********************************************/
// Import Scanner
import java.util.Scanner;

public class Exercise2 {
	public static void main(String[] args) {

		// Create new scanner variable
		Scanner in = new Scanner(System.in);

		// Prints name in format 'last, first'
		System.out.print("Enter your first name: ");
		String firstName = in.next();
		System.out.print("Enter your last name: ");
		String lastName = in.next();
		System.out.println(lastName + ", " + firstName);
		System.out.println(" "); // Blank Line

		// Calculates and prints the area of a circle
		System.out.print("Enter the value of pi: ");
		double pi = in.nextDouble();
		System.out.print("Enter the radius of a circle: ");
		double radius = in.nextDouble();
		double area = pi * (radius * radius);
		System.out.println("The area of a circle with radius " + radius + " is " + area);
                System.out.println(" "); // Blank Line

		// Clear the input buffer
		in.nextLine();

		// Asks user for phrase and a number. Prints the number of characters specified
		System.out.print("Enter a phrase with multiple words: ");
		String userPhrase = in.nextLine();
		int userPhraseLength = userPhrase.length();
		System.out.print("Enter a number less than " + userPhraseLength + ": ");
		int userPhraseIndex = in.nextInt();
		String userPhraseSubstring = userPhrase.substring(0, userPhraseIndex);
		System.out.println(userPhraseSubstring);
                System.out.println(" "); // Blank Line

		// Prints the first and last characters of a word
		System.out.print("Enter a word with no spaces: ");
		String theWord = in.next();
		System.out.println(theWord.charAt(0));
		System.out.println(theWord.charAt(theWord.length()-1));
                System.out.println(" "); // Blank Line

		// Asks for number of friends and ammount of candy.
		// Prints how many pieces each friend gets and how many remain.
		System.out.print("Enter how many friends you have: ");
		int friends = in.nextInt();
		System.out.print("Enter an ammount of candy: ");
		int candy = in.nextInt();
		int candyPer = candy / friends;
		int candyRemaining = candy % friends;
		System.out.println("Each friend gets " + candyPer + " pieces of candy.");
		System.out.println("There are " + candyRemaining + " pieces remaining.");

	}
}
