/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 6 - Vending Machine
 * Name: Alexander Horton
 * Last Updated: 10/8/2024
 */
package hortona;

/**
 * Simple vending machine that can take in money and sell items based on cost
 * as well as returning an amount of change.
 */
public class VendingMachine {

    private double totalMoney = 0;
    private double itemCost = 0;
    private boolean valid;

    /**
     * Allows insertion of money into the machine
     * @param in Passes in Scanner from main method
     */
    public void insertMoney(java.util.Scanner in) {
        valid = false;
        do {
            System.out.print("Amount of money inserted: ");
            double moneyInserted = in.nextDouble();
            if (moneyInserted > 0) {
                totalMoney = totalMoney + moneyInserted;
                valid = true;
            } else {
                System.out.println("Invalid payment. Must enter positive number.");
            }
        } while (!valid);
        in.nextLine(); // Clear Buffer
    }

    /**
     * Allows you to input a price for an item and prints how much change you would receive
     * @param in Passes in Scanner from main method
     */
    public void selectItem(java.util.Scanner in) {
        valid = false;
        do {
            System.out.print("Select item's price: ");
            double price = in.nextDouble();
            if (price > 0 && price <= totalMoney) {
                itemCost = price;
                valid = true;
            } else {
                System.out.println("Invalid payment. Must enter positive number.");
            }
        } while (!valid);
        makeChange();
        totalMoney = totalMoney - itemCost;
        in.nextLine(); // Clear Buffer

    }
    private void makeChange(){
        final int dollarsToCentsConversion = 100;
        final int quarterValue = 25;
        final int dimeValue = 10;
        final int nickelValue = 5;
        int remainingChange = (int)((totalMoney - itemCost) * dollarsToCentsConversion);
        int quarters;
        int dimes;
        int nickels;
        int pennies;

        quarters = remainingChange / quarterValue;
        remainingChange = remainingChange % quarterValue;
        dimes = remainingChange / dimeValue;
        remainingChange = remainingChange % dimeValue;
        nickels = remainingChange / nickelValue;
        remainingChange = remainingChange % nickelValue;
        pennies = remainingChange;

        System.out.println("Your Change\n===========");
        System.out.printf("%d quarter(s)\n%d dime(s)\n%d nickel(s)\n%d penny(ies)\n",
                quarters, dimes, nickels, pennies);

    }
}