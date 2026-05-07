/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 5 - Decorate a Garden
 * Name: Dillon Fayas
 * Created: 2/3/2025
 */
package garden;

import javafx.scene.Group;

import java.io.IOException;

/**
 * Abstract decorator for flower
 */
public abstract class FlowerDecorator implements Flower {

    private final Flower innerComponent;

    protected FlowerDecorator(Flower innerComponent) {
        this.innerComponent = innerComponent;
    }

    @Override
    public boolean requestLand(Bee bee) {
        return innerComponent.requestLand(bee);
    }

    @Override
    public boolean requestLeave() {
        return innerComponent.requestLeave();
    }

    @Override
    public void addGraphics() throws IOException {
        // Build the base (or previously decorated) graphics first,
        // then concrete decorators append their own visuals on top.
        innerComponent.addGraphics();
    }

    @Override
    public Group getGraphics() {
        // All decorators share the same Group that lives in the concrete Flower.
        return innerComponent.getGraphics();
    }

    @Override
    public void step() {
        innerComponent.step();
    }

    @Override
    public int getX() {
        return innerComponent.getX();
    }

    @Override
    public int getY() {
        return innerComponent.getY();
    }

    @Override
    public Bee getAttachedBee() {
        return innerComponent.getAttachedBee();
    }
}