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
 * A laser beam for the crab to fire
 */
public class LaserBeam extends IslandObject implements Observer {

    /**
     * Width of the beam
     */
    public static final int WIDTH = 50;
    /**
     * Height of the beam
     */
    public static final int HEIGHT = 50;
    private static final Image BEAM_IMAGE = new Image("file:images/laser-1.png");

    /**
     * Make a laser beam
     * @param game The GameManager the laser is contained in
     * @param x the x position of the beam
     * @param y the y position of the beam
     */
    public LaserBeam(GameManager game, int x, int y) {
        super(game, x, y - HEIGHT, WIDTH, HEIGHT, BEAM_IMAGE);
        containingGame.registerObserver(this);
    }

    @Override
    public boolean isGroundObject() {
        return false;
    }

    @Override
    public boolean isFalling() {
        return false;
    }

    @Override
    public boolean isStationary() {
        return false;
    }

    @Override
    public boolean canHit(IslandObject other) {
        return other.isFalling() || other.isStationary() && !other.isGroundObject();
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
                containingGame.scheduleForDeletion(this);
            } else if (other.isStationary() || !other.isGroundObject()) {
                containingGame.scheduleForDeletion(this);
            }
        }
    }

    @Override
    public void step() {
        final int distanceToMove = 5;
        y -= distanceToMove;
    }
}