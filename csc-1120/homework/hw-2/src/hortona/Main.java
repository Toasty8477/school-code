/*
 * Course: CSC-1120
 * Homework 2 - JavaFX
 * Name: Alexander Horton
 */

package hortona;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A simple adding machine
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        final int spacing = 10;
        final int sidePadding = 50;
        HBox hBox = new HBox();
        hBox.setSpacing(spacing);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.setSpacing(spacing);
        vBox.setPadding(new Insets(spacing, sidePadding, spacing, sidePadding));

        TextField firstOperand = new TextField();
        TextField secondOperand = new TextField();
        TextField result = new TextField();
        result.setEditable(false);
        Button operator = new Button("+");
        Button equals = new Button("=");
        equals.setOnAction(_ -> calculate(firstOperand, secondOperand, result));
        firstOperand.setOnAction(_ -> calculate(firstOperand, secondOperand, result));
        secondOperand.setOnAction(_ -> calculate(firstOperand, secondOperand, result));
        result.setStyle("-fx-background-color: lightGrey");
        hBox.getChildren().addAll(firstOperand, operator, secondOperand, equals);

        vBox.getChildren().addAll(hBox, result);
        stage.setScene(new Scene(vBox));
        stage.setTitle("Welcome to Calculator 3000!");
        stage.show();
    }

    private void calculate(TextField firstOperand, TextField secondOperand, TextField result)
        throws NumberFormatException {
        try {
            int a = Integer.parseInt(firstOperand.getText());
            int b = Integer.parseInt(secondOperand.getText());
            result.setText(String.valueOf(a + b));
            if (a+b < 0) {
                result.setStyle("-fx-background-color: red; -fx-text-fill: white");
            } else {
                result.setStyle("-fx-background-color: lightGrey; -fx-text-fill: black");
            }
        } catch (NumberFormatException e) {
            System.out.println("One of the entered values was not a number");
        }
    }
}