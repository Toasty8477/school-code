/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Alex Horton
 * Created: 2/3/2025
 */
package garden;

import javafx.scene.Group;

/**
 * Abstract implementation of a bee to add decorator support
 */
public interface Bee {
    /**
     * Gets the graphics group of the bee
     * @return A Group containing all the graphics for the bee
     */
    Group getGraphics();
    /**
     * Called by the simulation to update the bee every tick
     */
    void step();
    /**
     * Sets the movement behavior of the bee
     * @param moveBehavior A concrete implementation of MoveBehavior
     */
    void setMoveBehavior(MoveBehavior moveBehavior);
    /** 
     * Adds energy to the bee's current energy
     * @param energyToAdd The ammount of energy to add
     */
    void replenishEnergy(int energyToAdd);
    /**
     * Gets the x position of the bee
     * @return bee's x coordinate
     */
    int getX();
    /**
     * Gets the y position of the bee
     * @return bee's y coordinate
     */
    int getY();
    /**
     * Sets the x position of the bee
     * @param x The x coordinate the bee should move to
     */
    void setX(int x);
    /**
     * Sets the y position of the bee
     * @param y The y coordinate the bee should move to
     */
    void setY(int y);
    /**
     * Gets the flower the bee is currently attached to
     * @return The flower the bee is currently attached to
     */
    Flower getAttachedFlower();
    /**
     * Sets the flower the bee should be attached to
     * @param flower The flower the bee should attach to
     */
    void setAttachedFlower(Flower flower);
    /**
     * Returns the energy the bee has
     * @return Bee's current energy
     */
    int getEnergy();
}
