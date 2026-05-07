/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 6 - Observing Coconuts
 * Names: Alex Horton and Kaden Christie
 * Created: 3/3/2025
 */
package username;

import username.islandobjects.IslandObject;

/**
 * An object that manages a list of observers and notifies them on a collision
 */
public interface Subject {
    /**
     * Add an observer to the list
     * @param observer the observer to be added
     */
    void registerObserver(Observer observer);

    /**
     * Removes an observer from the list
     * @param observer the observer to be removed
     */
    void removeObserver(Observer observer);

    /**
     * Calls the update method for each observer currently existing in the game.
     * @param obj1 The object doing the colliding
     * @param obj2 The object being collided with
     */
    void notifyObservers(IslandObject obj1, IslandObject obj2);
}
