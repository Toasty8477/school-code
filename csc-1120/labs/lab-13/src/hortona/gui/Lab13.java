package hortona.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Lab13 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("autocompleter.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("More Autocompleter :(");
        stage.show();
    }
}
