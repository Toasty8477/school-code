/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 2 - Strategic Checkers
 * Name: Alex Horton
 * Created: 1/27/2025
 */

package hortona;

/**
 * The movement behavior of a checker
 */
public abstract class MoveBehavior {

    abstract boolean isValidOrdinaryMove(Square square, Piece piece);

    abstract Piece getCapturedPiece(Square square, Piece piece);

    /**
     * Assuming the given square represents a valid move, returns any piece
     * that would be captured during the move. If there is no piece, returns
     * null.
     * @param square Target square for the move
     * @param piece The piece that is being moved
     * @return null the piece that would be captured or null if there is none
     */
    public Piece getMiddlePiece(Square square, Piece piece) {
        int middleX = (square.getX() + piece.getX()) / 2;
        int middleY = (square.getY() + piece.getY()) / 2;
        return piece.getController().getSquare(middleX, middleY).getPiece();
    }
}
