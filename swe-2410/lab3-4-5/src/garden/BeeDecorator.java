/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Alex Horton
 * Created: 2/3/2025
 */

package garden;

/**
 * Abstract class that defines a bee decorator
 */
public abstract class BeeDecorator implements Bee {
    protected Bee bee;

    /**
     * Decorator super consructor to set bee attribute
     * @param bee The bee that is to be decorated
     */
    public BeeDecorator(Bee bee) {
        this.bee = bee;
    }

    /**
     * Steps the bee
     */
    public abstract void step();
}
