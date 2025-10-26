/**************************
* Course: CSC1110 - 111
* Fall 2024
* ASSIGNMENT # - REPLACE ME
* Name: Alexander Horton
* Created 1/23/2025
**************************/

package hortona;

/**
 * Provides aliases for the Unicode block characters
 * as well as methods to convert between Unicode and 01 text format.
 */
public enum PixelCluster {

    /**
     * Full Block
     */
    BLACK('\u2588'),
    /**
     * Unfilled Block
     */
    WHITE('\u0020'),
    /**
     * Upper Half Block
     */
    TOP_BLACK('\u2580'),
    /**
     * Lower Half Block
     */
    BOTTOM_BLACK('\u2584'),
    /**
     * Left Half Block
     */
    LEFT_BLACK('\u258C'),
    /**
     * Right Half Block
     */
    RIGHT_BLACK('\u2590'),
    /**
     * Quadrant Upper Left and Bottom Right
     */
    TOP_LEFT_BOTTOM_RIGHT('\u259A'),
    /**
     * Quadrant Upper Right and Bottom Left
     */
    TOP_RIGHT_BOTTOM_LEFT('\u259E'),
    /**
     * Quadrant Lower Left
     */
    BOTTOM_LEFT_BLACK('\u2596'),
    /**
     * Quadrant Lower Right
     */
    BOTTOM_RIGHT_BLACK('\u2597'),
    /**
     * Quadrant Upper Left
     */
    TOP_LEFT_BLACK('\u2598'),
    /**
     * Quadrant Upper Right
     */
    TOP_RIGHT_BLACK('\u259D'),
    /**
     * Quadrant Upper Right and Lower Left and Lower Right
     */
    TOP_LEFT_WHITE('\u259F'),
    /**
     * Quadrant Upper Left and Lower Left and Lower Right
     */
    TOP_RIGHT_WHITE('\u2599'),
    /**
     * Quadrant Upper Left and Upper Right and Lower Right
     */
    BOTTOM_LEFT_WHITE('\u259C'),
    /**
     * Quadrant Upper Left and Upper Right and Lower Left
     */
    BOTTOM_RIGHT_WHITE('\u259B');

    private final char code;

    PixelCluster(char code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return Character.toString(code);
    }

    /**
     * Returns a Unicode block corresponding to the inputted 2x2 array
     * @param cluster a 2x2 array of zeros and ones
     * @return a Unicode Block character
     * @throws IllegalArgumentException if array is not 2x2
     * or does not contain only zeros and ones
     */
    public static PixelCluster getCluster(int[][] cluster) throws IllegalArgumentException {
        boolean validCluster = true;
        String clusterString = "";
        PixelCluster ret;

        if (cluster.length == 2 && cluster[0].length == 2) {
            for (int i = 0; i < cluster.length; i++) {
                for (int j = 0; j < cluster[0].length; j++) {
                    if (cluster[i][j] == 0 || cluster[i][j] == 1) {
                        clusterString += cluster[i][j];
                    } else {
                        validCluster = false;
                    }
                }
            }
        } else {
            validCluster = false;
        }

        if (validCluster) {
            switch(clusterString) {
                case "1111" -> ret = BLACK;
                case "0000" -> ret = WHITE;
                case "1100" -> ret = TOP_BLACK;
                case "0011" -> ret = BOTTOM_BLACK;
                case "1010" -> ret = LEFT_BLACK;
                case "0101" -> ret = RIGHT_BLACK;
                case "1001" -> ret = TOP_LEFT_BOTTOM_RIGHT;
                case "0110" -> ret = TOP_RIGHT_BOTTOM_LEFT;
                case "0010" -> ret = BOTTOM_LEFT_BLACK;
                case "0001" -> ret = BOTTOM_RIGHT_BLACK;
                case "1000" -> ret = TOP_LEFT_BLACK;
                case "0100" -> ret = TOP_RIGHT_BLACK;
                case "0111" -> ret = TOP_LEFT_WHITE;
                case "1011" -> ret = TOP_RIGHT_WHITE;
                case "1101" -> ret = BOTTOM_LEFT_WHITE;
                case "1110" -> ret = BOTTOM_RIGHT_WHITE;
                default -> throw new IllegalArgumentException("Not a valid pixel cluster");
            }

        } else {
            throw new IllegalArgumentException("Not a valid pixel cluster");
        }

        return ret;
    }

    /**
     * Returns a 2x2 array corresponding to the inputted Unicode block
     * @param code a Unicode block character
     * @return a 2x2 array of zeros and ones
     * @throws IllegalArgumentException if the inputted char is not a Unicode block
     */
    public static int[][] getPixels(char code) throws IllegalArgumentException {

        return switch (code) {
            case '\u2588' -> new int[][] {
                    {1, 1}, {1, 1}}; //Black
            case '\u0020' -> new int[][] {
                    {0, 0}, {0, 0}}; //White
            case '\u2580' -> new int[][] {
                    {1, 1}, {0, 0}}; //Top Black
            case '\u2584' -> new int[][] {
                    {0, 0}, {1, 1}}; //Bottom Black
            case '\u258C' -> new int[][] {
                    {1, 0}, {1, 0}}; //Left Black
            case '\u2590' -> new int[][] {
                    {0, 1}, {0, 1}}; //Right Black
            case '\u259A' -> new int[][] {
                    {1, 0}, {0, 1}}; //Top Left Bottom Right
            case '\u259E' -> new int[][] {
                    {0, 1}, {1, 0}}; //Top Right Bottom Left
            case '\u2596' -> new int[][] {
                    {0, 0}, {1, 0}}; // Bottom Left Black
            case '\u2597' -> new int[][] {
                    {0, 0}, {0, 1}}; // Bottom Right Black
            case '\u2598' -> new int[][] {
                    {1, 0}, {0, 0}}; // Top Left Black
            case '\u259D' -> new int[][] {
                    {0, 1}, {0, 0}}; // Top Right Black
            case '\u259F' -> new int[][] {
                    {0, 1}, {1, 1}}; // Top Left White
            case '\u2599' -> new int[][] {
                    {1, 0}, {1, 1}}; // Top Right White
            case '\u259C' -> new int[][] {
                    {1, 1}, {0, 1}}; // Bottom Left White
            case '\u259B' -> new int[][] {
                    {1, 1}, {1, 0}}; // Bottom Right White
            default -> throw new IllegalArgumentException("Not a valid unicode block");
        };

    }
}
