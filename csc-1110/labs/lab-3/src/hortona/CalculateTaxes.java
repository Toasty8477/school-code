/************************
 * Course: CSC1110 - 110
 * Fall 2024
 * Lab 3 - Tax Filing
 * Name: Alexander Horton
 * Created 09/20/2024
 ************************/

package hortona;

import java.text.DecimalFormat;
import java.util.Scanner;

public class CalculateTaxes {
    public static void main(String[] args) {

        // Make new scanner
        Scanner in = new Scanner(System.in);
        // Makes decimal formatters for effective rate and dollar amount respectively.
        DecimalFormat rateFormatter = new DecimalFormat("##.#%");
        DecimalFormat taxFormatter = new DecimalFormat("$###,###");

        // initialize tax value
        double tax = 0;

        // Ask if single of joint filing then for income
        System.out.print("Are you a single filer or a married joint filer ('s' or 'j'): ");
        String maritalStatus = in.next();
        System.out.print("Enter an estimate of your earned income for 2024 (xxxx.xx): ");
        double income = in.nextDouble();

        // Single filing math. Magic numbers are for total taxes before that bracket.
        if (maritalStatus.equalsIgnoreCase("s")) {
            if (income <= 11600) {
                tax = (income * 0.1);
            } else if (income > 11600 && income <= 47150) {
                tax = (income - 11600) * 0.12 + 1160;
            } else if (income > 47150 && income <= 100_525) {
                tax = (income - 47150) * 0.22 + 5426;
            } else if (income > 100_525 && income <= 191_950) {
                tax = (income - 100_525) * 0.24 + 17_168.5;
            } else if (income > 191_950 && income <= 243_725) {
                tax = (income - 191_950) * 0.32 + 39_110.5;
            } else if (income > 243_725 && income <= 609_350) {
                tax = (income - 243_725) * 0.35 + 55_678.5;
            } else if (income > 609_350) {
                tax = (income - 609350) * 0.37 + 183_647.25;
            }
        // Joint filing math. Same as above but with new numbers!
        } else if (maritalStatus.equalsIgnoreCase("j")) {
            if (income <= 23200) {
                tax = (income * 0.1);
            } else if (income > 23_200 && income <= 94_300) {
                tax = (income - 23_200) * 0.12 + 2320;
            } else if (income > 94_300 && income <= 201_050) {
                tax = (income - 94_300) * 0.22 + 10_852;
            } else if (income > 201_050 && income <= 383_900) {
                tax = (income - 201_050) * 0.24 + 34_337;
            } else if (income > 383_900 && income <= 487_450) {
                tax = (income - 383_900) * 0.32 + 78_221;
            } else if (income > 487_450 && income <= 731_200) {
                tax = (income - 487_450) * 0.35 + 111_357;
            } else if (income > 731_200) {
                tax = (income - 731_200) * 0.37 + 196_669.5;
            }
        }
        // Round to nearest whole number
        tax = Math.round(tax);
        // Calculate effective tax rate and format to a percentage
        String effectiveTaxRate = rateFormatter.format(tax / income);
        // Output total taxes in dollar amount and effective tax percentage.
        System.out.println("Your estimated taxes for 2024 are: " + taxFormatter.format(tax));
        System.out.println("This results in a " + effectiveTaxRate + " effective tax rate.");
    }
}