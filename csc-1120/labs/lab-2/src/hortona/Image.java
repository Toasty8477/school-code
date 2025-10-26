/*
 * Course: CSC-1120 - 131
 * Lab 2 - Image Displayer 3001
 * Name: Alexander Horton
 * Last Updated: 02/04/2025
 */

package hortona;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.EOFException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Loads and save images made of Unicode blocks
 */
public class Image {

    private final int[][] pixels;

    /**
     * A constructor that reads the contents of the imagePath.
     * @param imagePath a filepath to an image
     * @throws IOException if an error occurs reading the file
     * @throws IllegalArgumentException if the format of the file
     * @throws InvalidFormatException
     * does not match one of the supported formats
     */
    public Image(Path imagePath) throws
            IOException, IllegalArgumentException, InvalidFormatException {
        String contents = "";
        int rows = 1;
        int columns;
        boolean binary = false;

        if (imagePath.toString().contains(".txt")) {
            Scanner scanner = new Scanner(imagePath);

            contents += scanner.nextLine();
            if (contents.charAt(0) == '0' || contents.charAt(0) == '1') {
                binary = true;
                columns = contents.length();
            } else {
                columns = contents.length() * 2;
            }

            while (scanner.hasNext()) {
                contents += scanner.nextLine();
                rows++;
            }

            if (binary) {
                pixels = new int[rows][columns];
                for (int i = 0; i < pixels.length; i++) {
                    for (int j = 0; j < pixels[0].length; j++) {
                        if (Integer.parseInt(contents.substring(0, 1)) == 0
                                || Integer.parseInt(contents.substring(0, 1)) == 1) {
                            pixels[i][j] = Integer.parseInt(contents.substring(0, 1));
                            contents = contents.substring(1);
                        } else {
                            throw new InvalidFormatException("File contains malformed data.");
                        }
                    }
                }
            } else {
                pixels = new int[rows * 2][columns];
                for (int i = 0; i < pixels.length; i += 2) {
                    int[] row1 = new int[pixels[0].length];
                    int[] row2 = new int[pixels[0].length];
                    for (int j = 0; j < pixels[0].length; j += 2) {
                        int[][] cluster = PixelCluster.getPixels(contents.charAt(0));
                        row1[j] = cluster[0][0];
                        row1[j + 1] = cluster[0][1];
                        row2[j] = cluster[1][0];
                        row2[j + 1] = cluster[1][1];
                        contents = contents.substring(1);
                    }
                    pixels[i] = row1;
                    pixels[i + 1] = row2;
                }
            }
        } else if (imagePath.toString().contains(".ibig")) {
            DataInputStream dataInputStream =
                    new DataInputStream(new FileInputStream(imagePath.toString()));
            int width = dataInputStream.readInt();
            int height = dataInputStream.readInt();
            pixels = new int[height][width];
            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[0].length; j++) {
                    pixels[i][j] = dataInputStream.readInt();
                }
            }
            dataInputStream.close();
        } else if (imagePath.toString().contains(".isml")) {
            final int shiftStart = 7;
            DataInputStream dataInputStream =
                    new DataInputStream(new FileInputStream(imagePath.toString()));
            int width = dataInputStream.readInt();
            int height = dataInputStream.readInt();
            pixels = new int[height][width];
            byte bIn = dataInputStream.readByte();
            int shift = shiftStart;
            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[0].length; j++) {
                    byte bOut = (byte) (bIn >> shift);
                    bOut = (byte) (bOut << shiftStart);
                    bOut = (byte) (bOut >> shiftStart);
                    if (shift > 0) {
                        shift--;
                        pixels[i][j] = bOut * -1;
                    } else {
                        shift = shiftStart;
                        pixels[i][j] = bOut * -1;
                        try {
                            bIn = dataInputStream.readByte();
                        } catch (EOFException e) {
                            dataInputStream.close();
                        }
                    }

                }
            }
        } else {
            throw new IllegalArgumentException("File is not a .txt, .ibig, or .isml file.");
        }
    }

    @Override
    public String toString() {
        if (pixels.length % 2 == 0) {
            return makeNormal();
        } else {
            return makeUneven();
        }
    }

    /**
     * writes the image to the specified filename using the specified format
     * @param filename the name of the file without the extension
     * @param format 01text or unicode
     * @throws IOException if the file could not be opened
     * @throws IllegalArgumentException if the file is an unsupported format
     * or contains the extension
     */
    public void save(String filename, String format) throws IOException, IllegalArgumentException{
        if (filename.contains(".txt")) {
            throw new IllegalArgumentException("Filename should not contain extension");
        }

        if (format.equalsIgnoreCase("01text")) {
            try(PrintWriter pw = new PrintWriter(filename + ".txt")) {
                for (int i = 0; i < pixels.length; i++) {
                    for (int j = 0; j < pixels[0].length; j++) {
                        pw.print(pixels[i][j]);
                    }
                    pw.print("\n");
                }
            } catch (FileNotFoundException e) {
                throw new IOException("Could not open file");
            }
        } else if (format.equalsIgnoreCase("unicode")) {
            try(PrintWriter pw = new PrintWriter(filename + ".txt")) {
                pw.print(this);
            } catch (FileNotFoundException e) {
                throw new IOException("Could not open file");
            }
        } else if (format.equalsIgnoreCase("ibig")) {
            try(DataOutputStream dataOutputStream =
                        new DataOutputStream(new FileOutputStream(filename + ".ibig"))) {
                dataOutputStream.writeInt(pixels[0].length);
                dataOutputStream.writeInt(pixels.length);
                for (int i = 0; i < pixels.length; i++) {
                    for (int j = 0; j < pixels[0].length; j++) {
                        dataOutputStream.writeInt(pixels[i][j]);
                    }
                }
            }
        } else if (format.equalsIgnoreCase("isml")) {
            final int shiftStart = 7;
            try(DataOutputStream dataOutputStream =
                        new DataOutputStream(new FileOutputStream(filename + ".isml"))) {
                dataOutputStream.writeInt(pixels[0].length);
                dataOutputStream.writeInt(pixels.length);
                int shift = shiftStart;
                byte b = 0;
                for (int i = 0; i < pixels.length; i++) {
                    for (int j = 0; j < pixels[0].length; j++) {
                        b = (byte) ((byte) ((byte) pixels[i][j] << shift) | b);
                        if (shift > 0) {
                            shift--;
                        } else {
                            shift = shiftStart;
                            dataOutputStream.write(b);
                            b = 0;
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Not a supported format");
        }
    }

    /**
     * draws the loaded image to a javaFX canvas
     * @param gc 2d graphics context
     */
    public void draw(GraphicsContext gc) {
        final double scalingFactor = 10;
        gc.setFill(Color.BLACK);
        gc.setTransform(scalingFactor, 0, 0, scalingFactor, 0, 0);
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                if (pixels[i][j] == 1) {
                    gc.fillRect(j, i, 1, 1);
                }
            }
        }
    }

    private String makeNormal() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixels.length; i += 2) {
            int[] row1 = pixels[i];
            int[] row2 = pixels[i+1];
            int[][] cluster = new int[2][2];
            for (int j = 0; j < pixels[0].length; j += 2) {
                cluster[0][0] = row1[j];
                cluster[0][1] = row1[j+1];
                cluster[1][0] = row2[j];
                cluster[1][1] = row2[j+1];
                sb.append(PixelCluster.getCluster(cluster));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String makeUneven() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixels.length - 1; i += 2) {
            int[] row1 = pixels[i];
            int[] row2 = pixels[i+1];
            int[][] cluster = new int[2][2];
            for (int j = 0; j < pixels[0].length - 1; j += 2) {
                cluster[0][0] = row1[j];
                cluster[0][1] = row1[j+1];
                cluster[1][0] = row2[j];
                cluster[1][1] = row2[j+1];
                sb.append(PixelCluster.getCluster(cluster));
            }
            cluster[0][0] = row1[row1.length - 1];
            cluster[0][1] = 0;
            cluster[1][0] = row2[row2.length - 1];
            cluster[1][1] = 0;
            sb.append(PixelCluster.getCluster(cluster));

            sb.append("\n");
        }
        int[] row1 = pixels[pixels.length - 1];
        int[] row2 = new int[pixels[0].length];
        int[][] cluster = new int[2][2];
        for (int i = 0; i < pixels[0].length - 1; i += 2) {
            cluster[0][0] = row1[i];
            cluster[0][1] = row1[i+1];
            cluster[1][0] = row2[i];
            cluster[1][1] = row2[i+1];
            sb.append(PixelCluster.getCluster(cluster));
        }
        cluster[0][0] = row1[row1.length - 1];
        cluster[0][1] = 0;
        cluster[1][0] = row2[row2.length - 1];
        cluster[1][1] = 0;
        sb.append(PixelCluster.getCluster(cluster));
        sb.append("\n");
        return sb.toString();
    }
}
