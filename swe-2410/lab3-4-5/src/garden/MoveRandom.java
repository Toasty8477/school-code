/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Alex Horton
 * Created: 2/3/2025
 */
package garden;

import java.util.List;
import java.util.Random;

class MoveRandom implements MoveBehavior {

    private Flower chosenFlower = null;
    private Random rand = new Random();

    @Override
    public void move(Simulation simulation, Bee bee) {
        final int ten = 10;

        if (chosenFlower == null) {
            List<Flower> flowers = simulation.getFlowers();
            chosenFlower = flowers.get(rand.nextInt(0, flowers.size()));
        }

        
        int flowerX = chosenFlower.getX();
        int flowerY = chosenFlower.getY();
        int beeX = bee.getX();
        int beeY = bee.getY();

        if (Math.abs(flowerX - beeX) <= 1 && Math.abs(flowerY - beeY) <= 1
                && bee.getAttachedFlower() == null) {
            if (chosenFlower.requestLand(bee)) {
                bee.setX(flowerX);
                bee.setY(flowerY);
                bee.setAttachedFlower(chosenFlower);
            }
        } else if (bee.getAttachedFlower() != null) {
            if (chosenFlower.requestLeave()) {
                chosenFlower = null;
                bee.setAttachedFlower(null);
            }
        } else {
            int xDistanceToMove;
            int yDistanceToMove;
            double distanceBetween;
            distanceBetween = Math.sqrt(Math.pow(flowerX - beeX, 2) + Math.pow(flowerY - beeY, 2));
            xDistanceToMove = (int)((flowerX - beeX) / Math.max(distanceBetween / ten, 1));
            yDistanceToMove = (int)((flowerY - beeY) / Math.max(distanceBetween / ten, 1));
            bee.setX(bee.getX() + xDistanceToMove);
            bee.setY(bee.getY() + yDistanceToMove);
        }
    }    
}