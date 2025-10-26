/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 10 - StarBlind
 * Name: Alexander Horton
 * Created 11/08/2024
 */
package hortona;

/**
 * list of ansi color escape codes
 */
public enum Color {
    /**
     * Resets color to default
     */
    RESET("\u001B[0m"),
    /**
     * black
     */
    BLACK("\u001B[30m"),
    /**
     * red
     */
    RED("\u001B[31m"),
    /**
     * green
     */
    GREEN("\u001B[32m"),
    /**
     * yellow
     */
    YELLOW("\u001B[33m"),
    /**
     * blue
     */
    BLUE("\u001B[34m"),
    /**
     * magenta
     */
    MAGENTA("\u001B[35m"),
    /**
     * cyan
     */
    CYAN("\u001B[36m"),
    /**
     * white
     */
    WHITE("\u001B[37m");

    private final String ansi;

    Color(String ansi){
        this.ansi = ansi;
    }

    @Override
    public String toString() {
        return ansi;
    }

    /**
     * colors only the passed in text
     * @param color the color to make the text
     * @param text the text to be colored
     * @return the colored text
     */
    public static String colorString(Color color, String text) {
        return color + text;
    }

    /**
     * gets a random color
     * @return an ansi color code
     */
    public static Color getColor() {
        final int colorChoices = 6;
        final int red = 1;
        final int green = 2;
        final int yellow = 3;
        final int blue = 4;
        final int magenta = 5;
        final int cyan = 6;
        int choice = (int)(Math.random()*colorChoices+1);
        Color ret = Color.BLACK;
        if (choice == red) {
            ret = Color.RED;
        } else if (choice == green) {
            ret = Color.GREEN;
        } else if (choice == yellow) {
            ret = Color.YELLOW;
        } else if (choice == blue) {
            ret = Color.BLUE;
        } else if (choice == magenta) {
            ret = Color.MAGENTA;
        } else if (choice == cyan) {
            ret = Color.CYAN;
        }
        return ret;
    }

    /**
     * gets a color based and a letter
     * @param code r, g, y, b, m, or c
     * @return and ansi color code
     */
    public static Color getColor(char code) {
        final char red = 'r';
        final char green = 'g';
        final char yellow = 'y';
        final char blue = 'b';
        final char magenta = 'm';
        final char cyan = 'c';
        Color ret = Color.BLACK;
        if (code == red) {
            ret = Color.RED;
        } else if (code == green) {
            ret = Color.GREEN;
        } else if (code == yellow) {
            ret = Color.YELLOW;
        } else if (code == blue) {
            ret = Color.BLUE;
        } else if (code == magenta) {
            ret = Color.MAGENTA;
        } else if (code == cyan) {
            ret = Color.CYAN;
        }
        return ret;
    }
}