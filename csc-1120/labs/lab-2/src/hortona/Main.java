/*
 * Course: CSC-1120 - 131
 * Lab 2 - Image Displayer 3001
 * Name: Alexander Horton
 * Last Updated: 02/04/2025
 */

package hortona;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Runs the Image Displayer 3001
 */
public class Main extends Application {

    private final double canvasWidth = 500;
    private final double canvasHeight = 200;

    private Image image;
    private final Canvas canvas = new Canvas(canvasWidth, canvasHeight);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(buildGUI());
        stage.setTitle("Image Displayer 3001");
        stage.show();
    }

    /**
     * Creation of components
     * @return Completed Scene
     */

    public Scene buildGUI() {

        final int spacing = 10;

        gc.setFill(Color.LIGHTCORAL);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        VBox vBox = new VBox();
        vBox.setSpacing(spacing);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(canvas, buttonBar());
        return new Scene(vBox);
    }

    private VBox buttonBar() {
        final double buttonWidth = 100;
        final double buttonSpacing = 4;
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(buttonSpacing);
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);
        topBar.setSpacing(buttonSpacing);
        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setSpacing(buttonSpacing);
        Button load = new Button("Load");
        load.setPrefWidth(buttonWidth);
        load.setOnAction(e -> loadAndSaveFile(e));
        Button quit = new Button("Quit");
        quit.setPrefWidth(buttonWidth);
        quit.setOnAction(_ -> {
            Stage stage = (Stage) quit.getScene().getWindow();
            stage.close();
        });
        Button saveBitText = new Button("Save Bit Text");
        saveBitText.setPrefWidth(buttonWidth);
        saveBitText.setOnAction(e -> loadAndSaveFile(e));
        Button saveBlockText = new Button("Save Block Text");
        saveBlockText.setPrefWidth(buttonWidth);
        saveBlockText.setOnAction(e -> loadAndSaveFile(e));
        Button saveBinary = new Button("Save Binary");
        saveBinary.setPrefWidth(buttonWidth);
        saveBinary.setOnAction(e -> loadAndSaveFile(e));
        Button saveBitBinary = new Button("Save Bit Binary");
        saveBitBinary.setPrefWidth(buttonWidth);
        saveBitBinary.setOnAction(e -> loadAndSaveFile(e));
        topBar.getChildren().addAll(saveBitText, saveBlockText, load);
        bottomBar.getChildren().addAll(saveBinary, saveBitBinary, quit);
        buttons.getChildren().addAll(topBar, bottomBar);
        return buttons;
    }

    private void loadAndSaveFile(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        File file;
        if (((Button) e.getSource()).getText().equals("Load")) {
            fileChooser.setTitle("Load File");
            file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    image = new Image(file.toPath());
                    gc.setFill(Color.LIGHTCORAL);
                    gc.fillRect(0, 0, canvasWidth, canvasHeight);
                    image.draw(gc);
                } catch (IOException | IllegalArgumentException | InvalidFormatException ex) {
                    errorAlert(ex);
                }
            }
        } else {
            fileChooser.setTitle("Save File");
            file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    if (((Button) e.getSource()).getText().equals("Save Bit Text")
                            && image != null) {
                        image.save(file.getPath(), "01text");
                    } else if (((Button) e.getSource()).getText().equals("Save Block Text")
                            && image != null) {
                        image.save(file.getPath(), "unicode");
                    } else if (((Button) e.getSource()).getText().equals("Save Binary")
                            && image != null) {
                        image.save(file.getPath(), "ibig");
                    } else if (((Button) e.getSource()).getText().equals("Save Bit Binary")
                            && image != null) {
                        image.save(file.getPath(), "isml");
                    }
                } catch (IOException | IllegalArgumentException ex) {
                    errorAlert(ex);
                }
            }
        }
    }

    private void errorAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.show();
    }
}
