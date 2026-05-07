/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 5 - Decorate a Garden
 * Name: Dillon Fayas
 * Created: 2/3/2025
 */
package garden;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Flower Decorator A Draining Flower
 */
public class FlowerDecoratorA extends FlowerDecorator {

    private static final int ENERGY_TO_LEAVE = 30;
    private static final int ENERGY_DRAINED_PER_TICK = -10;
    private static final double IMAGE_WIDTH = 75.0;

    private int totalDrained = 0;

    /**
     * Constructor
     * @param innerComponent inner component
     */
    public FlowerDecoratorA(Flower innerComponent) {
        super(innerComponent);
    }

    /**
     * Determines whether the currently attached bee can leave
     * @return true if the bee may leave
     */
    @Override
    public boolean requestLeave() {
        if (totalDrained >= ENERGY_TO_LEAVE) {
            return super.requestLeave();
        }
        return false;
    }

    /**
     * Adds custom graphics
     * @throws IOException if file fails
     */
    @Override
    public void addGraphics() throws IOException {
        super.addGraphics();

        ImageView thornsImage = new ImageView(new Image(
                Files.newInputStream(Paths.get("images/thorns.png"))));
        thornsImage.setPreserveRatio(true);
        thornsImage.setFitWidth(IMAGE_WIDTH);

        getGraphics().getChildren().add(thornsImage);
    }

    /**
     * Drains energy from the attached bee each tick, then steps
     */
    @Override
    public void step() {
        Bee bee = getAttachedBee();
        if (bee != null) {
            bee.replenishEnergy(ENERGY_DRAINED_PER_TICK);
            totalDrained += Math.abs(ENERGY_DRAINED_PER_TICK);
        } else {
            totalDrained = 0;
        }
        super.step();
    }
}