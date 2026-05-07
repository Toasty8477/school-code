/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Adela Velez
 * Created: 1/2/2025
 */

package garden;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Loads the Garden simulation program
 */
public class GardenDesign extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("bee_simulator.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Bee Runner 2049");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
