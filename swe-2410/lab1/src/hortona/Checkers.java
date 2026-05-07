/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 1 - Checkers Lab No Patterns
 * Name: Adela Velez
 * Created: 1/2/2025
 */

package hortona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Standard template to launch the app.
 */
public class Checkers extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root =
                FXMLLoader.load(Objects.requireNonNull(getClass().getResource("checkers.fxml")));
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
