/*
 * Course: SWE2410 - 111, 121
 * Spring 2026
 * Lecture Code
 * Name: Adela Velez
 * Created: 2/27/2026
 */

package username.islandobjects;

import javafx.scene.image.Image;
import username.GameManager;

/**
 * Represents the sky above the crab and beach.
 */
public class Sky extends IslandObject {
    /**
     * Height of the sky at the top of the window.
     */
    public static final int SKY_HEIGHT = 100;
    private static final Image SKY_IMAGE = new Image("file:images/sky.png");

    /**
     * Creates a sky
     * @param game GameManger the sky resides in
     * @param islandWidth Width of the island
     */
    public Sky(GameManager game, int islandWidth) {
        super(game, 0, 0, islandWidth, SKY_HEIGHT, SKY_IMAGE);
    }

    /**
     * Island objects can be categorized as either falling,
     * on the ground, or neither
     *
     * @return True if this object is a ground object
     */
    @Override
    public boolean isGroundObject() {
        return false;
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
