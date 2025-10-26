/*
 * Course: CSC-1120 - 131
 * Homework 3 - Calculator
 * Name: Alexander Horton
 * Last Updated: 02/07/2025
 */

package hortona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Simple calculator for performing operations on two numbers
 */
public class Calculator extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Calculator.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Welcome to Calculator 3000!");
        stage.show();
    }

    /**
     * Calculate two numbers with an operator
     * @param a number one
     * @param b number two
     * @param operator operator
     * @return the result of the operation
     */
    public static String calculate(int a, int b, String operator) {
        String result;
        if (operator.equals("+")) {
            result = Integer.toString(a + b);
        } else if (operator.equals("-")) {
            result = Integer.toString(a - b);
        } else if (operator.equals("*")) {
            result = Integer.toString(a*b);
        } else if (operator.equals("/") && b != 0) {
            result = Integer.toString(a/b);
        } else if (operator.equals("%") && b!= 0) {
            result = Integer.toString(a%b);
        } else {
            result = "ERROR!!!";
        }
        return result;
    }
}
