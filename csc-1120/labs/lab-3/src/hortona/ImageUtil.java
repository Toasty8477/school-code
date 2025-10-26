/*
 * Course: CSC1120
 * Lab 3: Image Manipulator (3000)
 * ImageUtil
 * Chris Taylor
 * Updated: 1/25/2019
 */
package hortona;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Image utility class to facilitate reading/writing standard image formats
 * and provide additional helper methods related to images.
 *
 * @author taylor
 * @version 20190125.1
 */
public final class ImageUtil {
    /**
     * Writes the image to the file specified by path. The image format
     * is based on the file extension specified in the path.
     *
     * @param path  Location of the image file
     * @param image The image to be written
     * @throws IllegalArgumentException if the path extension specifies an unsupported image format
     * @throws IOException              if an error occurs during reading
     */
    public static void writeImage(Path path, Image image) throws IOException,
            IllegalArgumentException {
        BufferedImage bufferedImag = SwingFXUtils.fromFXImage(image, null);
        String extension = getExtension(path);
        final String[] validExtensions = {"bmp", "gif", "jpg", "png", "tiff"};
        if (Arrays.binarySearch(validExtensions, extension) < 0) {
            throw new IllegalArgumentException("File extension: ." + extension
                    + " does not specify a supported image format.");
        }
        try (OutputStream out = Files.newOutputStream(path)) {
            // Remove transparency layer to read JPGs since OpenJDK does not provide a native
            // JPEG encoder.
            // See https://blog.sixthpoint.com/bufferedimage-jpeg-transparency-using-openjdk/
            if (extension.equals("jpg") || extension.equals("bmp")) {
                BufferedImage convertedImage = new BufferedImage(bufferedImag.getWidth(),
                        bufferedImag.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                convertedImage.getGraphics().drawImage(bufferedImag, 0, 0, null);
                convertedImage.getGraphics().dispose();
                bufferedImag = convertedImage;
            }
            ImageIO.write(bufferedImag, extension, out);
        }
    }

    /**
     * Returns an Image as a result of decoding a supplied Path.
     *
     * @param path Location of the image file to read
     * @return an Image containing the decoded contents of the input
     * @throws IllegalArgumentException if path is null
     * @throws IOException              if an error occurs during reading
     */
    public static Image readImage(Path path) throws IOException, IllegalArgumentException {
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }
        try (InputStream in = Files.newInputStream(path)) {
            return SwingFXUtils.toFXImage(ImageIO.read(in), null);
        }
    }

    /**
     * Returns an image that has the filter kernel applied to it.
     *
     * @param image  the image on which the filter is applied
     * @param kernel the filter kernel to apply to the image
     * @return The resulting image after the filter kernel has been applied to image.
     * @throws IllegalArgumentException thrown if the kernel size is incorrect
     */
    public static Image convolve(Image image, double[] kernel) throws IllegalArgumentException {
        final int[] validKernelSizes = {1, 2 * 2, 3 * 3, 4 * 4, 5 * 5, 6 * 6};
        if (Arrays.binarySearch(validKernelSizes, kernel.length) < 0) {
            throw new IllegalArgumentException("The kernel array must be a power of two no " +
                    "greater than 36");
        }
        float[] kernelFloats = new float[kernel.length];
        for (int i = 0; i < kernel.length; i++) {
            kernelFloats[i] = (float) kernel[i];
        }
        int kernelSize = (int) Math.sqrt(kernel.length);
        BufferedImageOp op = new ConvolveOp(new Kernel(kernelSize, kernelSize, kernelFloats));
        BufferedImage result = op.filter(SwingFXUtils.fromFXImage(image, null), null);
        return SwingFXUtils.toFXImage(result, null);
    }

    /**
     * Gets the file extension (all characters after last '.') of a given file name, or throws
     * an exception
     *
     * @param path Full name of the file
     * @return just the file extension in lowercase letters
     * @throws IllegalArgumentException when no file extension is found
     */
    private static String getExtension(Path path) {
        String filename = path.toString();
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            throw new IllegalArgumentException("No file extension for " + path);
        }
        return filename.substring(dotIndex + 1).toLowerCase();
    }
}
