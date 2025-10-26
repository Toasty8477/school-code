/*
 * Course: CSC1110 - 111
 * Spring 2025
 * Lab 4 - Image Manipulator 3001
 * Name: Alexander Horton
 * Modified 2/18/2025
 */

package hortona;

import javafx.scene.paint.Color;

/**
 * Functional Interface to transform an image
 */
@FunctionalInterface
public interface Transformable {
    /**
     * Applies a transformation to a color given it's y value
     * @param y vertical position in the image
     * @param color original color
     * @return transformed color
     */
    Color apply(int y, Color color);
}
