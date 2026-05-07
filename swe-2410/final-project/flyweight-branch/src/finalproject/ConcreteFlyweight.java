/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Horton, Dinan
 */

package finalproject;

import java.util.LinkedList;

import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * very heavy
 */
public class ConcreteFlyweight implements Flyweight {
    private LinkedList<PathNode> path;
    private Image image;
    private final double width = 15;
    private final double height = 5;
    private final String graphicPath = getClass().getResource("traincar.png").toString();

    /**
     * Create a new concrete flyweight
     * 
     * @param path
     */
    public ConcreteFlyweight(LinkedList<PathNode> path) {
        this.image = new Image(graphicPath, width, height, false, false);
        this.path = new LinkedList<>(path);
    }

    @Override
    public void advance(Traincar t) {
        t.getNode().getTransforms().clear();

        t.incrementPointer();
        t.setPosition(path.get(t.getPointer()).getX(),
                path.get(t.getPointer()).getY());

        double prev = Math.toDegrees(path.get(t.getPointer() - 1).getAngle());
        double current = Math.toDegrees(path.get(t.getPointer()).getAngle());

        double angle = (prev + current) / 2;
        double[] position = t.getPosition();
        t.getNode().getTransforms().add(new Rotate(angle, position[0], position[1]));
    }

    @Override
    public LinkedList<PathNode> getPath() {
        return path;
    }

    @Override
    public Image getImage() {
        return image;
    }
}
