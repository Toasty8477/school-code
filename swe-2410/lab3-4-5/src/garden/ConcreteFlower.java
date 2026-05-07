/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 5 - Decorate a Garden
 * Name: Dillon Fayas
 * Created: 2/3/2025
 */
package garden;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Concrete component of flower
 */
public class ConcreteFlower implements Flower {
    private static final int DEFAULT_COOLDOWN = 20;
    private static final double IMAGE_WIDTH = 75.0;

    private final int x;
    private final int y;

    private int remainingCooldown = 0;
    private Bee attachedBee;

    private final Group graphics = new Group();

    /**
     * Constructor
     * @param paneWidth width
     * @param paneHeight height
     */
    public ConcreteFlower(double paneWidth, double paneHeight) {
        Random random = new Random();
        x = random.nextInt(1, (int)paneWidth);
        y = random.nextInt(1, (int)paneHeight);
    }

    /**
     * Determines whether a bee can land on the flower
     * @param bee the bee that wants to land
     * @return true if the bee may land
     */
    @Override
    public boolean requestLand(Bee bee) {
        if (attachedBee == null && remainingCooldown == 0) {
            attachedBee = bee;
            return true;
        }
        return false;
    }

    /**
     * Determines whether the currently attached bee can leave
     * @return true if the bee may leave
     */
    @Override
    public boolean requestLeave() {
        attachedBee = null;
        remainingCooldown = DEFAULT_COOLDOWN;
        return true;
    }

    /**
     * Builds and adds graphics for the flower into its internal Group
     * @throws IOException if an image file cannot be read
     */
    @Override
    public void addGraphics() throws IOException {
        ImageView flowerImage = new ImageView(new Image(
                Files.newInputStream(Paths.get("images/aster.png"))));
        flowerImage.setPreserveRatio(true);
        flowerImage.setFitWidth(IMAGE_WIDTH);
        graphics.getChildren().add(flowerImage);

        graphics.setLayoutX(x);
        graphics.setLayoutY(y);
    }

    /**
     * Returns all graphics for this flower
     * @return graphics group
     */
    @Override
    public Group getGraphics() {
        return graphics;
    }

    /**
     * Called each simulation tick to advance state
     */
    @Override
    public void step() {
        if (remainingCooldown > 0) {
            remainingCooldown--;
        }
    }

    /**
     * Gets the x position
     * @return x position
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Gets the y position
     * @return y position
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Gets the attached bee
     * @return attached bee
     */
    @Override
    public Bee getAttachedBee() {
        return attachedBee;
    }
}