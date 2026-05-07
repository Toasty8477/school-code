/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Noah Dinan
 */

package finalproject;

import java.util.LinkedList;

import javafx.scene.transform.Rotate;

/**
 * Advance the traincar along the track
 */
public class Advance {
    private Traincar traincar;
    private LinkedList<PathNode> path;

    /**
     * Create a new Advance
     * 
     * @param traincar
     */
    public Advance(Traincar traincar) {
        this.traincar = traincar;
        this.path = traincar.getPath();
    }

    /**
     * execute the Advance action
     */
    public void execute() {
        traincar.getNode().getTransforms().clear();

        traincar.incrementPointer();
        traincar.setPosition(path.get(traincar.getPointer()).getX(),
                path.get(traincar.getPointer()).getY());

        double prev = Math.toDegrees(path.get(traincar.getPointer() - 1).getAngle());
        double current = Math.toDegrees(path.get(traincar.getPointer()).getAngle());

        double angle = (prev + current) / 2;
        double[] position = traincar.getPosition();
        traincar.getNode().getTransforms().add(new Rotate(angle, position[0], position[1]));
    }
}
