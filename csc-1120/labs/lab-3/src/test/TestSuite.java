/*
 * Course: CSC-1120
 * Lab 3 - Image Manipulator 3000
 * Unit Tests
 */
package test;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import hortona.Controller;
import hortona.ImageIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


/**
 * Unit Tests for Lab 3 - Image manipulator 3000
 */
public class TestSuite {
    private final Path jpgTest = Paths.get("src", "test", "jpgTest.jpg");
    private final Path pngTest = Paths.get("src", "test", "pngTest.png");
    private final Path writeTest = Paths.get("src", "test", "out.png");
    private final Path grayTest = Paths.get("src", "test", "gray.png");
    private final Path negativeTest = Paths.get("src", "test", "negative.png");
    private final Path badPath = Paths.get("tests", "test.msoe");
    private final Path badExtension = Paths.get("test.jfif");
    private final WritableImage image = new WritableImage(3, 3);

    @Test
    @DisplayName("Testing read")
    @Order(1)
    void read() throws IOException {
        Assertions.assertThrows(IOException.class, () -> ImageIO.read(badPath),
                "read() should throw an IOException");
        Assertions.assertThrows(IllegalArgumentException.class, () -> ImageIO.read(badExtension));
        testReadImage(jpgTest);
        testReadImage(pngTest);
    }

    @Test
    @DisplayName("Testing write")
    @Order(2)
    void write() throws IOException{
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ImageIO.write(badExtension, image));
        Assertions.assertThrows(IOException.class, () -> ImageIO.write(badPath, image));
        testWriteImage(jpgTest, writeTest);
        testWriteImage(pngTest, writeTest);
    }

    private void testReadImage(Path source) throws IOException {
        Image sourceImage = new Image(new FileInputStream(source.toFile()));
        Image actualImage = ImageIO.read(source);
        compareImages(sourceImage, actualImage);
    }

    private void testWriteImage(Path source, Path target) throws IOException {
        Image sourceImage = new Image(new FileInputStream(source.toFile()));
        ImageIO.write(target, sourceImage);
        Assertions.assertTrue(target.toFile().exists());
        Image actualImage = new Image(new FileInputStream(target.toFile()));
        compareImages(sourceImage, actualImage);
        if(!target.toFile().delete()) {
            System.err.println("Could not delete temporary files.");
        }

    }

    private void compareImages(Image sourceImage, Image actualImage) {
        final double delta = 0.01;
        double width = sourceImage.getWidth();
        double height = sourceImage.getHeight();
        Assertions.assertEquals(width, actualImage.getWidth());
        Assertions.assertEquals(height, actualImage.getHeight());
        PixelReader sourceReader = sourceImage.getPixelReader();
        PixelReader actualReader = actualImage.getPixelReader();
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                Color color = sourceReader.getColor(x, y);
                Color actual = actualReader.getColor(x, y);
                Assertions.assertEquals(color.getRed(), actual.getRed(), delta);
                Assertions.assertEquals(color.getGreen(), actual.getGreen(), delta);
                Assertions.assertEquals(color.getBlue(), actual.getBlue(), delta);
            }
        }
    }

    @Test
    @DisplayName("Testing readMSOE")
    @Order(3)
    void readMSOE() throws IOException {
        Path badPath = Paths.get("tests", "test.msoe");
        Path badMSOE = Paths.get("src", "test", "badMSOE.msoe");
        Path path = Paths.get("src", "test", "test.msoe");
        Image image = ImageIO.read(path);
        PixelReader pr = image.getPixelReader();
        Assertions.assertAll(
                () -> Assertions.assertEquals(Color.web("#000000"), pr.getColor(0, 0),
                        "Pixel [0, 0] should contain #000000 but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertEquals(Color.web("#FFFFFF"), pr.getColor(1, 0),
                        "Pixel [1, 0] should contain #FFFFFF but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertEquals(Color.web("#000000"), pr.getColor(2, 0),
                        "Pixel [2, 0] should contain #000000 but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertEquals(Color.web("#FFFFFF"), pr.getColor(0, 1),
                        "Pixel [0, 1] should contain #FFFFFF but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertEquals(Color.web("#FFFFFF"), pr.getColor(1, 1),
                        "Pixel [1, 1] should contain #FFFFFF but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertEquals(Color.web("#FFFFFF"), pr.getColor(2, 1),
                        "Pixel [2, 1] should contain #FFFFFF but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertEquals(Color.web("#000000"), pr.getColor(0, 2),
                        "Pixel [0, 2] should contain #000000 but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertEquals(Color.web("#FFFFFF"), pr.getColor(1, 2),
                        "Pixel [1, 2] should contain #FFFFFF but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertEquals(Color.web("#000000"), pr.getColor(2, 2),
                        "Pixel [2, 2] should contain #000000 but contains " + pr.getColor(0, 0)),
                () -> Assertions.assertThrows(NoSuchFileException.class,
                        () -> ImageIO.read(badPath)),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> ImageIO.read(badMSOE))
        );
    }

    @Test
    @DisplayName("Testing writeMSOE")
    @Order(4)
    void writeMSOE() throws IOException {
        Path path = Paths.get("src", "test", "output.msoe");
        Path source = Paths.get("src", "test", "test.msoe");
        Path badPath = Paths.get("tests", "test.msoe");
        Image image = ImageIO.read(source);
        ImageIO.write(path, image);
        try (Scanner in = new Scanner(path.toFile());
                Scanner sourceRead = new Scanner(source.toFile())) {
            while (sourceRead.hasNext()) {
                String expected = sourceRead.next().toLowerCase();
                String actual = in.next().toLowerCase();
                Assertions.assertEquals(expected, actual,
                        "Should have written " + expected + " but wrote " + actual);
            }
        }
        Assertions.assertThrows(FileNotFoundException.class,
                () -> ImageIO.write(badPath, image));
        if (!path.toFile().delete()) {
            System.err.println("Could not delete temp file");
        }
    }


    @Test
    @DisplayName("Testing grayscale")
    @Order(5)
    void testGrayscale() throws NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException, IOException, InvocationTargetException {
        Method grayscale = Controller.class.getDeclaredMethod("grayscale");
        grayscale.setAccessible(true);
        ImageView view = new ImageView();
        runFilter(view, grayscale);
        Image source = new Image(new FileInputStream(grayTest.toFile()));
        compareImages(source, view.getImage());
    }

    @Test
    @DisplayName("Testing negate")
    @Order(6)
    void testNegative() throws NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException, IOException, InvocationTargetException {
        Method negative = Controller.class.getDeclaredMethod("negative");
        negative.setAccessible(true);
        ImageView view = new ImageView();
        runFilter(view, negative);
        Image source = new Image(new FileInputStream(negativeTest.toFile()));
        compareImages(source, view.getImage());
    }

    private void runFilter(ImageView view, Method method) throws NoSuchFieldException,
            IOException, IllegalAccessException, InvocationTargetException {
        Controller controller = new Controller();
        Field imageView = Controller.class.getDeclaredField("imageView");
        imageView.setAccessible(true);
        Image image = new Image(new FileInputStream(pngTest.toFile()));
        view.setImage(image);
        imageView.set(controller, view);
        method.invoke(controller);
    }
}
