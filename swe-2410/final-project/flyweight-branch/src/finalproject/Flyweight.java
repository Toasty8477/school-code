/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Horton, Dinan
 */

package finalproject;

import java.util.LinkedList;

import javafx.scene.image.Image;

/**
 * Flyweight
 */
public interface Flyweight {
    /**
     * advance to the next node in the path
     * 
     * @param t
     */
    void advance(Traincar t);

    /**
     * get the image
     * 
     * @return Image
     */
    Image getImage();

    /**
     * get the path
     * 
     * @return path
     */
    LinkedList<PathNode> getPath();
}
