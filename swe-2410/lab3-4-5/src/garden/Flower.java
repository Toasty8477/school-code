/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 5 - Decorate a Garden
 * Name: Dillon Fayas
 * Created: 2/3/2025
 */
package garden;

import javafx.scene.Group;
import java.io.IOException;

/**
 * Flower Component Interface
 */
public interface Flower {
    /**
     * Determines whether a bee can land on the flower
     * @param bee the bee that wants to land
     * @return true if the bee may land
     */
    boolean requestLand(Bee bee);

    /**
     * Determines whether the currently attached bee can leave
     * @return true if the bee may leave
     */
    boolean requestLeave();

    /**
     * Builds and adds graphics for the flower into its internal Group
     * @throws IOException if an image file cannot be read
     */
    void addGraphics() throws IOException;

    /**
     * Returns all graphics for this flower
     * @return graphics group
     */
    Group getGraphics();

    /**
     * Called each simulation tick to advance state
     */
    void step();

    /**
     * Gets the x position
     * @return x position
     */
    int getX();

    /**
     * Gets the y position
     * @return y position
     */
    int getY();

    /**
     * Gets the attached bee
     * @return attached bee
     */
    Bee getAttachedBee();
}