/*
 * Course: CSC1110 - 111
 * Spring 2025
 * Lab 3 - Image Manipulator 3000
 * Name: Alexander Horton
 * Modified 2/11/2025
 */

package hortona;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * Controller class for Image Manipulator 3000
 */
public class Controller {

    private Image image;

    @FXML
    private ImageView imageView;

    @FXML
    private Label colorCode;

    @FXML
    private void openImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        image = ImageIO.read(file.toPath());
        imageView.setImage(image);
    }
    @FXML
    private void saveImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        try {
            ImageIO.write(file.toPath(), image);
        } catch (IOException | IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    private void reloadImage() {
        imageView.setImage(image);
    }
    @FXML
    private void grayscale() {
        WritableImage modified = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = modified.getPixelWriter();
        PixelReader pixelReader = image.getPixelReader();
        for (int i = 0; i < modified.getHeight(); i++) {
            for (int j = 0; j < modified.getWidth(); j++) {
                pixelWriter.setColor(j, i, pixelReader.getColor(j, i).grayscale());
            }
        }
        imageView.setImage(modified);
    }
    @FXML
    private void negative() {
        WritableImage modified = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = modified.getPixelWriter();
        PixelReader pixelReader = image.getPixelReader();
        for (int i = 0; i < modified.getHeight(); i++) {
            for (int j = 0; j < modified.getWidth(); j++) {
                pixelWriter.setColor(j, i, pixelReader.getColor(j, i).invert());
            }
        }
        imageView.setImage(modified);
    }
    @FXML
    private void showColor(MouseEvent e) {
        final int hexEnd = 8;
        PixelReader pixelReader = image.getPixelReader();
        colorCode.setText("#" +
                pixelReader.getColor((int) e.getX(), (int) e.getY()).toString()
                        .substring(2, hexEnd).toUpperCase());
    }
}