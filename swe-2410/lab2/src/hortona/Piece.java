/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 2 - Strategic Checkers
 * Name: Alex Horton
 * Created: 1/27/2025
 */

package hortona;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Represents a movable piece on the board.
 */
public class Piece {
    /**
     * ENUMS for the type (color) of check pieces
     */
    public enum Type {
        /**
         * Red checker pieces
         */
        RED,
        /**
         * Black checker pieces
         */
        BLACK
    }
    private final GameController controller;
    private final Type type;

    /**
     * The position of the piece on the board, where (0,0) is the top left corner
     * position, (BoardController.BOARD_WIDTH-1,0) is the top right corner
     * and (BOARD_WIDTH-1,BOARD_WIDTH-1) is the bottom right corner.
     */
    private int x;
    private int y;

    private final Ellipse crown;

    private final Ellipse ellipse;

    private MoveBehavior moveBehavior;

    // Y position of the top board square
    private final int boardTop = 0;
    // Y position of the bottom board square
    private final int boardBottom = 5;
    // How much the crown is offset from the main checker
    private final int crownOffeset = 10;

    /**
     * Creates a checker piece with the option of making it a king
     * @param type Enum for the piece type (RED or BLACK)
     * @param x Column location of the piece starting from top left corner
     * @param y Row location of the piece starting from top left corner
     * @param controller Controller that manages the key presses and list of squares
     * @param moveBehavior The movement behavior this piece should use
     */
    public Piece(Type type, int x, int y, GameController controller, MoveBehavior moveBehavior) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.controller = controller;
        ellipse = createEllipse();
        crown = createEllipse();
        crown.setVisible(moveBehavior instanceof KingBehavior);
        setActive(false);
        reposition();
    }

    public Type getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameController getController() {
        return controller;
    }

    /**
     * Returns a String representation of the piece
     * @return String with the piece's (x,y) position
     */
    public String toString() {
        return "Piece at "+x+", "+y;
    }

    /**
     * Sets the outline of the checker pieces to indicate
     * that it is currently selected
     * @param isActive Boolean whether this piece is selected
     */
    public void setActive(boolean isActive) {
        if(isActive) {
            ellipse.setStrokeWidth(3);
            crown.setStrokeWidth(3);
        } else {
            ellipse.setStrokeWidth(1);
            crown.setStrokeWidth(1);
        }
    }

    /**
     * Try to move a piece to a given square, including capturing a piece
     * if appropriate. If the move is not legal, reports the problem
     * to the user.
     * @param square Square to try moving to.
     */
    public void tryMove(Square square) {
        if(square.getPiece() != null) {
            controller.getMessage().setText("That location is already occupied!\n" +
                    "Please select a different location or piece.");
        } else {
            if (isValidOrdinaryMove(square)) {
                moveTo(square);
                if (checkKing()) {
                    makeKing();
                }
            } else if (isValidCapture(square)) {
                captureMoveTo(square);
            } else {
                controller.getMessage().setText("The piece can neither move nor capture " +
                        "to that position.\n Please try a different square.");
            }
        }
    }

    /**
     * Translates the (x,y) position of the piece which are its column and row into
     * the window coordinates to properly display it in the middle of its respective square.
     */
    private void reposition() {
        ellipse.setLayoutX(x* GameController.SQUARE_SIZE + GameController.SQUARE_SIZE/2.0);
        ellipse.setLayoutY(y* GameController.SQUARE_SIZE + GameController.SQUARE_SIZE/2.0);
        crown.setLayoutX(x* GameController.SQUARE_SIZE + GameController.SQUARE_SIZE/2.0);
        crown.setLayoutY(y* GameController.SQUARE_SIZE +
                GameController.SQUARE_SIZE/2.0 - crownOffeset);
    }

    /**
     * Creates the shape for the ellipse, but also sets the onMouseClicked to a method
     * that will try to set this Piece as the active piece, and adds the shape to the
     * table so that it will be displayed.
     * @return Created Ellipse
     */
    private Ellipse createEllipse() {
        final double ellipseRadiusX = 25.0;
        final double ellipseRadiusY = 12.0;
        final Ellipse ellipse;
        ellipse = new Ellipse();
        ellipse.setRadiusX(ellipseRadiusX);
        ellipse.setRadiusY(ellipseRadiusY);
        ellipse.setStroke(Color.WHITE);
        if(this.type == Type.RED) {
            ellipse.setFill(Color.RED);
        } else if(this.type == Type.BLACK) {
            ellipse.setFill(Color.BLACK);
        } else {
            throw new IllegalArgumentException("Unknown type:"+type);
        }
        ellipse.setOnMouseClicked((MouseEvent event) -> {
            controller.trySetActive(this);
        });
        controller.getTable().getChildren().add(ellipse);
        return ellipse;
    }

    /**
     * Actually place the piece on the given square and force a redisplay.
     */
    private void placeOnSquare(Square square) {
        x = square.getX();
        y = square.getY();
        square.tryPlacePiece(this);
        reposition();
    }

    /**
     * Make a game-level move by removing the piece from the old position,
     * place it on the new position, redrawing the piece, switching the
     * turn to the next player, and setting this piece to not active.
     * Precondition:
     * The move must be valid -- a valid, unoccupied square must be provided.
     * @param square the position to which this piece will be moved.
     */
    private void moveTo(Square square) {
        controller.getSquare(x, y).removePiece();
        placeOnSquare(square);
        controller.switchTurns();
        setActive(false);
    }

    /**
     * Removes this piece from the board.
     */
    private void removeSelf() {
        controller.getSquare(x, y).removePiece();
        controller.getTable().getChildren().remove(ellipse);
        controller.getTable().getChildren().remove(crown);
    }

    /**
     * Make a game-level move when that move captures another piece.
     * This identifies the piece to be captured, removes that piece from
     * the board, and then moves the current piece to the new position.
     * Preconditions:
     * The move must be valid -- the place moved to must exist and there must be a piece to capture.
     *
     * @param square A square to which this piece is able to move and capture at the same time.
     * @throws  IllegalArgumentException If no capture is made by moving to the square.
     */
    private void captureMoveTo(Square square) {
        Piece captured = getCapturedPiece(square);
        if(captured == null) {
            throw new IllegalArgumentException("Cannot capture by moving to "+square);
        } else if (captured.type == this.type){
            controller.getMessage().setText("Cannot capture your own piece.");
        } else {
            captured.removeSelf();
            moveTo(square);
        }
    }

    /**
     * Check if the current piece can move to a new position without
     * capturing another piece.
     *
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square 
     */
    private boolean isValidOrdinaryMove(Square square) {
        if (moveBehavior != null) {
            return moveBehavior.isValidOrdinaryMove(square, this);
        } else {
            throw new IllegalStateException("This piece's move behavior is undefined");
        }
    }

    /**
     * Find the piece that would be captured by moving this piece to a given square.
     * The piece is not actually captured when calling this method.
     * It is simply identified by calling this method.
     *
     * @param square The square to which a move will be mode
     * @return null if the move cannot be made.
     *      Otherwise, return the piece that would be removed by moving to that square.
     */
    private Piece getCapturedPiece(Square square) {
        if (moveBehavior != null) {
            return moveBehavior.getCapturedPiece(square, this);
        } else {
            throw new IllegalStateException("This piece's behavior is undefined");
        }
    }

    /**
     * Check if the current piece can capture another piece when moving
     * to the given target square.
     *
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square and capture another
     *    piece at the same time.
     */
    private boolean isValidCapture(Square square) {
        return getCapturedPiece(square) != null;
    }

    private boolean checkKing() {
        if (type.equals(Type.BLACK)) {
            return y == boardTop;
        } else if (type.equals(Type.RED)) {
            return y == boardBottom;
        } else {
            throw new IllegalStateException("This piece has an unknown type:"+type);
        }
    }

    private void makeKing() {
        moveBehavior = new KingBehavior();
        crown.setVisible(true);
    }

}

