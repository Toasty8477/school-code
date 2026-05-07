/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 3 - Design a Garden
 * Name: Adela Velez
 * Created: 1/2/2025
 */

package garden;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Controller that hands the mouse and key presses. Delegates the
 * stepping of the simulator to the garden.
 */
public class Controller {
    private Simulation simulation;

    @FXML
    private Pane gardenPane;
    @FXML
    private TextField beeCount;
    @FXML
    private TextField flowerCount;

    @FXML
    private void initialize() {
        try {
            simulation = new Simulation(gardenPane);

            updateBeeFlowerCounts();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        this.gardenPane.setFocusTraversable(true);

        updateBeeLocations();
        updateFlowerLocations();
    }

    @FXML
    private void subtractBee() {
        simulation.removeBee();
        updateBeeFlowerCounts();
        gardenPane.requestFocus();
    }

    @FXML
    private void addBee() throws IOException {
        simulation.addBee();
        updateBeeLocations();
        updateBeeFlowerCounts();
        gardenPane.requestFocus();
    }

    @FXML
    private void subtractFlower() {
        simulation.removeFlower();
        updateBeeFlowerCounts();
        gardenPane.requestFocus();
    }

    @FXML
    private void addFlower() throws IOException {
        simulation.addFlower();
        updateFlowerLocations();
        updateBeeFlowerCounts();
        gardenPane.requestFocus();
    }

    @FXML
    private void legendSelectionChanged() {
        gardenPane.requestFocus();
    }

    /**
     * Steps the garden simulation one tick forward.
     */
    private void step(){
        simulation.step();
        updateBeeLocations();
        updateBeeFlowerCounts();
    }

    @FXML
    private void onKeyPressed(KeyEvent keyEvent) {
        gardenPane.requestFocus();
        if(keyEvent.getCode() == KeyCode.RIGHT){
            step();
        }
    }

    private void updateBeeLocations() {
        final int borderWidth = 10;
        for (Bee bee : simulation.getBees()) {
            double x = bee.getX();
            double y = bee.getY();

            x = Math.min(Math.max(x, borderWidth), gardenPane.getPrefWidth() - borderWidth);
            y = Math.min(Math.max(y, borderWidth), gardenPane.getPrefHeight() - borderWidth);

            bee.getGraphics().setLayoutX(x);
            bee.getGraphics().setLayoutY(y);
        }
    }

    private void updateFlowerLocations() {
        final int borderWidth = 10;
        for (Flower flower : simulation.getFlowers()) {
            double x = flower.getX();
            double y = flower.getY();

            x = Math.min(Math.max(x, borderWidth), gardenPane.getPrefWidth() - borderWidth);
            y = Math.min(Math.max(y, borderWidth), gardenPane.getPrefHeight() - borderWidth);

            flower.getGraphics().setLayoutX(x);
            flower.getGraphics().setLayoutY(y);
        }
    }

    private void updateBeeFlowerCounts() {
        beeCount.setText(String.valueOf(simulation.getBees().size()));
        flowerCount.setText(String.valueOf(simulation.getFlowers().size()));
    }

    public double getGardenWidth() {
        return gardenPane.getPrefWidth();
    }

    public double getGardenHeight() {
        return gardenPane.getPrefHeight();
    }
}
