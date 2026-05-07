/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Dillon Fayas
 * Created: 2/3/2025
 */
package garden;

import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulation class; handles all objects in the simulations
 */
public class Simulation {
    private final int defaultBeeCount = 5;
    private final int defaultFlowerCount = 10;

    private ArrayList<Bee> bees;
    private ArrayList<Flower> flowers;

    private Pane gardenPane;

    private final int boundary = 75;
    private double spawnBoundsX;
    private double spawnBoundsY;

    /**
     * Default simulation constructor
     * @param gardenPane garden pane
     * @throws IOException throws if the bee image cannot be found
     */
    public Simulation(Pane gardenPane) throws IOException {
        bees = new ArrayList<>();
        flowers = new ArrayList<>();
        this.gardenPane = gardenPane;
        spawnBoundsX = gardenPane.getPrefWidth() - boundary;
        spawnBoundsY = gardenPane.getPrefHeight() - boundary;
        for (int i = 0; i < defaultFlowerCount; i++) {
            addFlower();
        }
        for (int i = 0; i < defaultBeeCount; i++) {
            addBee();
        }
    }

    /**
     * Simulation constructor with non-default values
     * @param gardenPane garden pane
     * @param numBees number of bees the simulation starts with
     * @param numFlowers number of flowers the simulation starts with
     * @throws IOException throws if the bee image cannot be found
     */
    public Simulation(Pane gardenPane, int numBees, int numFlowers) throws IOException {
        this.gardenPane = gardenPane;
        for (int i = 0; i < numFlowers; i++) {
            addFlower();
        }
        for (int i = 0; i < numBees; i++) {
            addBee();
        }
    }

    /**
     * Adds a bee to the simulation
     * @return success
     * @throws IOException throws if the bee image cannot be found
     */
    public Bee addBee() throws IOException {
        int randomNum = (int)(Math.random()*4)+1;

        Bee newBee = new ConcreteBee(this, spawnBoundsX, spawnBoundsY);
        if (randomNum == 1) {
            newBee = new FancyBee(newBee, this);
        } else if (randomNum == 2) {
            newBee = new DrunkenBee(newBee);
        } else if (randomNum == 3) {
            newBee = new DrunkenBee(newBee);
            newBee = new FancyBee(newBee, this);
        }
        gardenPane.getChildren().add(newBee.getGraphics());
        bees.add(newBee);
        return newBee;
    }

    /**
     * Removes the last bee from the simulation
     * @return the bee that was removed
     */
    public Bee removeBee() {
        if (!bees.isEmpty()) {
            Bee removedBee = bees.removeLast();
            gardenPane.getChildren().remove(removedBee.getGraphics());
            return removedBee;
        }
        return null;
    }

    /**
     * Removes a specific bee from the simulation
     * @param bee the bee to remove
     * @return success
     */
    public boolean removeBee(Bee bee) {
        gardenPane.getChildren().remove(bee.getGraphics());
        return bees.remove(bee);
    }

    /**
     * Adds a flower to the simulation
     * @return success
     * @throws IOException if files fail
     */
    public Flower addFlower() throws IOException {
        int randomNum = (int)(Math.random()*4)+1;

        Flower newFlower = new ConcreteFlower(spawnBoundsX, spawnBoundsY);
        if (randomNum == 1) {
            newFlower = new FlowerDecoratorA(newFlower);
        } else if (randomNum == 2) {
            newFlower = new FlowerDecoratorB(newFlower);
        } else if (randomNum == 3) {
            newFlower = new FlowerDecoratorA(newFlower);
            newFlower = new FlowerDecoratorB(newFlower);
        }
        newFlower.addGraphics();
        gardenPane.getChildren().add(newFlower.getGraphics());
        flowers.add(newFlower);
        return newFlower;
    }

    /**
     * Removes a flower from the simulation
     * @return success
     */
    public Flower removeFlower() {
        if (!flowers.isEmpty()) {
            Flower removedFlower = flowers.removeLast();
            gardenPane.getChildren().remove(removedFlower.getGraphics());
            return removedFlower;
        }
        return null;
    }

    /**
     * Called each tick; steps the simulation along
     */
    public void step() {
        for (int i = bees.size() - 1; i >= 0; i--) {
            bees.get(i).step();
        }
        for (Flower flower : flowers) {
            flower.step();
        }

        int i = 0;
        while (i < bees.size()) {
            if (bees.get(i).getEnergy() <= 0) {
                removeBee(bees.get(i));
            } else {
                i++;
            }
        }
    }

    public List<Bee> getBees() {
        return bees;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }
}