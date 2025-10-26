/*
 * Course: CSC1120 - 131
 * Exercise 2 - JavaFX Events
 * Name: Alexander Horton
 */
package hortona;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * JavaFX Event Exercise
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Set default values
        final int width = 200;
        final int height = 150;
        final int spacing = 10;
        // Make parent container
        VBox vBox = new VBox(spacing);
        vBox.setAlignment(Pos.CENTER);
        // Make controls
        Label label = new Label("Watch me change!");
        TextField textField = new TextField("Enter some text here");
        Button button = new Button("Click Me!");
        Label coordinates = new Label();
        // Add event handlers
        label.setOnMouseEntered(_ -> label.setTextFill(Paint.valueOf("purple")));
        label.setOnMouseExited(_ -> label.setTextFill(Paint.valueOf("black")));
        // Add a listener to the TextField that will update the contents of label to whatever is in
        // the TextField when the user presses Enter/Return
        textField.setOnAction(_ -> label.setText(textField.getText()));
        button.setOnAction(_ -> label.setText("Watch me change!"));
        // Add a MouseEvent listener to the parent container that will display the current
        // coordinates of the mouse pointer in the coordinates label whenever the pointer
        // is moved inside the GUI window. You do not need to worry about what happens when
        // the mouse pointer leaves the window.
        vBox.setOnMouseMoved(e -> coordinates.setText(
                "X: " + Math.round(e.getX()) + " Y: " + Math.round(e.getY())));
        // Add controls to the parent
        vBox.getChildren().addAll(label, textField, button, coordinates);
        // Add parent container to the Scene and set the dimensions
        Scene scene = new Scene(vBox, width, height);
        // Add the scene to the stage and give it a title
        stage.setScene(scene);
        stage.setTitle("Events!");
        // Show the stage
        stage.show();
    }
}
