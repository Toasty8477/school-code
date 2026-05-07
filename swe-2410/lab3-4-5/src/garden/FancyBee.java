/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Alex Horton
 * Created: 2/3/2025
 */

package garden;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A decorator class that makes bees stay away from bees that aren't also fancy
 */
public class FancyBee extends BeeDecorator {

    private Simulation simulation;
    private Group graphics;

    /**
     * A decorator class for a ConcreteBee that makes it avoid non fancy bees
     * @param bee the ConcreteBee the decorator will use
     * @param simulation The simulation the bee is in
     */
    public FancyBee(Bee bee, Simulation simulation) {
        super(bee);

        final double initialWidth = 75.0;
        
        this.simulation = simulation;
        this.graphics = bee.getGraphics();

        try {
            ImageView topHat = new ImageView(new Image(
                    Files.newInputStream(Paths.get("images/top_hat.png"))));

            topHat.setPreserveRatio(true); // ensure ratio preserved when scaling the bee
            topHat.setFitWidth(initialWidth);

            this.graphics.getChildren().add(topHat);

        } catch (IOException e) {
            System.out.println("Could not load image");
        }
    }

    @Override
    public Group getGraphics() {
        return graphics;
    }

    @Override
    public void step() {
        bee.step();

        final int distance = 50;
        final int bound = 550;

        for (Bee bee : simulation.getBees()) {
            if (!(bee instanceof FancyBee) && !this.equals(bee)) {
                double distanceBetween = Math.sqrt(Math.pow(getX() - bee.getX(), 2)
                        + Math.pow(getY() - bee.getY(), 2));
                if (distanceBetween < distance) {
                    if (this.getX() > bee.getX()) {
                        this.setX(this.getX() + (int)distanceBetween);
                    } else {
                        this.setX(this.getX() + 1 - ((int)distanceBetween));
                    }
                    if (this.getY() > bee.getY()) {
                        this.setY(this.getY() + (int)distanceBetween);
                    } else {
                        this.setY(this.getY() + 1 - ((int)distanceBetween));
                    }
                }
            }
        }
        // Keep bee in bounds
        this.setX(Math.max(0, Math.min(this.getX(), bound)));
        this.setY(Math.max(0, Math.min(this.getY(), bound)));
    }

    @Override
    public void setMoveBehavior(MoveBehavior moveBehavior) {
        bee.setMoveBehavior(moveBehavior);
    }

    @Override
    public void replenishEnergy(int energyToAdd) {
        bee.replenishEnergy(energyToAdd);
    }

    @Override
    public int getX() {
        return bee.getX();
    }

    @Override
    public int getY() {
        return bee.getY();
    }

    @Override
    public void setX(int x) {
        bee.setX(x);
    }

    @Override
    public void setY(int y) {
        bee.setY(y);
    }

    @Override
    public Flower getAttachedFlower() {
        return bee.getAttachedFlower();
    }

    @Override
    public void setAttachedFlower(Flower flower) {
        bee.setAttachedFlower(flower);
    }

    @Override
    public int getEnergy() {
        return bee.getEnergy();
    }
    
}
