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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Represents a place on the board where a piece can sit.
 */
public class Square {
    private static final int SQUARE_SIZE = GameController.SQUARE_SIZE;
    /**
     * X location (column) and Y location (row) of the Square.
     */
    private final int x;
    private final int y;

    /**
     * The piece currently sitting on this square,
     * null if this square is empty.
     */
    private Piece piece = null;

    /**
     * Sets the position of this square on the board, where the top left square
     * is at (x,y) == (0,0),
     * the top right square is at (BoardController.BOARD_WIDTH-1,0)
     * and the bottom right square is at (BOARD_WIDTH-1,BOARD_WIDTH-1).
     *
     * @param x the horizontal position, increasing rightwards.
     * @param y the vertical position, increasing downwards.
     * @param controller The controller object that manages all the Squares
     */
    public Square(int x, int y, GameController controller) {
        this.x = x;
        this.y = y;
        Paint paint;
        if((this.y + this.x)%2==0) {
            paint = Color.BLACK.brighter().brighter().brighter().brighter();
        } else {
            paint = Color.RED.darker().darker();
        }
        Rectangle rectangle = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, paint);
        rectangle.setX(this.x *SQUARE_SIZE);
        rectangle.setY(this.y *SQUARE_SIZE);
        rectangle.setOnMouseClicked((MouseEvent event) -> controller.tryMovePiece(this));
        controller.getTable().getChildren().add(rectangle);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns a String representation of the Square which
     * consists of its (x,y) position.
     * @return String representation of the Square.
     */
    public String toString() {
        return "Square at "+x+", "+y;
    }

    /**
     * Returns the piece currently sitting on the square.
     * @return Piece on the Square or null if no Piece is on the Square.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Removes the current piece from the Square.
     * @throws IllegalStateException If there was no piece on this square
     */
    public void removePiece() throws IllegalStateException {
        if(piece == null) {
            throw new IllegalStateException("Cannot remove piece from an empty square.");
        }
        piece = null;
    }

    /**
     * Attempts to place the passed-in piece on this Square.
     * @param piece Piece to be placed.
     * @throws UnsupportedOperationException If this Square already has a Piece on it.
     * @throws IllegalArgumentException If the passed-in Piece is null.
     */
    public void tryPlacePiece(Piece piece) throws UnsupportedOperationException,
            IllegalArgumentException {
        if(this.piece != null) {
            throw new UnsupportedOperationException("The place being moved to " +
                    "already has a piece on it.");
        }
        if(piece == null) {
            throw new IllegalArgumentException("No piece provided.");
        }
        this.piece = piece;
    }

}
