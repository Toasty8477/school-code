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
 * Flower Decorator B Super Flower
 */
public class FlowerDecoratorB extends FlowerDecorator {

    private static final int ENERGY_RESTORED_PER_TICK = 100;
    private static final double IMAGE_WIDTH = 75.0;

    /**
     * Constructor
     * @param innerComponent inner component
     */
    public FlowerDecoratorB(Flower innerComponent) {
        super(innerComponent);
    }

    /**
     * Determines whether the currently attached bee can leave
     * @return true if the bee may leave
     */
    @Override
    public boolean requestLeave() {
        return super.requestLeave();
    }

    /**
     * Adds custom graphics
     * @throws IOException if file fails
     */
    @Override
    public void addGraphics() throws IOException {
        super.addGraphics();

        ImageView glowImage = new ImageView(new Image(
                Files.newInputStream(Paths.get("images/sparkles.png"))));
        glowImage.setPreserveRatio(true);
        glowImage.setFitWidth(IMAGE_WIDTH);

        getGraphics().getChildren().add(glowImage);
    }

    /**
     * Restores energy to the attached bee each tick, then steps
     */
    @Override
    public void step() {
        Bee bee = getAttachedBee();
        if (bee != null) {
            bee.replenishEnergy(ENERGY_RESTORED_PER_TICK);
        }
        super.step();
    }
}