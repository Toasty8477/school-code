/*
 * Course: CSC1110 - 111
 * Spring 2025
 * Lab 3 - Image Manipulator 3000
 * Name: Alexander Horton
 * Modified 2/11/2025
 */

package hortona;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Utility Class for Image Input and Output
 */
public class ImageIO {

    /**
     * Reads an image
     * @param path a path to an image
     * @return a JavaFX Image
     * @throws IOException If the file cannot be opened
     * @throws IllegalArgumentException If the file is an unsupported type
     */
    public static Image read(Path path) throws IOException, IllegalArgumentException {
        if (path.toString().contains(".jpg") || path.toString().contains(".png")) {
            return ImageUtil.readImage(path);
        } else if (path.toString().contains(".msoe")) {
            return readMSOE(path);
        } else {
            throw new IllegalArgumentException("File is not a format readable by this application");
        }
    }

    /**
     * Writes and image
     * @param path the file to write the image to
     * @param image a JavaFX Image
     * @throws IOException If the file cannot be found
     * @throws IllegalArgumentException If the file is an unsupported format
     */
    public static void write(Path path, Image image) throws IOException, IllegalArgumentException {
        if (path.toString().contains(".jpg") || path.toString().contains(".png")) {
            ImageUtil.writeImage(path, image);
        } else if (path.toString().contains(".msoe")) {
            writeMSOE(path, image);
        } else {
            throw new IllegalArgumentException("Cannot write this type of file");
        }
    }

    private static Image readMSOE(Path path) throws IOException, IllegalArgumentException {
        Scanner scanner = new Scanner(path);
        if (!scanner.nextLine().equals("MSOE")) {
            throw new IllegalArgumentException("File is not a valid .msoe file");
        }
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelWriter.setColor(j, i, Color.web(scanner.next()));
            }
        }

        return image;
    }

    private static void writeMSOE(Path path, Image image) throws IOException {
        try(PrintWriter printWriter = new PrintWriter(path.toString())) {
            final int hexEnd = 8;
            PixelReader pixelReader = image.getPixelReader();
            printWriter.println("MSOE");
            printWriter.println((int)image.getWidth() + " " + (int)image.getHeight());
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    Color color = pixelReader.getColor(j, i);
                    String hexCode = "#";
                    hexCode += color.toString().substring(2, hexEnd);
                    hexCode += " ";
                    printWriter.println(hexCode.toUpperCase());
                }
            }
        }
    }
}
