/*
 * Course: CSC1120
 * Spring 2025
 * Lab 5: CSC1110 Lab Upgrade
 * Name: Alexander Horton
 * Updated 02/25/2025
 */

package hortona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Program that calculates estimated taxes for 2025, effective tax rate, and estimated tax return
 */
public class Lab5 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Lab5Layout.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("file:src/hortona/icon.png"));
        stage.setTitle("Tax Calculator 3000");
        stage.show();
    }
}
