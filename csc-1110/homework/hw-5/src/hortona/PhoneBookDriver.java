/************************
 * Course: CSC1110 - 110
 * Fall 2024
 * Homework 5 - PhoneBook
 * Name: Alexander Horton
 * Created 10/2/2024
 ***********************/

package hortona;

import java.util.Scanner;

public class PhoneBookDriver {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();
        Scanner scan = new Scanner(System.in);
        boolean running = true;

        do{
            System.out.println("Enter a phone number in the form cc-area-local,");
            System.out.println("where cc = country code digits, area = area code digits,");
            System.out.println("and local = local phone digits.");
            System.out.print("Or enter q to quit: ");
            String number = scan.next();
            if (number.equalsIgnoreCase("q")) {
                running = false;
            } else {
                pb.setPhoneNumber(number);
                System.out.println("country code = "+pb.getCountryCode());
                System.out.println("area code = "+pb.getAreaCode());
                System.out.println("local number = "+pb.getLocalNumber());
            }
        } while (running);
    }
}