/*
 * Course: CSC1020
 * Lab 4 - Image Manipulator 3000 ImageIO Tests
 * Name: Sean Jones
 */
package test;

import javafx.scene.image.ImageView;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import hortona.ImageIO;
import hortona.Lab4Controller;
import hortona.Transformable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.testng.annotations.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Tests msoe and bmsoe read and write
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSuite {
    private final Path jpgTest = Paths.get("src", "test", "jpgTest.jpg");
    private final Path pngTest = Paths.get("src", "test", "pngTest.png");
    private final Path writeTest = Paths.get("src", "test", "out.png");
    private final Path grayTest = Paths.get("src", "test", "grayTest.png");
    private final Path negativeTest = Paths.get("src", "test", "negativeTest.png");
    private final Path redTest = Paths.get("src", "test", "redTest.png");
    private final Path redGrayTest = Paths.get("src", "test", "redGrayTest.png");
    private final Path blueTest = Paths.get("src", "test", "blueTest.png");
    private final Path badPath = Paths.get("tests", "test.msoe");
    private final Path badExtension = Paths.get("test.jfif");
    private final WritableImage image = new WritableImage(3, 3);

    @Test
    @DisplayName("Testing read()")
    @Order(1)
    void testRead() throws IOException {
        Assertions.assertThrows(IOException.class, () -> ImageIO.read(badPath),
                "read() should throw an IOException when given a bad path");
        Assertions.assertThrows(IllegalArgumentException.class, () -> ImageIO.read(badExtension),
                "read() should throw an IllegalArgumentException when a file with an " +
                        "unsupported extension is given.");
        testReadImage(jpgTest);
        testReadImage(pngTest);
    }

    @Test
    @DisplayName("Testing write()")
    @Order(2)
    void testWrite() throws IOException {
        Assertions.assertThrows(IOException.class, () -> ImageIO.write(badPath, image),
                "write() should throw an IOException when given a bad path");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ImageIO.write(badExtension, image),
                "write() should throw an IllegalArgumentException when a file with an " +
                        "unsupported extension is given.");
        testWriteImage(jpgTest, writeTest);
        testWriteImage(pngTest, writeTest);
    }

    private void testReadImage(Path source) throws IOException {
        Image sourceImage = new Image(new FileInputStream(source.toFile()));
        Image actualImage = ImageIO.read(source);
        compareImages(sourceImage, actualImage, "read");
    }

    private void testWriteImage(Path source, Path target) throws IOException {
        Image sourceImage = new Image(new FileInputStream(source.toFile()));
        ImageIO.write(target, sourceImage);
        Assertions.assertTrue(target.toFile().exists(),
                "Target file not created.");
        Image actualImage = new Image(new FileInputStream(target.toFile()));
        compareImages(sourceImage, actualImage, "write");
        if(!target.toFile().delete()) {
            System.err.println("Could not delete temp file.");
        }

    }

    private void compareImages(Image sourceImage, Image actualImage, String test) {
        double width = sourceImage.getWidth();
        double height = sourceImage.getHeight();
        Assertions.assertEquals(width, actualImage.getWidth());
        Assertions.assertEquals(height, actualImage.getHeight());
        PixelReader sourceReader = sourceImage.getPixelReader();
        PixelReader actualReader = actualImage.getPixelReader();
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                Assertions.assertEquals(sourceReader.getColor(x, y), actualReader.getColor(x, y),
                        "Error " + test + "ing. " + "Expected " +sourceReader.getColor(x, y) +
                        " but was " + actualReader.getColor(x, y) + ".");
            }
        }
    }

    @Test
    @DisplayName("Testing readMSOE")
    @Order(3)
    void readMSOE() throws IOException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Path path = Paths.get("src", "test", "test.msoe");
        Method readMSOEMethod = ImageIO.class.getDeclaredMethod("readMSOE", Path.class);
        readMSOEMethod.setAccessible(true);
        Image msoeImage = (Image) readMSOEMethod.invoke(null, path);
        Image image = ImageIO.read(path);
        compareImages(msoeImage, image, "MSOE read");
        PixelReader pr = image.getPixelReader();
        Exception exception = getException(badPath);
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
                        "Pixel [2, 2] should contain #000000 but contains " + pr.getColor(0, 0))
        );
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ImageIO.read(Paths.get("src", "test", "badHeader.msoe")),
                "Should throw an IllegalArgumentException when a bad header is encountered");
        Assertions.assertNotNull(exception, "Expected exception when a bad path is passed in");
        Assertions.assertTrue(exception instanceof FileNotFoundException ||
                exception instanceof NoSuchFileException, "Exception type mismatch: " + exception);
        Assertions.assertThrows(InputMismatchException.class,
                () -> ImageIO.read(Paths.get("src", "test", "badDimensions.msoe")),
                "Should throw an InputMismatchException when a bad header is encountered");
        Assertions.assertThrows(InputMismatchException.class,
                () -> ImageIO.read(Paths.get("src", "test", "doubleDimensions.msoe")),
                "Should throw an InputMismatchException when a bad header is encountered");
    }

    private Exception getException(Path badPath) throws IOException {
        Exception exception = null;
        try {
            ImageIO.read(badPath);
        } catch(NoSuchFileException | FileNotFoundException e) {
            exception = e;
        }
        return exception;
    }

    @Test
    @DisplayName("Testing writeMSOE")
    @Order(4)
    void writeMSOE() throws IOException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Path path = Paths.get("src", "test", "output.msoe");
        Path msoePath = Paths.get("src", "test", "output2.msoe");
        Path source = Paths.get("src", "test", "test.msoe");
        Image image = ImageIO.read(source);
        ImageIO.write(path, image);
        Method writeMSOEMethod = ImageIO.class
                .getDeclaredMethod("writeMSOE", Path.class, Image.class);
        writeMSOEMethod.setAccessible(true);
        writeMSOEMethod.invoke(null, msoePath, image);
        try (Scanner in = new Scanner(path.toFile());
                Scanner msoeIn = new Scanner(msoePath);
                Scanner sourceRead = new Scanner(source.toFile())) {
            while (sourceRead.hasNext()) {
                String expected = sourceRead.next().toLowerCase();
                String msoeExpected = msoeIn.next().toLowerCase();
                String actual = in.next().toLowerCase();
                Assertions.assertEquals(expected, actual,
                        "Should have written " + expected + " but wrote " + actual);
                Assertions.assertEquals(expected, actual,
                        "Should have written " + msoeExpected + " but wrote " + actual);
            }
        }
        Assertions.assertThrows(FileNotFoundException.class,
                () -> ImageIO.write(badPath, image));
        if (!path.toFile().delete() || !msoePath.toFile().delete()) {
            System.err.println("Could not delete temp file.");
        }
    }

    @Test
    @DisplayName("Testing readBMSOE")
    @Order(5)
    void readBMSOE() throws IOException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Path source = Paths.get("src", "test", "test.bmsoe");
        Method readBMSOEMethod = ImageIO.class.getDeclaredMethod("readBMSOE", Path.class);
        readBMSOEMethod.setAccessible(true);
        Method intToColor = ImageIO.class.getDeclaredMethod("intToColor", int.class);
        intToColor.setAccessible(true);
        Image bmsoeImage = (Image) readBMSOEMethod.invoke(null, source);
        Image image = ImageIO.read(source);
        compareImages(bmsoeImage, image, "BMSOE read");
        try (DataInputStream dis = new DataInputStream(new FileInputStream(source.toFile()))) {
            String header = "BMSOE";
            StringBuilder actualHeader = new StringBuilder();
            for(int i = 0; i < header.length(); ++i) {
                actualHeader.append((char) dis.readByte());
            }
            Assertions.assertEquals(header, actualHeader.toString(),
                    "Header should have been BMSOE, but was " + actualHeader);
            PixelReader pr = image.getPixelReader();
            int width = dis.readInt();
            int height = dis.readInt();
            Assertions.assertEquals(width, (int) image.getWidth(),
                    "Reading BMSOE, width should be " + width + " but is "
                            + (int) image.getWidth());
            Assertions.assertEquals(height, (int) image.getHeight(),
                    "Reading BMSOE, height should be " + height + " but is "
                            + (int) image.getHeight());
            for(int y = 0; y < image.getHeight(); ++y) {
                for(int x = 0; x < image.getWidth(); ++x) {
                    Color color = (Color) intToColor.invoke(null, dis.readInt());
                    Assertions.assertEquals(color, pr.getColor(x, y),
                            "Reading BMSOE, pixel at " + x + ", " + y +" should be " + color +
                                    " but is " + pr.getColor(x, y));
                }
            }
        }
        Assertions.assertThrows(IOException.class, () -> ImageIO.read(badPath),
                "When reading a BMSOE file, a bad path should throw an IOException.");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ImageIO.read(Paths.get("src", "test", "badHeader.bmsoe")),
                "When reading a BMSOE file, a bad file header " +
                        "should throw an IllegalArgumentException.");
    }

    @Test
    @DisplayName("Testing writeBMSOE")
    @Order(6)
    void writeBMSOE() throws IOException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        Path path = Paths.get("src", "test", "output.bmsoe");
        Path bmsoePath = Paths.get("src", "test", "output2.bmsoe");
        Path source = Paths.get("src", "test", "test.msoe");
        Method writeBMSOEMethod = ImageIO.class.getDeclaredMethod(
                "writeBMSOE", Path.class, Image.class);
        writeBMSOEMethod.setAccessible(true);
        Image image = ImageIO.read(source);
        writeBMSOEMethod.invoke(null, bmsoePath, image);
        ImageIO.write(path, image);
        Image bmsoeImage = ImageIO.read(bmsoePath);
        Assertions.assertTrue(path.toFile().exists(),
                "File output.bmsoe should exist but does not.");
        Assertions.assertTrue(bmsoePath.toFile().exists(),
                "File output2.bmsoe should exist but does not.");
        compareImages(image, bmsoeImage, "BMSOE write");
        try (Scanner read = new Scanner(source.toFile());
                DataInputStream dis = new DataInputStream(new FileInputStream(path.toFile()))) {
            String header = "BMSOE";
            StringBuilder actualHeader = new StringBuilder();
            for(int i = 0; i < header.length(); ++i) {
                actualHeader.append((char) dis.readByte());
            }
            Assertions.assertEquals(header, actualHeader.toString(),
                    "Header should have been BMSOE, but was " + actualHeader);
            read.nextLine();
            int width = read.nextInt();
            int height = read.nextInt();
            int actualWidth = dis.readInt();
            int actualHeight = dis.readInt();
            Assertions.assertEquals(width, actualWidth,
                    "Write BMSOE, width should be " + width + " but it is " + actualWidth);
            Assertions.assertEquals(width, actualWidth,
                    "Write BMSOE, height should be " + height + " but it is " + actualHeight);
            Method intToColor = ImageIO.class.getDeclaredMethod("intToColor", int.class);
            intToColor.setAccessible(true);
            while(read.hasNext()) {
                Assertions.assertEquals(Color.web(read.next()),
                        intToColor.invoke(null, dis.readInt()));
            }

        }
        Assertions.assertThrows(FileNotFoundException.class, () -> ImageIO.write(badPath, image),
                "Write BMSOE should throw an IllegalArgumentException if the path is bad.");
        if (!path.toFile().delete() || !bmsoePath.toFile().delete()) {
            System.err.println("Could not delete temp file");
        }
    }

    @Test
    @DisplayName("Testing grayscale")
    @Order(7)
    void testGrayscale() throws NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException, IOException, InvocationTargetException {
        Method grayscale = Lab4Controller.class.getDeclaredMethod("grayscale");
        grayscale.setAccessible(true);
        ImageView view = new ImageView();
        runFilter(view, grayscale);
        Image source = new Image(new FileInputStream(grayTest.toFile()));
        compareImages(source, view.getImage(), "grayscale transform");
    }

    @Test
    @DisplayName("Testing negate")
    @Order(8)
    void testNegative() throws NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException, IOException, InvocationTargetException {
        Method negative = Lab4Controller.class.getDeclaredMethod("negative");
        negative.setAccessible(true);
        ImageView view = new ImageView();
        runFilter(view, negative);
        Image source = new Image(new FileInputStream(negativeTest.toFile()));
        compareImages(source, view.getImage(), "negative transform");
    }

    @Test
    @DisplayName("Testing Red")
    @Order(9)
    void testRed() throws NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException, IOException, InvocationTargetException {
        Method red = Lab4Controller.class.getDeclaredMethod("red");
        red.setAccessible(true);
        ImageView view = new ImageView();
        runFilter(view, red);
        Image source = new Image(new FileInputStream(redTest.toFile()));
        compareImages(source, view.getImage(), "Red transform");
    }

    @Test
    @DisplayName("Testing RedGray")
    @Order(10)
    void testRedGray() throws NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException, IOException, InvocationTargetException {
        Method redGray = Lab4Controller.class.getDeclaredMethod("redGray");
        redGray.setAccessible(true);
        ImageView view = new ImageView();
        runFilter(view, redGray);
        Image source = new Image(new FileInputStream(redGrayTest.toFile()));
        compareImages(source, view.getImage(), "Red-Gray transform");
    }

    @Test
    @DisplayName("Testing transformImage")
    @Order(11)
    void transform() throws NoSuchMethodException, IOException, InvocationTargetException,
            IllegalAccessException {
        final int colorScalar = 255;
        Lab4Controller controller = new Lab4Controller();
        Method transformImage = Lab4Controller.class.getDeclaredMethod(
                "transformImage", Image.class, Transformable.class);
        transformImage.setAccessible(true);
        Transformable transformBlue =
                (_, c) -> Color.rgb(0, 0, (int) (c.getBlue() * colorScalar), 1);
        Image source = ImageIO.read(pngTest);
        Image blue = ImageIO.read(blueTest);
        Image actual = (Image) transformImage.invoke(controller, source, transformBlue);
        compareImages(blue, actual, "Blue transform");
    }

    private void runFilter(ImageView view, Method method) throws NoSuchFieldException,
            IOException, IllegalAccessException, InvocationTargetException {
        Lab4Controller controller = new Lab4Controller();
        Field imageView = Lab4Controller.class.getDeclaredField("imageView");
        imageView.setAccessible(true);
        Image image = new Image(new FileInputStream(pngTest.toFile()));
        view.setImage(image);
        imageView.set(controller, view);
        method.invoke(controller);
    }
}
