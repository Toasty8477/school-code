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
import username.Observer;

/**
 * Represents a crab that can shoots laser beams (pew pew) out of its eyes that
 * will destroy falling coconuts. If a coconut hits the crab, the crab is dead.
 */
public class Crab extends IslandObject implements Observer {
    /**
     * Width of the crabs avatar
     */
    public static final int WIDTH = 50;
    /**
     * Height of the crabs' avatar
     */
    public static final int HEIGHT = 50;
    private static final Image CRAB_IMAGE = new Image("file:images/crab-2.png");

    /**
     * Creates a crab
     * @param game Game manager the crab lives in
     * @param skyHeight Height of the sky, which is used to set the y coordinate of the crab.
     * @param islandWidth Width of the island which is used to set the x coordinate of the crab.
     */
    public Crab(GameManager game, int skyHeight, int islandWidth) {
        super(game, islandWidth / 2, skyHeight, WIDTH, HEIGHT, CRAB_IMAGE);
        containingGame.registerObserver(this);
    }

    /**
     * Island objects can be categorized as either falling,
     * on the ground, or neither
     * @return True if this object is a ground object
     */
    public boolean isGroundObject(){
        return true;
    }
    /**
     * Island objects can be categorized as either falling,
     * on the ground, or neither
     * @return True if this object is a falling object
     */
    public boolean isFalling(){
        return false;
    }
    /**
     * Island objects can be categorized as not moving
     * @return True if this object does not move
     */
    public boolean isStationary(){
        return false;
    }
    /**
     * Returns true if this object can hit the passed-in
     * other objects. Some classes can only hit falling,
     * ground, or neither type of object. Also, hits are
     * one directional.
     *
     * @param other The object we want to see if we hit.
     * @return True if we can hit the passed-in object
     */
    @Override
    public boolean canHit(IslandObject other) {
        return false;
    }

    /**
     * Moves the crab to the left or right
     * @param offset Amount to move the crab
     */
    public void crawl(int offset) {
        x += offset;
    }


    /**
     * Called by the subject when a collision occurs to trigger collision behavior.
     * @param obj1 the first object involved in a collision.
     * @param obj2 the second object involved in a collision.
     */
    @Override
    public void update(IslandObject obj1, IslandObject obj2) {
        if (this == obj1 || this == obj2) {
            IslandObject other = this == obj1 ? obj2 : obj1;
            if (other.isFalling()) {
                containingGame.setGameOver();
            }
        }
    }
}
