/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 6 - Observing Coconuts
 * Names: Alex Horton and Kaden Christie
 * Created: 3/3/2025
 */
package username;

import javafx.scene.control.Label;
import username.islandobjects.IslandObject;

/**
 * Handles logic for the game's scoreboard
 */
public class Scoreboard implements Observer {
    private int laserStrikes;
    private int beachStrikes;
    private final Label laserLabel;
    private final Label beachLabel;

    /**
     * Creates a new scoreboard object for keeping track of laser and beach strikes.
     * @param laserLabel the FX:ID for the laser strikes label
     * @param beachLabel the FX:ID for the beach strikes label
     */
    public Scoreboard(Label laserLabel, Label beachLabel) {
        laserStrikes = 0;
        beachStrikes = 0;
        this.laserLabel = laserLabel;
        this.beachLabel = beachLabel;
    }

    /**
     * Resets the scoreboard back to default values.
     */
    public void restart() {
        laserStrikes = 0;
        beachStrikes = 0;
        laserLabel.setText("Laser Strikes: 0");
        beachLabel.setText("Beach Strikes: 0");
    }

    /**
     * Called by the subject when a collision occurs to trigger collision behavior.
     *
     * @param obj1 the first object involved in a collision.
     * @param obj2 the second object involved in a collision.
     */
    @Override
    public void update(IslandObject obj1, IslandObject obj2) {
        if (obj1.isFalling() || obj2.isFalling()) {
            IslandObject other = obj1.isFalling() ? obj2 : obj1;
            if (!other.isGroundObject() && !other.isFalling() && !other.isStationary()) {
                laserStrikes++;
                laserLabel.setText("Laser Strikes: " + laserStrikes);
            } else if (other.isStationary() && other.isGroundObject()) {
                beachStrikes++;
                beachLabel.setText("Beach Strikes: " + beachStrikes);
            }
        }
    }
}
