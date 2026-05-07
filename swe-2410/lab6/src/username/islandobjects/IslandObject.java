/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 6 - Observing Coconuts
 * Names: Alex Horton and Kaden Christie
 * Created: 3/3/2025
 */

package username.islandobjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import username.GameManager;

/**
 * Represents an object in the game. Each island object has a
 * location and can determine if it hits another island object.
 */
public abstract class IslandObject {
    protected final int width;
    protected final int height;
    protected final GameManager containingGame;
    protected int x;
    protected int y;
    private ImageView imageView = null;

    /**
     * Creates an island object
     * @param game GameManger this object lives in
     * @param x X location of the object
     * @param y Y location of the object
     * @param width Width of the object
     * @param height Height of the object
     * @param image Image used to represent the object
     */
    public IslandObject(GameManager game, int x, int y, int width, int height, Image image) {
        containingGame = game;
        imageView = new ImageView(image);
        this.width = width;
        this.height = height;
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height);
        this.x = x;
        this.y = y;
        display();
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }

    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Updates the x and y location of this object.
     */
    public void display() {
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
    }
    /**
     * Method that is called each time step of the game/simulation.
     * Most subclass will not have to override this, unless they
     * perform some action, like falling a set amount each simulation tick.
     */
    public void step(){
        //Do nothing
    }
    /**
     * Determines whether this object is touching another object.
     * Two objects are considered touching if this object can hit
     * the other object and if both of the following conditions are met:
     * 1) Their hittable heights are within n pixels of each other.
     * 2) Their horizontal edges overlap. Two objects overlap horizontally
     *    if the horizontal interval [leftX, rightX] of one object
     *    intersects with the horizontal interval of the other object.
     *    This occurs when the left edge of each object lies to the left
     *    of the other object's right edge.
     * @param other the object to test for collision with
     * @return true if this object can hit the other and both the vertical proximity
     *         and horizontal overlap conditions are met; false otherwise
     */
    public boolean isTouching(IslandObject other) {
        final int threshold = 5;
        boolean hittable = Math.abs(this.hittableHeight() - other.hittableHeight()) <= threshold;
        boolean overlapping = other.x + other.imageView.getFitWidth() >= this.x &&
                other.x <= this.x + this.imageView.getFitWidth();
        return hittable && overlapping;
    }

    /**
     * Island objects can be categorized as either falling,
     * on the ground, or neither
     * @return True if this object is a ground object
     */
    public abstract boolean isGroundObject();

    /**
     * Island objects can be categorized as either falling,
     * on the ground, or neither
     * @return True if this object is a falling object
     */
    public abstract boolean isFalling();

    /**
     * Island objects can be categorized as not moving
     * @return True if this object does not move
     */
    public abstract boolean isStationary();

    /**
     * Returns true if this object can hit the passed-in
     * other objects. Some classes can only hit falling,
     * ground, or neither type of object. Also, hits are
     * one directional.
     * @param other The object we want to see if we hit.
     * @return True if we can hit the passed-in object
     */
    public abstract boolean canHit(IslandObject other);

    /**
     * Performs any actions that are needed when this object
     * is removed from the game, such as detaching from any
     * subjects it is subscribed to if this subclass is an observer.
     */
    public void onDestroy(){
        //do nothing
    }

    /**
     * The hittable height for this object.
     * Falling objects and any stationary non-ground objects
     * hittable height is at the bottom of their ImageView.
     * Everything else's hittable height is the top of their ImageView.
     * You can get the height of an objects ImageView by calling getFitHeight().
     * @return Hittable height for this object
     */
    protected int hittableHeight() {
        if (isFalling() || isStationary() && !isGroundObject()) {
            return y + (int)imageView.getFitHeight();
        } else {
            return y;
        }
    }

    @Override
    public String toString() {
        String[] tokens = getClass().getName().split("\\.");
        return tokens[tokens.length - 1] + "("+x+","+y+")";
    }
}
