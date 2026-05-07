/*
 * Course: SWE2410 - 111, 121
 * Spring 2026
 * Lecture Code
 * Name: Adela Velez
 * Created: 3/3/2026
 */

package username;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Launches the game.
 */
public class CoconutsLab extends Application {
    private GameController controller;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("coconuts.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        primaryStage.setTitle("A Lonely Beach");
        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> controller.onKeyPressed(e));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
