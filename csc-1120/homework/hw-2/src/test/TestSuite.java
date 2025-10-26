/*
 * Course: CSC-1120
 * Homework 2 - JavaFX
 * Calculator 3000
 * Name: Alexander Horton
 */
package test;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import hortona.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.List;

/**
 * Tests for the simple calculator program
 */
public class TestSuite extends ApplicationTest {
    private final Main main = new Main();
    private Scene scene;

    @Start
    @Override
    public void start(Stage stage) {
        main.start(stage);
        scene = stage.getScene();
    }

    @Test
    @DisplayName("GUI Tests")
    @Order(1)
    void guiTests() {
        List<Node> nodes = scene.getRoot().getChildrenUnmodifiable();
        Pane pane = (Pane) nodes.getFirst();
        List<Node> controls = pane.getChildren();
        // Verify the controls exist
        Assertions.assertInstanceOf(TextField.class, controls.getFirst(),
                "First FX control should be a TextField");
        Assertions.assertInstanceOf(TextField.class, controls.get(2),
                "Third FX control should be a TextField");
        Assertions.assertInstanceOf(Button.class, controls.get(1),
                "Second FX control should be a Button");
        Assertions.assertInstanceOf(Button.class, controls.get(3),
                "Fourth FX control should be a Button");
        FxAssert.verifyThat((Button) controls.get(1), LabeledMatchers.hasText("+"),
                sb -> sb.append("Expected the operator button to have as its text '+' but was ")
                        .append(((Button) controls.get(1)).getText()));
        FxAssert.verifyThat((Button) controls.get(3), LabeledMatchers.hasText("="),
                sb -> sb.append("Expected the equals button to have as its text '=' but was ")
                        .append(((Button) controls.get(3)).getText()));
        Assertions.assertInstanceOf(TextField.class, nodes.get(1),
                "Output control should be a TextField");
        Color color = (Color) ((TextField) nodes.get(1))
                .getBackground().getFills().getFirst().getFill();
        Assertions.assertEquals(Color.LIGHTGRAY, color,
                "Results TextField background should be LIGHTGRAY(0xd3d3d3ff) but is "
                        + color);
    }

    @Test
    @DisplayName("Testing Functionality")
    @Order(2)
    void functionality() {
        TextField operand1 = (TextField) ((Pane) scene.getRoot().getChildrenUnmodifiable()
                .getFirst()).getChildren().getFirst();
        TextField operand2 = (TextField) ((Pane) scene.getRoot().getChildrenUnmodifiable()
                .getFirst()).getChildren().get(2);
        Button equals = (Button) ((Pane) scene.getRoot().getChildrenUnmodifiable().getFirst())
                .getChildren().get(3);
        TextField result = (TextField) (scene.getRoot().getChildrenUnmodifiable().get(1));
        FxRobot robot = new FxRobot();
        robot.clickOn(equals);
        Assertions.assertTrue(result.getText().isEmpty(),
                "Results field should be empty if both operands are not full");
        robot.clickOn(operand1);
        robot.write("2");
        robot.clickOn(equals);
        Assertions.assertTrue(result.getText().isEmpty(),
                "Results field should be empty if both operands are not full");
        robot.clickOn(operand2);
        robot.write("1");
        robot.clickOn(equals);
        Assertions.assertEquals("3", result.getText(),
                "Result of 2 + 1 should be 3 but is " + result.getText());
        operand2.clear();
        robot.clickOn(operand2);
        robot.write("-3");
        robot.clickOn(equals);
        Assertions.assertEquals("-1", result.getText(),
                "Result of 2 + -3 should be -1 but is " + result.getText());
        Color color = (Color) result.getBackground().getFills().getFirst().getFill();
        Assertions.assertEquals(Color.RED, color,
                "Color of the results field should be RED when a negative value is present, but is "
                        + color);
        operand2.clear();
        robot.clickOn(operand2);
        robot.write("2");
        robot.clickOn(equals);
        Assertions.assertEquals("4", result.getText(),
                "Result of 2 + 2 should be 4 but is " + result.getText());
        color = (Color) result.getBackground().getFills().getFirst().getFill();
        Assertions.assertEquals(Color.LIGHTGRAY, color);
        operand1.clear();
        robot.clickOn(operand1);
        robot.write("3");
        robot.type(KeyCode.ENTER);
        Assertions.assertEquals("5", result.getText());
        operand2.clear();
        robot.clickOn(operand2);
        robot.write("1");
        robot.type(KeyCode.ENTER);
        Assertions.assertEquals("4", result.getText());
    }
}
