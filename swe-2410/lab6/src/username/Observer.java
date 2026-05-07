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
 * Object that watches for collisions and is updated by the subject when one occurs.
 */
public interface Observer {
    /**
     * Called by the subject when a collision occurs to trigger collision behavior.
     * @param obj1 the first object involved in a collision.
     * @param obj2 the second object involved in a collision.
     */
    void update(IslandObject obj1, IslandObject obj2);
}
