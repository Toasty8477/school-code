/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 6 - Observing Coconuts
 * Names: Alex Horton and Kaden Christie
 * Created: 3/3/2025
 */

package username;

import javafx.scene.layout.Pane;
import username.islandobjects.Beach;
import username.islandobjects.Coconut;
import username.islandobjects.Crab;
import username.islandobjects.IslandObject;
import username.islandobjects.LaserBeam;
import username.islandobjects.Sky;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This class manages the game, including tracking
 * all island objects and detecting when they hit
 */
public class GameManager implements Subject {
    //Utility objects
    private final Random random = new Random();

    //Lists of objects
    private final List<IslandObject> allObjects = new LinkedList<>();
    private final List<IslandObject> scheduledForRemoval = new LinkedList<>();
    private final List<Observer> observers = new ArrayList<>();

    //Important dimensions
    private int skyHeight;
    private int width;

    //Variables that control game flow
    private int gameTick = 0;
    private boolean gameOver = false;
    private int laserCooldown = 0;

    //JavaFX objects
    private final Pane gamePane;
    private final GameController controller;
    private final Scoreboard scoreboard;

    //Game objects
    private Crab theCrab;
    private Beach theBeach;
    private Sky theSky;

    /**
     * Creates a Game Manager that handles the logic and operations of the game.
     * @param controller Controller that has the key presses
     * @param gamePane Pane for displaying the game elements
     * @param scoreboard Scoreboard for keeping track of strikes
     */
    public GameManager(GameController controller, Pane gamePane, Scoreboard scoreboard) {
        this.gamePane = gamePane;
        this.controller = controller;
        this.scoreboard = scoreboard;
        initializeIslandObjects();
    }

    private void initializeIslandObjects(){
        if(this.gamePane != null){
            observers.add(scoreboard);
            this.skyHeight = (int) (gamePane.getPrefHeight() - Beach.BEACH_HEIGHT);
            this.width = (int) gamePane.getPrefWidth();

            //Make the sky
            this.theSky = new Sky(this, width);
            addObject(theSky);

            //Make the beach
            this.theBeach = new Beach(this, skyHeight, width);
            addObject(theBeach);

            //Make the crab
            this.theCrab = new Crab(this, skyHeight - Crab.HEIGHT, width);
            addObject(theCrab);
        }
    }
    public Pane getGamePane() {
        return gamePane;
    }
    public Crab getCrab() {
        return theCrab;
    }
    public boolean isGameOver(){
        return gameOver;
    }
    /**
     * Moves the crab by the passed-in amount
     * @param crabMovement Amount to move the crab
     */
    public void tryMoveCrab(int crabMovement) {
        theCrab.crawl(crabMovement);
    }
    /**
     * Does any operations relating to
     * having the game be over
     */
    public void setGameOver(){
        gameOver = true;
        controller.setGameOverLabel();
    }
    /**
     * Method that is called each step of the game. Performs various
     * operations such as stepping each object in the game, detecting
     * collisions, and removing items.
     */
    public void step() {
        gameTick++;

        tryDropCoconut();
        for (IslandObject o : allObjects) {
            o.step();
            o.display();
        }
        checkForCollisions();
        laserCooldown = Math.max(0, laserCooldown - 1);
        //Calls deleteObject on all the objects in the scheduledForRemoval list
        scheduledForRemoval.forEach(this::deleteObject);
        scheduledForRemoval.clear();
    }

    /**
     * Checks if any object hits any other object and performs the
     * appropriate actions in response.
     */
    private void checkForCollisions() {
        for (IslandObject thisObj : allObjects) {
            for (IslandObject otherObj : allObjects) {
                //This checks if this or other are scheduled for removal may not be necessary
                if (!scheduledForRemoval.contains(thisObj) &&
                        !scheduledForRemoval.contains(otherObj)) {
                    if (thisObj != otherObj && thisObj.canHit(otherObj)) {
                        if (thisObj.isTouching(otherObj)) {
                            notifyObservers(thisObj, otherObj);
                        }
                    }
                }
            }
        }
    }
    /**
     * Restarts the game by clearing the lists of objects,
     * re-initializing the island objects, resetting the
     * control variables, and resetting any ui elements
     */
    public void restart(){
        //clear the lists
        gamePane.getChildren().clear();
        allObjects.clear();
        scheduledForRemoval.clear();
        scoreboard.restart();

        //remake the starting island objects
        initializeIslandObjects();

        //reset control variables
        gameOver = false;
        gameTick = 0;
        laserCooldown = 0;
    }
    /**
     * Creates and drops a coconut at a set interval of gameTicks
     * by creating the coconut and then adding it to the game.
     * You may also need to do observer operations such
     * setting and/or attaching to a subject.
     */
    public void tryDropCoconut() {
        final int dropInterval = 50;

        if (gameTick % dropInterval == 0) {
            Coconut coconut = new Coconut(this,
                    random.nextInt(Coconut.WIDTH, (int)(gamePane.getPrefWidth()) - Coconut.WIDTH),
                    Coconut.HEIGHT);
            addObject(coconut);
            registerObserver(coconut);
        }
    }

    /**
     * Creates a laser is the laser cooldown is finished
     * and adds it to the game.
     * You may also need to do observer operations such
     * setting and/or attaching to a subject.
     */
    public void tryFireLaser() {
        final int maxCoolDown = 20;
        if (laserCooldown == 0) {
            int laserX = (int)(theCrab.getX());
            addObject(new LaserBeam(this, laserX, theCrab.getY()));
            laserCooldown = maxCoolDown;
        }
    }

    /**
     * Adds an object to the game.
     * @param object Object to add.
     */
    private void addObject(IslandObject object) {
        allObjects.add(object);
        gamePane.getChildren().add(object.getImageView());
    }
    /**
     * Helper method to remove an object from the game. Does the
     * reverse operations of addObject()
     */
    private void deleteObject(IslandObject object){
        object.onDestroy();
        allObjects.remove(object);
        gamePane.getChildren().remove(object.getImageView());
    }
    /**
     * We can not remove an object from a list while we are iterating
     * over that list. This helper methods allows us to schedule
     * an object for deletion, which can happen after we are done
     * iterating over the list.
     * @param islandObject The object to be removed
     */
    public void scheduleForDeletion(IslandObject islandObject) {
        scheduledForRemoval.add(islandObject);
    }

    /**
     * Adds an observer to the list of current observers.
     * @param observer the observer to add to the list.
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of current observers.
     * @param observer the observer to remove from the list.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Called by the subject when a collision occurs to trigger collision behavior.
     * @param obj1 the first object involved in a collision.
     * @param obj2 the second object involved in a collision.
     */
    @Override
    public void notifyObservers(IslandObject obj1, IslandObject obj2) {
        for (Observer o : observers) {
            o.update(obj1, obj2);
        }
    }
}
