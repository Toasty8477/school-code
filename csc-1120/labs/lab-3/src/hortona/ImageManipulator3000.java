/*
 * Course: CSC1110 - 111
 * Spring 2025
 * Lab 3 - Image Manipulator 3000
 * Name: Alexander Horton
 * Modified 2/11/2025
 */

package hortona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Launch Image Manipulator 300
 */
public class ImageManipulator3000 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("layout.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Image Manipulator 3000");
        stage.show();

    }
}
