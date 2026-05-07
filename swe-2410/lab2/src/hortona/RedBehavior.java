/*
 * Course: SWE2410 - 111
 * Spring 2026
 * Lab 2 - Strategic Checkers
 * Name: Alex Horton
 * Created: 1/27/2025
 */

package hortona;

/**
 * Behavior for the red checker type
 */
public class RedBehavior extends MoveBehavior {

    @Override
    boolean isValidOrdinaryMove(Square square, Piece piece) {
        return square.getY() == piece.getY() + 1 && Math.abs(square.getX() - piece.getX()) == 1;
    }

    @Override
    Piece getCapturedPiece(Square square, Piece piece) {
        Piece ret;
        if (!((square.getY() == piece.getY() + 2 && Math.abs(square.getX() - piece.getX()) == 2))) {
            ret = null;
        } else {
            ret = getMiddlePiece(square, piece);
        }
        return ret;
    }
}
