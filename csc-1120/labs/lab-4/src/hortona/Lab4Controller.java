/*
 * Course: CSC1110 - 111
 * Spring 2025
 * Lab 4 - Image Manipulator 3001
 * Name: Alexander Horton
 * Modified 2/18/2025
 */

package hortona;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Controller class for Image Manipulator 3001
 */
public class Lab4Controller {

    private Image image;

    @FXML
    private ImageView imageView;
    @FXML
    private Label colorCode;
    @FXML
    private Button showFilter;

    private Stage filter;
    private Stage main;

    public void setMain(Stage main) {
        this.main = main;
    }
    public void setFilter(Stage filter) {
        this.filter = filter;
    }

    public ImageView getImageView() {
        return imageView;
    }

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
            ImageIO.write(file.toPath(), imageView.getImage());
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
        final double redFilter = 0.21;
        final double greenFilter = 0.71;
        final double blueFilter = 0.07;
        imageView.setImage(transformImage(imageView.getImage(), (_, color) -> Color.gray(
                redFilter*color.getRed() + greenFilter*color.getGreen() +
                        blueFilter*color.getBlue())));
    }
    @FXML
    private void negative() {
        imageView.setImage(transformImage(imageView.getImage(), (_, color) -> Color.color(
                1-color.getRed(), 1-color.getGreen(), 1-color.getBlue())));
    }
    @FXML
    private void red() {
        imageView.setImage(transformImage(imageView.getImage(), (_, color) -> Color.color(
                color.getRed(), 0, 0)));
    }
    @FXML
    private void redGray() {
        final double redFilter = 0.21;
        final double greenFilter = 0.71;
        final double blueFilter = 0.07;
        imageView.setImage(transformImage(imageView.getImage(), (y, color) -> {
            if (y%2 == 0) {
                return Color.color(color.getRed(), 0, 0);
            } else {
                return Color.gray(redFilter*color.getRed() +
                        greenFilter*color.getGreen() + blueFilter*color.getBlue());
            }
        }));
    }
    @FXML
    private void showFilter() {
        filter.setOnHiding(_ -> showFilter.setText("Show Filter"));
        filter.setX(main.getX() + main.getWidth());
        filter.setY(main.getY());
        if (showFilter.getText().equals("Show Filter")) {
            showFilter.setText("Hide Filter");
            filter.show();
        } else {
            showFilter.setText("Show Filter");
            filter.close();
        }
    }

    private Image transformImage(Image image, Transformable transform) {
        WritableImage modified = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = modified.getPixelWriter();
        for (int i = 0; i < modified.getHeight(); i++) {
            for (int j = 0; j < modified.getWidth(); j++) {
                pixelWriter.setColor(j, i, transform.apply(i, pixelReader.getColor(j, i)));
            }
        }
        return modified;
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