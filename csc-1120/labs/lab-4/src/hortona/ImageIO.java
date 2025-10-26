/*
 * Course: CSC1110 - 111
 * Spring 2025
 * Lab 4 - Image Manipulator 3001
 * Name: Alexander Horton
 * Modified 2/18/2025
 */

package hortona;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Image file IO for Image Manipulator 3001
 */
public class ImageIO {
    private static final int RED_OFFSET = 16;
    private static final int GREEN_OFFSET = 8;
    private static final int ALPHA_OFFSET = 24;
    private static final int MASK = 0x000000FF;
    private static final double SCALAR = 255.0;


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
        } else if (path.toString().contains(".bmsoe")) {
            return readBMSOE(path);
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
        } else if (path.toString().contains(".bmsoe")) {
            writeBMSOE(path, image);
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

    private static void writeBMSOE(Path path, Image image) throws IOException {
        try(DataOutputStream dataOutputStream = new DataOutputStream(
                new FileOutputStream(path.toString()))) {
            final String header = "BMSOE";
            PixelReader pixelReader = image.getPixelReader();

            for (int i = 0; i < header.length(); i++) {
                dataOutputStream.writeByte(header.charAt(i));
            }

            dataOutputStream.writeInt((int) image.getWidth());
            dataOutputStream.writeInt((int) image.getHeight());
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    dataOutputStream.writeInt(colorToInt(pixelReader.getColor(j, i)));
                }
            }
        }
    }

    private static Image readBMSOE(Path path) throws IOException, IllegalArgumentException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(path.toString()));
        final int headerLength = 5;
        String type = "";
        int width;
        int height;

        for (int i = 0; i < headerLength; i++) {
            type += (char)dataInputStream.read();
        }
        if (!type.equals("BMSOE")) {
            throw new IllegalArgumentException();
        }

        width = dataInputStream.readInt();
        height = dataInputStream.readInt();
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelWriter.setColor(j, i, intToColor(dataInputStream.readInt()));
            }
        }
        dataInputStream.close();
        return image;
    }



    private static Color intToColor(int color) {
        double red = ((color >> RED_OFFSET) & MASK)/SCALAR;
        double green = ((color >> GREEN_OFFSET) & MASK)/SCALAR;
        double blue = (color & MASK)/SCALAR;
        double alpha = ((color >> ALPHA_OFFSET) & MASK)/SCALAR;
        return new Color(red, green, blue, alpha);
    }

    private static int colorToInt(Color color) {
        int red = ((int)(color.getRed() * SCALAR)) & MASK;
        int green = ((int)(color.getGreen() * SCALAR)) & MASK;
        int blue = ((int)(color.getBlue() * SCALAR)) & MASK;
        int alpha = ((int)(color.getOpacity() * SCALAR)) & MASK;
        return (alpha << ALPHA_OFFSET) + (red << RED_OFFSET) + (green << GREEN_OFFSET) + blue;
    }
}
