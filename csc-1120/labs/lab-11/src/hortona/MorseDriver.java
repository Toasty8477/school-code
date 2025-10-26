/*
 * Course: CSC-1120A
 * Lab 11: Morse Encoder
 * Name: Alexander Horton
 * Last Updated: 04/15/2025
 */

package hortona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Driver for Morse Encoder
 */
public class MorseDriver extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("morseEncoder.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Morse Encoder 3000");
        stage.show();
    }
}
