/*
 * Course: CSC-1120
 * Lab 1 - Image Displayer 3000
 * Name: TODO
 */
package test;

import hortona.Image;
import hortona.PixelCluster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test suite for Lab 1
 */
public class TestSuite {
    private final String[] codes = {" ", "▗", "▖", "▄", "▝", "▐", "▞", "▟",
            "▘", "▚", "▌", "▙", "▀", "▜", "▛", "█"};
    private List<String> unicodeValues;
    private final int[][][] pixelClusters = new int[][][] {
            {
                    {0, 0}, {0, 0}
            },
            {
                    {0, 0}, {0, 1}
            },
            {
                    {0, 0}, {1, 0}
            },
            {
                    {0, 0}, {1, 1}
            },
            {
                    {0, 1}, {0, 0}
            },
            {
                    {0, 1}, {0, 1}
            },
            {
                    {0, 1}, {1, 0}
            },
            {
                    {0, 1}, {1, 1}
            },
            {
                    {1, 0}, {0, 0}
            },
            {
                    {1, 0}, {0, 1}
            },
            {
                    {1, 0}, {1, 0}
            },
            {
                    {1, 0}, {1, 1}
            },
            {
                    {1, 1}, {0, 0}
            },
            {
                    {1, 1}, {0, 1}
            },
            {
                    {1, 1}, {1, 0}
            },
            {
                    {1, 1}, {1, 1}
            }
    };

    @BeforeEach
    void setup() {
        unicodeValues = new ArrayList<>(List.of(codes));
    }

    @Test
    @DisplayName("Testing PixelCluster enumeration values")
    @Order(1)
    void testEnumValues() throws NoSuchFieldException, IllegalAccessException {
        Field code = PixelCluster.class.getDeclaredField("code");
        code.setAccessible(true);
        PixelCluster[] clusters = PixelCluster.values();

        for (String s : codes) {
            boolean found = false;
            for (int i = 0; i < clusters.length && !found; ++i) {
                if (s.charAt(0) == (Character) code.get(clusters[i])) {
                    found = true;
                    unicodeValues.remove(s);
                }
            }
            Assertions.assertTrue(found, "PixelCluster missing " + s);
        }
        final int numUnicodeValues = 16;
        Assertions.assertEquals(0, unicodeValues.size(),
                "PixelCluster should have 16 distinct values, but only has "
                        + (numUnicodeValues - unicodeValues.size()));
    }

    @Test
    @DisplayName("Testing PixelCluster toString")
    @Order(2)
    void clusterToString() {
        PixelCluster[] clusters = PixelCluster.values();
        for (String s : codes) {
            boolean found = false;
            for (int i = 0; i < clusters.length && !found; ++i) {
                if (s.equals(clusters[i].toString())) {
                    found = true;
                }
            }
            Assertions.assertTrue(found, "PixelCluster missing " + s);
        }
    }

    @Test
    @DisplayName("Testing PixelCluster getCluster")
    @Order(3)
    void getCluster() {
        for (int i = 0; i < pixelClusters.length; ++i) {
            PixelCluster c = PixelCluster.getCluster(pixelClusters[i]);
            Assertions.assertEquals(codes[i], c.toString());
        }
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> PixelCluster.getCluster(new int[3][2]),
                        "Should throw IllegalArgumentException if dimensions are not 2x2"),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> PixelCluster.getCluster(new int[2][3]),
                        "Should throw IllegalArgumentException if dimensions are not 2x2"),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> PixelCluster.getCluster(new int[][] {
                                {1, 0},
                                {2, 1}
                        }),
                        "Should throw IllegalArgumentException if pixel values are not 0 or 1")
        );
    }

    @Test
    @DisplayName("Testing PixelCluster getPixels")
    @Order(4)
    void getPixels() {
        for (int i = 0; i < pixelClusters.length; ++i) {
            int[][] cluster = PixelCluster.getPixels(codes[i].charAt(0));
            Assertions.assertTrue(Arrays.deepEquals(pixelClusters[i], cluster));
        }
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> PixelCluster.getPixels('a'),
                        "Should throw IllegalArgumentException if code is invalid."),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> PixelCluster.getPixels('▅'),
                        "Should throw IllegalArgumentException if code is invalid.")
        );
    }

    @Test
    @DisplayName("Testing Image constructor")
    @Order(5)
    void imageConstructor() throws IOException, NoSuchFieldException {
        final int[][] expected = new int[][] {
                {1, 0},
                {0, 1}
        };
        final int[][] unevenExpected = new int[][] {
                {1, 0, 0, 1, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 1}
        };
        Image image = new Image(Paths.get("src", "test", "smallTest.txt"));
        Image uneven = new Image(Paths.get("src", "test", "uneven.txt"));
        Field pixels = Image.class.getDeclaredField("pixels");
        pixels.setAccessible(true);
        Assertions.assertAll(
                () -> Assertions.assertTrue(Arrays.deepEquals(expected,
                                (Object[]) pixels.get(image)),
                        "Incorrect pixel values. Should be {1, 0}, {0, 1} but are: "
                                + Arrays.deepToString((Object[]) pixels.get(image))),
                () -> Assertions.assertTrue(Arrays.deepEquals(unevenExpected,
                                (Object[]) pixels.get(uneven)),
                        "Incorrect pixel values. Should be {1, 0, 0, 1, 0, 0, 1}, " +
                                "{1, 0, 0, 1, 0, 0, 1}, {1, 0, 0, 1, 0, 0, 1} but are: "
                                + Arrays.deepToString((Object[]) pixels.get(uneven))),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new Image(Paths.get("src", "test", "bad.txt")),
                        "Should throw IllegalArgumentException for file with invalid values"),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> new Image(Paths.get("src", "test", "b")),
                        "Should throw IllegalArgumentException for invalid file type"),
                () -> Assertions.assertThrows(IOException.class,
                        () -> new Image(Paths.get("src", "test", "b.txt")),
                        "Should throw IOException for missing or corrupt files")
        );
    }

    @Test
    @DisplayName("Testing Image toString")
    @Order(6)
    void imageToString() throws IOException, NoSuchFieldException {
        Image image = new Image(Paths.get("src", "test", "smallTest.txt"));
        Field pixels = Image.class.getDeclaredField("pixels");
        pixels.setAccessible(true);
        Assertions.assertEquals("▚\n", image.toString(),
                "toString should return ▚\\n, but returned: " + image);
        image = new Image(Paths.get("src", "test", "test.txt"));
        Assertions.assertEquals("""
                ▙▟▛▜
                ▀▄▌▐
                ▘▝▖▗
                ▚▞█\s
                """, image.toString());
    }

    @Test
    @DisplayName("Testing Image save")
    @Order(7)
    void save() throws IOException {
        File bitText = Paths.get("src", "test", "bitText.txt").toFile();
        File blockText = Paths.get("src", "test", "blockText.txt").toFile();
        Path bitPath = Paths.get("src", "test", "bitText");
        Path blockPath = Paths.get("src", "test", "blockText");

        Image image = new Image(Paths.get("src", "test", "smallTest.txt"));
        image.save(bitPath.toString(), "01text");
        image.save(blockPath.toString(), "unicode");
        Assertions.assertTrue(bitText.exists());
        Assertions.assertTrue(blockText.exists());
        Assertions.assertEquals("▚\n", new Image(bitText.toPath()).toString());
        Assertions.assertEquals("▚\n", new Image(blockText.toPath()).toString());

        image = new Image(Paths.get("src", "test", "test.txt"));
        image.save(bitPath.toString(), "01text");
        image.save(blockPath.toString(), "unicode");
        Assertions.assertEquals("""
                ▙▟▛▜
                ▀▄▌▐
                ▘▝▖▗
                ▚▞█\s
                """, new Image(bitText.toPath()).toString());
        Assertions.assertEquals("""
                ▙▟▛▜
                ▀▄▌▐
                ▘▝▖▗
                ▚▞█\s
                """, new Image(blockText.toPath()).toString());

        image = new Image(Paths.get("src", "test", "uneven.txt"));
        image.save(bitPath.toString(), "01text");
        image.save(blockPath.toString(), "unicode");
        Assertions.assertTrue(bitText.exists());
        Assertions.assertTrue(blockText.exists());
        Assertions.assertEquals("""
                ▌▐ ▌
                ▘▝ ▘
                """, new Image(bitText.toPath()).toString());
        Assertions.assertEquals("""
                ▌▐ ▌
                ▘▝ ▘
                """, new Image(blockText.toPath()).toString());
        Assertions.assertTrue(bitText.delete());
        Assertions.assertTrue(blockText.delete());
    }
}