/********************************************************
* Course: CSC1110 - 111
* Fall 2024
* Excercise 1 - Variable declaration and basic operations
* Name: Alexander Horton
* Created 09/06/2024
********************************************************/

public class Excercise1 {

	public static void main(String[] args) {
		// All the variables
		int catsOwned = 1;
		int dogsOwned = 1;
		int petsOwned = (catsOwned + dogsOwned);

		String firstName = "Alexander";
		String lastName = "Horton";

		int friends = 3;
		int totalCandy = 17;
		int candyPerFriend = (totalCandy / friends);

		final double pi = 3.1415927;
		int circleCircumference = 15;
		// Circle math
		double circleDiameter = (circleCircumference / pi);
		double circleRadius = (circleDiameter / 2);
		double circleArea = pi * (circleRadius * circleRadius);

		// Print Statements

		System.out.println("I own " + catsOwned + " cat(s) and " + dogsOwned + " dog(s) for a total of " + petsOwned + " pets.");
		System.out.println(" "); // Blank Line
		System.out.println("Hello. My name is " + firstName + " " + lastName + ". You killed my father. Prepare to die.");
		System.out.println(" "); // Blank Line
		System.out.println("I have " + friends + " friends, and " + totalCandy + " pieces of candy .");
		System.out.println("Each of my friends can have " + candyPerFriend + " pieces of candy.");
		System.out.println(" "); // Blank Line
		System.out.println("A circle with a Circumference of " + circleCircumference + " will have a:");
		System.out.println("\tDiameter of " + circleDiameter);
		System.out.println("\tRadius of " + circleRadius);
		System.out.println("\tArea of " + circleArea);

	} // Close main method
} // Close Excercise1 class
