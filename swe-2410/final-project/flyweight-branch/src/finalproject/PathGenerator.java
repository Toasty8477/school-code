/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Alex Horton
 */

package finalproject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Random;

/**
 * This class handles generating a path for the train to follow
 */
public class PathGenerator {

    private static Random rand = new Random();

    /**
     * Returns a path for our train to follow.
     * 
     * @param distanceBetween The distance between nodes.
     * @param maxNodes        The maximum number of nodes the path can have, used
     *                        for testing purposes.
     * @param width           The width of the area the path is being created in.
     * @param height          The height of the area the path is being created in.
     * @return A linked list of path points.
     */
    public static LinkedList<PathNode> generatePath(int distanceBetween, int maxNodes,
            double width, double height) {

        // Padding so nodes aren't too close to the edge
        final double padding = 25;
        final int maxAngle = 20;

        double x;
        double y;

        // Make the path
        LinkedList<PathNode> path = new LinkedList<>();

        // Generate random position on the screen
        x = rand.nextDouble(2 * padding, width - 2 * padding);
        y = rand.nextDouble(2 * padding, height - 2 * padding);

        // Generate anchor points for the path
        // anchorPoints = generateAnchorPoints(5, padding, width, height);

        // Add node to path
        // PathNode initialNode = new PathNode(anchorPoints[0][0], anchorPoints[0][1]);
        PathNode initialNode = new PathNode(x, y, 0);
        path.add(initialNode);


        while (path.getLast().getX() <= width - padding && path.getLast().getX() >= padding
                && path.size() < maxNodes) {
            // assign the last node in the path to a variable because we need it a lot
            PathNode lastNode = path.getLast();

            // Determine the angle of a vector
            double angle;
            if ((lastNode.getY() - padding) <= padding) {
                // If we are too close to the top of the screen only go down
                angle = rand.nextInt(maxAngle / 4, maxAngle);
            } else if (lastNode.getY() + padding >= height) {
                // If we are too close to the bottom of the screen only go up
                angle = rand.nextInt(-maxAngle, maxAngle / 4);
            } else {
                angle = rand.nextInt(-maxAngle, maxAngle);
            }

            angle = Math.toRadians(angle); // Put the angle in radians for trig calculations
            angle += lastNode.getAngle();

            // Get the x and y components of the vector we just determined the angle for and
            // use those to determine the next node's position
            x = (distanceBetween * Math.cos(angle)) + lastNode.getX();
            y = (distanceBetween * Math.sin(angle)) + lastNode.getY();

            x = BigDecimal.valueOf(x).setScale(2, RoundingMode.HALF_UP).doubleValue();
            y = BigDecimal.valueOf(y).setScale(2, RoundingMode.HALF_UP).doubleValue();

            // Add the node to the path
            path.add(new PathNode(x, y, angle));
        }

        return path;
    }
}
