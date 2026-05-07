/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Alex Horton
 * Created: 2/3/2025
 */
package garden;

/**
 * Movement behavior for a Bee
 */
public interface MoveBehavior {
    /**
     * move method to step a bee
     * @param simulation a simulation object
     * @param bee a bee
     */
    void move(Simulation simulation, Bee bee);
}