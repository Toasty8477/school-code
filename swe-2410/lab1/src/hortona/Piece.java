/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 1 - Checkers Lab No Patterns
 * Name: Adela Velez
 * Created: 1/2/2025
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

    private boolean king;
    private final Ellipse crown;

    private final Ellipse ellipse;

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
     * @param king Whether a piece is a king
     */
    public Piece(Type type, int x, int y, GameController controller, Boolean king) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.controller = controller;
        this.king = king;
        ellipse = createEllipse();
        crown = createEllipse();
        crown.setVisible(king);
        setActive(false);
        reposition();
    }

    /**
     * Creates a checker piece without the option of making it a King
     * @param type Enum for the piece type (RED or BLACK)
     * @param x Column location of the piece starting from top left corner
     * @param y Row location of the piece starting from top left corner
     * @param controller Controller that manages the key presses and list of squares
     */
    public Piece(Type type, int x, int y, GameController controller) {
        this(type, x, y, controller, false);
    }

    public Type getType() {
        return type;
    }

    public boolean getKing() {
        return king;
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
                if (checkKing(square.getPiece())) {
                    makeKing(square.getPiece());
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
        if (getKing()) {
            return square.getY() == y - 1 && Math.abs(square.getX() - x) == 1 ||
                    square.getY() == y + 1 && Math.abs(square.getX() - x) == 1;
        } else if(type.equals(Type.BLACK)) {
            return square.getY() == y - 1 && Math.abs(square.getX() - x) == 1;
        } else if(type.equals(Type.RED)){
            return square.getY() == y + 1 && Math.abs(square.getX() - x) == 1;
        } else {
            throw new IllegalStateException("This piece has an unknown type:"+type);
        }
    }

    /**
     * Assuming the given square represents a valid move, returns any piece
     * that would be captured during the move. If there is no piece, returns
     * null.
     * @param square Target square for the move
     * @return null the piece that would be captured or null if there is none
     */
    private Piece getMiddlePiece(Square square) {
        int middleX = (square.getX() + x) / 2;
        int middleY = (square.getY() + y) / 2;
        return controller.getSquare(middleX, middleY).getPiece();
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
        Piece ret;

        if (getKing()) {
            if (!(((square.getY() == y + 2 || square.getY() == y - 2)
                    && Math.abs(square.getX()-x) == 2))) {
                ret = null;
            } else {
                ret = getMiddlePiece(square);
            }
        } else if(type.equals(Type.BLACK)) {
            if (!((square.getY() == y - 2 &&
                    Math.abs(square.getX()-x) == 2))) {
                ret = null;
            } else {
                ret = getMiddlePiece(square);
            }
        } else if(type.equals(Type.RED)){
            if (!((square.getY() == y + 2 &&
                    Math.abs(square.getX()-x) == 2))) {
                ret = null;
            } else {
                ret = getMiddlePiece(square);
            }
        } else {
            throw new IllegalStateException("This piece has an unknown type:"+type);
        }
        return ret;
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

    private boolean checkKing(Piece piece) {
        if (type.equals(Type.BLACK)) {
            return piece.y == boardTop;
        } else if (type.equals(Type.RED)) {
            return piece.y == boardBottom;
        } else {
            throw new IllegalStateException("This piece has an unknown type:"+type);
        }
    }

    private void makeKing(Piece piece) {
        king = true;
        crown.setVisible(true);
    }

}

