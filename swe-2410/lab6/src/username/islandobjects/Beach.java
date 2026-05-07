/*
 * Course: SWE2410 - 111, 121
 * Spring 2026
 * Lecture Code
 * Name: Adela Velez
 * Created: 3/3/2026
 */

package username.islandobjects;

import javafx.scene.image.Image;
import username.GameManager;


/**
 * The beach catches (hits) coconuts and increases the coconut score.
 */
public class Beach extends IslandObject {
    /**
     * Height of the beach on the bottom of the window
     */
    public static final int BEACH_HEIGHT = 50;
    private static final Image BEACH_IMAGE = new Image("file:images/beach.png");
    /**
     * Represents the beach
     * @param game GameController this beach lives in.
     * @param skyHeight Topmost edge of the beach.
     * @param islandWidth Width of the beach.
     */
    public Beach(GameManager game, int skyHeight, int islandWidth) {
        super(game, 0, skyHeight, islandWidth, BEACH_HEIGHT, BEACH_IMAGE);
    }
    /**
     * Island objects can be categorized as either falling,
     * on the ground, or neither
     *
     * @return True if this object is a ground object
     */
    @Override
    public boolean isGroundObject() {
        return true;
    }

    /**
     * Island objects can be categorized as either falling,
     * on the ground, or neither
     *
     * @return True if this object is a falling object
     */
    @Override
    public boolean isFalling() {
        return false;
    }

    /**
     * Island objects can be categorized as not moving
     *
     * @return True if this object does not move
     */
    @Override
    public boolean isStationary() {
        return true;
    }

    /**
     * Returns true if this object can hit the passed-in
     * other objects. Some classes can only hit falling,
     * ground, or neither type of object
     *
     * @param other The object we want to see if we hit.
     * @return True if we can hit the passed-in object
     */
    @Override
    public boolean canHit(IslandObject other) {
        return false;
    }

}
