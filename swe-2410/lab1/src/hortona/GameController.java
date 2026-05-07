/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 1 - Checkers Lab No Patterns
 * Name: Adela Velez
 * Created: 1/2/2025
 */

package hortona;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is both the controller for the main FXML window
 * and manages the entire board. It thus holds all the squares
 * on the board and each square can hold a piece.
 */
public class GameController {
    /**
     * Size of each square
     */
    public static final int SQUARE_SIZE = 60;
    /**
     * Width of the board
     */
    public static final int BOARD_WIDTH = 6;

    @FXML
    private Label message; // red text below the squares.
    @FXML
    private Pane table; // green space on which board squares and pieces are laid

    private Piece.Type whoseTurn = Piece.Type.BLACK;
    private final List<Square> squares = new ArrayList<>();
    private Piece activePiece = null;

    @FXML
    private void initialize() {
        createSquares();
        for(int i = 0; i < BOARD_WIDTH; i += 2) {
            createPiece(Piece.Type.RED, i, 0);
        }
        for(int i = 1; i < BOARD_WIDTH; i += 2) {
            createPiece(Piece.Type.BLACK, i, BOARD_WIDTH-1);
        }
        table.setFocusTraversable(true); // ensure table will receive key presses
    }

    public Label getMessage() {
        return message;
    }
    public Pane getTable() {
        return table;
    }
    /**
     * Tries to set the passed in piece to be the active piece.
     * Will print a message if the current piece can be set to active.
     * @param piece The Piece to check.
     */
    public void trySetActive(Piece piece) {
        if(piece.getType().equals(whoseTurn)) {
            if(activePiece != null) {
                activePiece.setActive(false);
            }
            piece.setActive(true);
            activePiece = piece;
            message.setText("Click on the square to which that piece should move.");
        } else {
            message.setText("""
                    This piece cannot move because it is not that player's turn.
                    Please select a piece from the opposite player""");
        }
    }

    /**
     * Tries to move the active piece to the passed in square.
     * Will print a message if there is no active piece.
     * @param square The Square to move to.
     */
    public void tryMovePiece(Square square) {
        if(activePiece == null) {
            message.setText("Please click on a piece to move.");
        } else {
            activePiece.tryMove(square);
        }
    }

    /**
     * Changes the whoseTurn attribute. Called after a piece moves.
     */
    public void switchTurns() {
        if(whoseTurn.equals(Piece.Type.BLACK)) {
            whoseTurn = Piece.Type.RED;
        } else { //assumes only two types
            whoseTurn = Piece.Type.BLACK;
        }
        activePiece = null;
        message.setText("Click on a piece from the opposite player to move it.");
    }

    /**
     * Gets the square at the passed in coordinates.
     * @param x X position (column) of the queried Square.
     * @param y Y position (row) of the queried Square.
     *          Y values increase from top to bottom.
     * @return The Square object at the passed-in coordinates.
     * @throws IllegalArgumentException if the passed in coordinates do not fall
     * within the bounds of the board.
     */
    public Square getSquare(int x, int y) throws IllegalArgumentException {
        if(!isValidPosition(x, y)) {
            throw new IllegalArgumentException("This square does not exist: "+x+ " "+ y);
        }
        return squares.get(y*BOARD_WIDTH + x);
    }

    private void createSquares() {
        for(int indRow = 0; indRow < BOARD_WIDTH; indRow++) {
            for(int indCol = 0; indCol < BOARD_WIDTH; indCol++) {
                Square square = new Square(indCol, indRow, this);
                squares.add(square);
            }
        }
    }
    private void createPiece(Piece.Type type, int x, int y) {
        Piece piece = new Piece(type, x, y, this);
        getSquare(x, y).tryPlacePiece(piece);
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < BOARD_WIDTH && y < BOARD_WIDTH;
    }
}
