/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 6 - Bank Account
 * Name: Alexander Horton
 * Last Updated: 10/11/2024
 */

package hortona;

import java.util.Scanner;

/**
 * Driver class for BankAccount
 */
public class ATM {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int option;
        String name;
        int age;
        String pin;
        double depositAmount;
        String enteredPin;
        double withdrawAmount;
        boolean running = true;

        System.out.println("Welcome to University Bank.");
        System.out.println("Follow the prompts below to set up your new account.\n");

        System.out.print("Enter your name: ");
        name = scan.next();
        System.out.print("Enter your age: ");
        age = scan.nextInt();
        System.out.print("Enter a pin (e.g., 1234): ");
        pin = scan.next();

        BankAccount account = new BankAccount(name, age, pin);

        do{
            System.out.println("\nAccount Details:");
            System.out.printf("\tName: %s\n\tAge: %d\n\tBalance: %.2f\n",
                    account.getName(), account.getAge(), account.getBalance());
            System.out.println("\nOptions:\n\t1. Deposit.\n\t2. Withdraw.\n\t3. Quit.");
            System.out.print("What would you like to do: ");
            option = scan.nextInt();
            if (option == 1){
                System.out.print("\nHow much would you like to deposit: ");
                depositAmount = scan.nextDouble();
                if (account.deposit(depositAmount) == -1){
                    System.out.println("Error depositing funds.");
                }

            } else if (option == 2) {
                System.out.print("\nHow much would you like to withdraw: ");
                withdrawAmount = scan.nextDouble();
                System.out.print("Enter your pin: ");
                enteredPin = scan.next();
                if (account.withdraw(enteredPin, withdrawAmount) == -1){
                    System.out.println("Error withdrawing funds.");
                }
            } else if (option == 3) {
                running = false;
            } else {
                System.out.println("\nInvalid Entry.");
            }

        } while (running);
        System.out.println("\nThank you for banking with University Bank.");
    }
}
