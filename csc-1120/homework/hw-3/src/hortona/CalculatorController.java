/*
 * Course: CSC-1120 - 131
 * Homework 3 - Calculator
 * Name: Alexander Horton
 * Last Updated: 02/07/2025
 */

package hortona;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for the Calculator class
 */
public class CalculatorController {
    @FXML
    private TextField operandA;
    @FXML
    private TextField operandB;
    @FXML
    private Button operator;
    @FXML
    private TextField result;

    @FXML
    private void calculate() {
        try {
            if (!operandA.getText().isEmpty() && !operandB.getText().isEmpty()) {
                int a = Integer.parseInt(operandA.getText());
                int b = Integer.parseInt(operandB.getText());
                String mathResult = Calculator.calculate(a, b, operator.getText());
                result.setText(mathResult);
                if (mathResult.equals("ERROR!!!")) {
                    result.setStyle("-fx-background-color: red; -fx-text-fill: white");
                } else if (Integer.parseInt(mathResult) < 0) {
                    result.setStyle("-fx-background-color: red; -fx-text-fill: white");
                } else {
                    result.setStyle("-fx-background-color: lightGray; -fx-text-fill: black");
                }
            }
        } catch(NumberFormatException e) {
            result.setText("ERROR!!!");
            result.setStyle("-fx-background-color: red; -fx-text-fill: white");
        }
    }
    @FXML
    private void changeOperation() {
        String currentOperator = operator.getText();
        switch(currentOperator) {
            case "+" -> operator.setText("-");
            case "-" -> operator.setText("*");
            case "*" -> operator.setText("/");
            case "/" -> operator.setText("%");
            case "%" -> operator.setText("+");
        }
    }
}
