/*************************************
* Course: CSC1110 - 111
* Fall 2024
* Homework 1 - Variable Initialization
* Name: Alexander Horton
* Created 09/05/2024
*************************************/

public class VariableInitialization {
	public static void main(String[] args){

		// Set up variables
		String firstName = "Alex";
		String lastName = "Horton";
		String familyMemberName = "Nicholas";
		int currentYear = 2024;
		int birthYear = 2005;
		int familyMemberBirthYear = 2009;

		final double meterToInchConversion = 39.37008;
		double height = 1.8288; // In meters
		double familyMemberHeight = 1.7526; // In meters

		// Math to find ages
		int aproximateAge = currentYear - birthYear;
		int familyMemberAproximateAge = currentYear - familyMemberBirthYear;

		// Math to find heights
		double heightInches = height * meterToInchConversion;
		double familyMemberHeightInches = familyMemberHeight * meterToInchConversion;

		// Bunch of print statements
		System.out.println("My last name is " + lastName);
		System.out.println("My First Name Is " + firstName);
		System.out.println("The first name of someone else in my family is " + familyMemberName);
		System.out.println("My full name is " + firstName + " " + lastName);
		System.out.println("Their full name is " + familyMemberName + " " + lastName);
		System.out.println(" "); // Just prints a space to show a blank line
		System.out.println("The current year is " + currentYear);
		System.out.println("I was born in " + birthYear);
		System.out.println("Nicholas was born in " + familyMemberBirthYear);
		System.out.println(aproximateAge + " is " + firstName +"'s aproximate age");
		System.out.println(familyMemberAproximateAge + " is " + familyMemberName +"'s aproximate age");
		System.out.println(" "); // Another blank line
		System.out.println("The conversion from meters to inches is " + meterToInchConversion);
		System.out.println(firstName + " is " + height + " meters tall");
		System.out.println(familyMemberName + " is " + familyMemberHeight + " meters tall");
		System.out.println(firstName + " is " + heightInches + " inches tall");
		System.out.println(familyMemberName + " is " + familyMemberHeightInches + " inches tall");
	}
}
