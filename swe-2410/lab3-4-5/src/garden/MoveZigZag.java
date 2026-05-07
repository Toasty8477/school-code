/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Alex Horton
 * Created: 2/3/2025
 */
package garden;

/**
 * Makes a bee move in a zig zag pattern
 */
public class MoveZigZag implements MoveBehavior {

    private final int leftRightDistance = 10;
    private final int upDownDistance = 50;
    private final int flowerDistance = 30;

    private int paneWidth;
    private int paneHeight;
    private boolean goingRight;
    private boolean goingDown;

    MoveZigZag(double paneWidth, double paneHeight) {
        this.paneWidth = (int)paneWidth;
        this.paneHeight = (int)paneHeight;
        this.goingRight = true;
        this.goingDown = true;
    }

    @Override
    public void move(Simulation simulation, Bee bee) {
        int beeX = bee.getX();
        int beeY = bee.getY();

        beeX += goingRight ? leftRightDistance : 1 - leftRightDistance;
        beeX = Math.min(beeX, paneWidth);

        if (beeX == paneWidth || beeX <= 0) {
            beeY += goingDown ? upDownDistance : 1 - upDownDistance;
            beeY = Math.min(beeY, paneWidth);
            goingRight = !goingRight;

            if (beeY == paneHeight || beeY <= 0) {
                goingDown = !goingDown;
            }
        }

        for (Flower flower : simulation.getFlowers()) {
            int flowerX = flower.getX();
            int flowerY = flower.getY();
            if (Math.abs(flowerX - beeX) <= flowerDistance
                    && Math.abs(flowerY - beeY) <= flowerDistance
                    && bee.getAttachedFlower() == null) {
                if (flower.requestLand(bee)) {
                    beeX = flowerX;
                    beeY = flowerY;
                    bee.setAttachedFlower(flower);
                }
            }
        }
        if (bee.getAttachedFlower() != null) {
            if (bee.getAttachedFlower().requestLeave()) {
                bee.setAttachedFlower(null);
            }
        }

        bee.setX(beeX);
        bee.setY(beeY);
    }
    
}
