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
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class the represents a bee in the simulation
 */
public class ConcreteBee implements Bee {

    private static final int DEFAULT_ENERGY = 100;
    
    private final int maxEnergy;
    private int energy;
    private MoveBehavior moveBehavior;

    private int x;
    private int y;

    private Flower attachedFlower;

    private Group graphics;
    private ProgressBar energyBar;
    private Simulation simulation;

    /**
     * Constructor for a bee that gives default values of 100 for energy,
     * 10 for movement distance, and sets the movement pattern randomly.
     * @param simulation A reference to the simulation that the bee belongs to.
     * @param paneWidth The width of the pane in which the bee is contained
     * @param paneHeight The height of the pane in which the bee is contained
     * @throws IOException If bee image is not found
     */
    public ConcreteBee(Simulation simulation,
            double paneWidth, double paneHeight) throws IOException {
        this(simulation, DEFAULT_ENERGY, DEFAULT_ENERGY,
                paneWidth, paneHeight, getRandomMoveBehavior(paneWidth, paneHeight));
    }
    /**
     * Constructor for the bee with all options set by the user
     * @param simulation A reference to the simulation that the bee belongs to.
     * @param energy The initial energy the bee should have
     * @param maxEnergy The maximum energy the bee can have
     * @param paneWidth The width of the pane in which the bee is contained
     * @param paneHeight The height of the pane in which the bee is contained
     * @param moveBehavior What movement pattern the bee should use
     * @throws IOException Throws an IOException if the bee image cannot be found
     */
    public ConcreteBee(Simulation simulation, int energy, int maxEnergy,
                double paneWidth, double paneHeight, MoveBehavior moveBehavior) throws IOException {

        final double initialWidth = 75.0;

        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.moveBehavior = moveBehavior;
        this.graphics = new Group();
        this.simulation = simulation;

        ImageView beeImage;

        if (moveBehavior instanceof MoveZigZag) {
            beeImage = new ImageView(new Image(
                    Files.newInputStream(Paths.get("images/bee-2.png"))));
        } else {
            beeImage = new ImageView(new Image(
                    Files.newInputStream(Paths.get("images/bee-1.png"))));
        }

        beeImage.setPreserveRatio(true); // ensure ratio preserved when scaling the bee
        beeImage.setFitWidth(initialWidth);

        graphics.getChildren().add(beeImage);

        this.energyBar = new ProgressBar();
        graphics.getChildren().add(energyBar);
        energyBar.setProgress(energy);

        Random random = new Random();
        x = random.nextInt(1, (int)paneWidth);
        y = random.nextInt(1, (int)paneHeight);
    }

    @Override
    public Group getGraphics() {
        return graphics;
    }

    @Override
    public void setMoveBehavior(MoveBehavior newMoveBehavior) {
        this.moveBehavior = newMoveBehavior;
    }

    @Override
    public void replenishEnergy(int energyToAdd) {
        energy = Math.min(energy + energyToAdd, maxEnergy);
        energyBar.setProgress((double)energy / maxEnergy);
    }

    @Override
    public void step() {
        final int energyPerStep = 1;

        if (energy > 0) {
            moveBehavior.move(simulation, this);
            energy -= energyPerStep;
        }
        
        energyBar.setProgress((double)energy / maxEnergy);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setAttachedFlower(Flower flower) {
        this.attachedFlower = flower;
    }

    @Override
    public Flower getAttachedFlower() {
        return attachedFlower;
    }

    private static MoveBehavior getRandomMoveBehavior(double paneWidth, double paneHeight) {
        final double thresh = 0.5;
        return Math.random() > thresh ? new MoveRandom() : new MoveZigZag(paneWidth, paneHeight);
    }
    @Override
    public int getEnergy() {
        return energy;
    }
}
