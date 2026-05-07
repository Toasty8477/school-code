/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Noah Dinan
 */

package finalproject;

import javafx.scene.image.ImageView;
import java.util.LinkedList;

import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * a traincar
 */
public class Traincar {
    private ImageView node;
    private Image image;
    private final String graphicPath = getClass().getResource("traincar.png").toString();
    private LinkedList<PathNode> path;

    private int pointer;
    private final double width = 15;
    private final double height = 5;
    private Advance advance;

    /**
     * create a new traincar
     * 
     * @param pointer
     * @param path
     */
    public Traincar(int pointer, LinkedList<PathNode> path) {
        this.image = new Image(graphicPath, width, height, false, false);
        this.node = new ImageView(image);
        this.path = new LinkedList<>(path);
        this.advance = new Advance(this);

        this.pointer = pointer;

        setPosition(path.get(pointer).getX(), path.get(pointer).getY());
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
        if (pointer < path.size() - 1) {
            advance.execute();
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
        return path;
    }

}
