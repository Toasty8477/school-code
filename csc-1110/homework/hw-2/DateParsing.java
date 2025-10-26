/**************************
* Course: CSC1110 - 110
* Fall 2024
* Homework 2 - Date Parsing
* Name: Alexander Horton
* Created 09/11/2024
**************************/

import java.util.Scanner;

public class DateParsing {
	public static void main(String[] args){

		Scanner in = new Scanner(System.in); // Input Scanner

		// Variables for later
		int currentMonth;
		int currentDay;
		int currentYear;

		int birthMonth;
		int birthDay;
		int birthYear;

		int monthsLeft;
		int yearsSinceBirth;

		String currentDate;
		String birthDate;

		// Get current date
		System.out.print("Input the current date(mm/dd/yyyy): ");
		currentDate = in.next();
		// Get user's birth date
		System.out.print("Input your birth date(mm/dd/yyyy): ");
		birthDate = in.next();

		// Parse recived dates into individual parts
		// Done by getting the respective part of the string then parsing to an int
		currentMonth = Integer.parseInt(currentDate.substring(0,2));
		currentDay = Integer.parseInt(currentDate.substring(3,5));
		currentYear = Integer.parseInt(currentDate.substring(6));
		birthMonth = Integer.parseInt(birthDate.substring(0,2));
                birthDay = Integer.parseInt(birthDate.substring(3,5));
                birthYear = Integer.parseInt(birthDate.substring(6));

		// Math time
		monthsLeft = (12 - currentMonth);
		yearsSinceBirth = (currentYear - birthYear);

		// Output
		System.out.println("Today is day " + currentDay + " of month " + currentMonth + " in the year " + currentYear);
		if (monthsLeft == 1) {
			System.out.println("There is " + monthsLeft + " month left in the year");
		} else {
			System.out.println("There are " + monthsLeft + " months left in the year");
		}
		System.out.println("You were born on day " + birthDay + " of month " + birthMonth + " in the year " + birthYear);
		System.out.println("It has been aproximately " + yearsSinceBirth + " years since you were born");
	}
}
