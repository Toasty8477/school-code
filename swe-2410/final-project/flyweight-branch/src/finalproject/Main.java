/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Horton, Dinan
 */

package finalproject;

import java.util.LinkedList;
import java.util.Objects;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * creates threads for moving the train, displaying memory usage;
 * starts the JavaFX application
 */
public class Main extends Application {
    private static final int TRAIN_LENGTH = 1000;
    private static final int SLEEP_DURATION_MS = 20;
    private static final double PATH_NODE_WIDTH = 1;
    private static final int TWO_THOUSAND = 1024 * 1024;
    private static final int HALF_SECOND = 500;
    private Pane mainArea;
    private Train train;
    private Label memUseLabel;
    private LinkedList<PathNode> path;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setTitle("traaaaaaaaaaaaaain");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setScene(scene);
        primaryStage.show();

        mainArea = (Pane) root;
        memUseLabel = (Label) (mainArea.getChildren().getFirst());

        // keep generating paths until one is good
        boolean good = false;
        while (!good) {
            path = PathGenerator.generatePath(4, (int) Math.pow(TWO_THOUSAND, 4),
                    primaryStage.getWidth(), primaryStage.getHeight());
            try {
                train = new Train(TRAIN_LENGTH, path);
                System.out.println("Acceptable path generated!");
                good = true;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }

        // attach javafx graphics to mainArea
        for (PathNode n : path) {
            Circle node = new Circle(n.getX(), n.getY(), PATH_NODE_WIDTH);
            mainArea.getChildren().add(node);
        }
        for (Traincar car : train.getTraincars()) {
            mainArea.getChildren().add(car.getNode());
        }

        // memory monitor
        Thread resourceMonitorThread = new Thread(() -> {
            boolean couldBeFalse = true;
            try {
                while (couldBeFalse) {
                    long memUsed = (Runtime.getRuntime().totalMemory() -
                            Runtime.getRuntime().freeMemory())
                            / TWO_THOUSAND;
                    String labelText = "Memory Used: " + memUsed + "MB";
                    System.out.println(labelText);
                    Platform.runLater(() -> {
                        memUseLabel.setText(labelText);
                    });
                    Thread.sleep(HALF_SECOND);
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupt!!!!!!");
            }
        });
        resourceMonitorThread.setDaemon(true);
        resourceMonitorThread.start();

        Thread moveTrainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < path.size(); i++) {
                    try {
                        for (Traincar car : train.getTraincars()) {
                            Platform.runLater(() -> car.advance());
                        }
                        Thread.sleep(SLEEP_DURATION_MS);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupt!!!!!!");
                    }
                }
            }
        });
        moveTrainThread.setDaemon(true);

        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE ||
                    event.getCode() == KeyCode.Q) {
                System.exit(0);
            } else if (event.getCode() == KeyCode.S) {
                moveTrainThread.start();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
