/*
 * Course: CSC1120
 * Spring 2025
 * Lab 5: CSC1110 Lab Upgrade
 * Name: Alexander Horton
 * Updated 02/25/2025
 */

package hortona;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.text.DecimalFormat;

/**
 * Controller for Lab5
 */
public class Lab5Controller {

    private DecimalFormat rateFormatter = new DecimalFormat("##.#%");
    private DecimalFormat taxFormatter = new DecimalFormat("$###,###");

    @FXML
    private TextField income;
    @FXML
    private TextField deduction;
    @FXML
    private ToggleGroup filingType;
    @FXML
    private RadioButton single;
    @FXML
    private RadioButton joint;
    @FXML
    private TextField taxPaid;
    @FXML
    private TextField taxReturn;
    @FXML
    private TextField taxRate;
    @FXML
    private TextField totalDeductions;

    @FXML
    private void calculate() {
        final int paychecksPerYear = 26;
        try {
            long tax = 0;
            if (filingType.getSelectedToggle().equals(single)) {
                tax = taxes(Double.parseDouble(income.getText()), "s");
            } else if (filingType.getSelectedToggle().equals(joint)) {
                tax = taxes(Double.parseDouble(income.getText()), "j");
            }
            double deductions = Double.parseDouble(deduction.getText())*paychecksPerYear;
            taxPaid.setText(taxFormatter.format(tax));
            taxReturn.setText(taxFormatter.format(deductions - tax));
            totalDeductions.setText(taxFormatter.format(deductions));
            taxRate.setText(rateFormatter.format(tax/Double.parseDouble(income.getText())));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter A value in all fields");
            alert.show();
        }
    }

    private long taxes(double income, String maritalStatus) {

        final int singleFirstBracket = 11_925;
        final int singleSecondBracket = 48_474;
        final int singleThirdBracket = 103_350;
        final int singleFourthBracket = 197_300;
        final int singleFifthBracket = 250_525;
        final int singleSixthBracket = 626_350;

        final double singleFirstBracketAmount = 1192.5;
        final double singleSecondBracketAmount = 5578.38;
        final double singleThirdBracketAmount = 17_651.1;
        final double singleFourthBracketAmount = 40_199.1;
        final double singleFifthBracketAmount = 57_231.1;
        final double singleSixthBracketAmount = 188_769.85;

        final int jointFirstBracket = 23_850;
        final int jointSecondBracket = 96_950;
        final int jointThirdBracket = 206_700;
        final int jointFourthBracket = 394_600;
        final int jointFifthBracket = 501_050;
        final int jointSixthBracket = 751_600;

        final double jointFirstBracketAmount = 2385;
        final double jointSecondBracketAmount = 11_157;
        final double jointThirdBracketAmount = 35_302;
        final double jointFourthBracketAmount = 80_398;
        final double jointFifthBracketAmount = 114_462;
        final double jointSixthBracketAmount = 202_154.5;

        final double firstMultiplier = 0.1;
        final double secondMultiplier = 0.12;
        final double thirdMultiplier = 0.22;
        final double fourthMultiplier = 0.24;
        final double fifthMultiplier = 0.32;
        final double sixthMultiplier = 0.35;
        final double seventhMultiplier = 0.37;

        double tax = -1;
        // Single filing math. Magic numbers are for total taxes before that bracket.
        if (maritalStatus.equalsIgnoreCase("s")) {
            if (income <= singleFirstBracket) {
                tax = income * firstMultiplier;
            } else if (income > singleFirstBracket && income <= singleSecondBracket) {
                tax = (income - singleFirstBracket) * secondMultiplier + singleFirstBracketAmount;
            } else if (income > singleSecondBracket && income <= singleThirdBracket) {
                tax = (income - singleSecondBracket) * thirdMultiplier + singleSecondBracketAmount;
            } else if (income > singleThirdBracket && income <= singleFourthBracket) {
                tax = (income - singleThirdBracket) * fourthMultiplier + singleThirdBracketAmount;
            } else if (income > singleFourthBracket && income <= singleFifthBracket) {
                tax = (income - singleFifthBracket) * fifthMultiplier + singleFourthBracketAmount;
            } else if (income > singleFifthBracket && income <= singleSixthBracket) {
                tax = (income - singleFifthBracket) * sixthMultiplier + singleFifthBracketAmount;
            } else if (income > singleSixthBracket) {
                tax = (income - singleSixthBracket) * seventhMultiplier + singleSixthBracketAmount;
            }
            // Joint filing math. Same as above but with new numbers!
        } else if (maritalStatus.equalsIgnoreCase("j")) {
            if (income <= jointFirstBracket) {
                tax = income * firstMultiplier;
            } else if (income > jointFirstBracket && income <= jointSecondBracket) {
                tax = (income - jointFirstBracket) * secondMultiplier + jointFirstBracketAmount;
            } else if (income > jointSecondBracket && income <= jointThirdBracket) {
                tax = (income - jointSecondBracket) * thirdMultiplier + jointSecondBracketAmount;
            } else if (income > jointThirdBracket && income <= jointFourthBracket) {
                tax = (income - jointThirdBracket) * fourthMultiplier + jointThirdBracketAmount;
            } else if (income > jointFourthBracket && income <= jointFifthBracket) {
                tax = (income - jointFourthBracket) * fifthMultiplier + jointFourthBracketAmount;
            } else if (income > jointFifthBracket && income <= jointSixthBracket) {
                tax = (income - jointFifthBracket) * sixthMultiplier + jointFifthBracketAmount;
            } else if (income > jointSixthBracket) {
                tax = (income - jointSixthBracket) * seventhMultiplier + jointSixthBracketAmount;
            }
        }
        return Math.round(tax);
    }
}
