/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 6 - Observing Coconuts
 * Names: Alex Horton and Kaden Christie
 * Created: 3/3/2025
 */

package username.islandobjects;

import javafx.scene.image.Image;
import username.GameManager;
import username.Observer;

/**
 * A coconut that falls over the beach
 */
public class Coconut extends IslandObject implements Observer {

    /**
     * Width of the coconut
     */
    public static final int WIDTH = 50;
    /**
     * Height of the coconut
     */
    public static final int HEIGHT = 50;
    private static final Image COCONUT_IMAGE = new Image("file:images/coco-1.png");

    /**
     * Create a coconut
     * @param game the GameManager the coconut is contained in
     * @param x x position of the coconut
     * @param y y position of the coconut
     */
    public Coconut(GameManager game, int x, int y) {
        super(game, x, y, WIDTH, HEIGHT, COCONUT_IMAGE);
        containingGame.registerObserver(this);
    }

    @Override
    public boolean isGroundObject() {
        return false;
    }

    @Override
    public boolean isFalling() {
        return true;
    }

    @Override
    public boolean isStationary() {
        return false;
    }

    @Override
    public boolean canHit(IslandObject other) {
        return other.isGroundObject(); // Crab and ground
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
            if (other.isGroundObject() && other.isStationary()) {
                containingGame.scheduleForDeletion(this);
            } else if (!other.isGroundObject() && !other.isStationary() && !other.isFalling()) {
                containingGame.scheduleForDeletion(this);
            }
        }
    }

    @Override
    public void step() {
        final int distanceToMove = 2;
        y += distanceToMove;
    }
}