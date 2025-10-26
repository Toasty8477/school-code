/*
 * Course: CSC1110 - 111
 * Spring 2025
 * Lab 4 - Image Manipulator 3001
 * Name: Alexander Horton
 * Modified 2/18/2025
 */

package hortona;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Image Manipulator 3001
 */
public class Lab4 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Stage filter = new Stage();
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("Lab4Controller.fxml"));
        FXMLLoader filterLoader = new FXMLLoader();
        filterLoader.setLocation(getClass().getResource("KernelController.fxml"));
        Parent mainRoot = mainLoader.load();
        Parent filterRoot = filterLoader.load();
        stage.setScene(new Scene(mainRoot));
        stage.setTitle("Image Manipulator 3001");
        filter.setScene(new Scene(filterRoot));
        filter.setTitle("Filter Kernel");

        Lab4Controller mainController = mainLoader.getController();
        KernelController kernelController = filterLoader.getController();
        mainController.setMain(stage);
        mainController.setFilter(filter);
        kernelController.setMain(mainController);
        stage.show();
    }
}
