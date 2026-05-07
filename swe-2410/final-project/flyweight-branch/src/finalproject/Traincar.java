/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Noah Dinan
 */

package finalproject;

import javafx.scene.image.ImageView;
import java.util.LinkedList;

import javafx.scene.Node;

/**
 * a traincar
 */
public class Traincar {
    private Flyweight flyweight;

    private int pointer;
    private ImageView node;
    private final double width = 15;
    private final double height = 5;

    /**
     * create a new traincar
     * 
     * @param pointer
     * @param factory
     */
    public Traincar(int pointer, FlyweightFactory factory) {
        this.pointer = pointer;
        this.flyweight = factory.getFlyweight("train");
        this.node = new ImageView(flyweight.getImage());

        setPosition(getPath().get(pointer).getX(), getPath().get(pointer).getY());
    }

    /**
     * get the JavaFX Node
     * 
     * @return node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Advance this Traincar to the next node in the path
     */
    public void advance() {
        if (pointer < getPath().size() - 1) {
            flyweight.advance(this);
        }
    }

    /**
     * set this Traincar's position
     * 
     * @param x
     * @param y
     */
    public void setPosition(double x, double y) {
        node.setX(x - (width / 2));
        node.setY(y - (height / 2));
    }

    /**
     * get this Traincar's position
     * 
     * @return [x, y]
     */
    public double[] getPosition() {
        return new double[] {
                node.getX() + (width / 2),
                node.getY() + (height / 2),
        };
    }

    /**
     * increase pointer by one
     */
    public void incrementPointer() {
        this.pointer++;
    }

    /**
     * get the integer pointer to this Traincar's current node along the path
     * 
     * @return pointer
     */
    public int getPointer() {
        return pointer;
    }

    /**
     * get the path
     * 
     * @return path
     */
    public LinkedList<PathNode> getPath() {
        return flyweight.getPath();
    }

}
