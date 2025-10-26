/*
 * Course: CS1110 - 111
 * Fall 2024
 * Homework 10
 * Name: Alexander Horton
 * Last Updated: 11/7/2024
 */
package hortona;

/**
 * Enum of ansi colors.
 */
public enum Color {
    /**
     * reset to default color
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

    /**
     * returns the ansi escape code of the color
     * @return ansi escape code
     */
    public String toString() {
        return ansi;
    }
}