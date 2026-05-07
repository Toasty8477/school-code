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
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A decorator class that makes bees stay away from bees that aren't also fancy
 */
public class DrunkenBee extends BeeDecorator {

    private Group graphics;
    private Random random;

    /**
     * A decorator class for a ConcreteBee that makes it move a random ammount in
     * the x and y directions after each normal move
     * @param bee the ConcreteBee the decorator will use
     */
    public DrunkenBee(Bee bee) {
        super(bee);

        final double initialWidth = 90.0;

        this.graphics = bee.getGraphics();
        this.random = new Random();

        try {
            ImageView bavarianHat = new ImageView(new Image(
                    Files.newInputStream(Paths.get("images/beer_mug.png"))));

            bavarianHat.setPreserveRatio(true); // ensure ratio preserved when scaling the bee
            bavarianHat.setFitWidth(initialWidth);

            this.graphics.getChildren().add(bavarianHat);

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
        final int bound = 550;
        final int minDev = -15;
        final int maxDev = 15;

        bee.step();

        this.setX(Math.max(0, Math.min(this.getX() + random.nextInt(minDev, maxDev), bound)));
        this.setY(Math.max(0, Math.min(this.getY() + random.nextInt(minDev, maxDev), bound)));
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
