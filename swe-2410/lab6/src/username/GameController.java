/*
 * Course: SWE2410 - 111, 121
 * Spring 2026
 * Lecture Code
 * Name: Adela Velez
 * Created: 3/3/2026
 */

package username;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX Controller class for the game - generally,
 * JavaFX elements (other than Image) should be here
 */
public class GameController implements Initializable {
    /**
     * Time between calls to step() (ms)
     */
    private static final double MILLISECONDS_PER_STEP = 1000.0 / 30;

    private Timeline coconutTimeline;

    @FXML
    private Pane gamePane;
    @FXML
    private Label laserLabel;
    @FXML
    private Label beachLabel;
    @FXML
    private Label gameOverLabel;

    private GameManager theGame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scoreboard scoreboard = new Scoreboard(laserLabel, beachLabel);
        theGame = new GameManager(this, gamePane, scoreboard);
        gamePane.setFocusTraversable(true);

        //Sets up the timeline
        coconutTimeline = new Timeline();
        //Time between update steps
        Duration duration = Duration.millis(MILLISECONDS_PER_STEP);
        //At the end of each step
        EventHandler<ActionEvent> onStepEnd = (ActionEvent event) -> {
            theGame.step();
            if(theGame.isGameOver()){
                coconutTimeline.pause();
            }
        };
        KeyFrame keyFrame = new KeyFrame(duration, onStepEnd);
        coconutTimeline.getKeyFrames().add(keyFrame);
        coconutTimeline.setCycleCount(Timeline.INDEFINITE);
        coconutTimeline.play();
    }

    @FXML
    private void restartGame(){
        theGame.restart();
        coconutTimeline.play();
        gameOverLabel.setVisible(false);
    }

    @FXML
    private void pauseGame(){
        if (coconutTimeline.getStatus() == Animation.Status.PAUSED) {
            coconutTimeline.play();
        } else {
            coconutTimeline.pause();
        }
    }

    /**
     * Sets the game over label to be visible.
     */
    public void setGameOverLabel() {
        gameOverLabel.setVisible(true);
    }

    /**
     * Takes the left, right, and up arrow key presses and
     * forward them to the GameManager. If the down or space
     * bar are hit, restarts or pauses the game.
     * @param keyEvent Event the represents the key pressed.
     */
    public void onKeyPressed(KeyEvent keyEvent) {
        final int crabMovement = 10;
        if (keyEvent.getCode() == KeyCode.DOWN) {
            restartGame();
        } else if(!theGame.isGameOver()){
            if (keyEvent.getCode() == KeyCode.SPACE) {
                pauseGame();
            } else if(coconutTimeline.getStatus() != Animation.Status.PAUSED) {
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    theGame.tryMoveCrab(crabMovement);
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    theGame.tryMoveCrab(-crabMovement);
                } else if (keyEvent.getCode() == KeyCode.UP) {
                    //pew pew
                    theGame.tryFireLaser();
                }
            }
        }
    }
}
