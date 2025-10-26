/*
 * Course: CSC1120A - 131
 * Homework 1 - Colors
 * Color Driver
 * Name: Alexander Horton
 * Last Updated: 1/22/2025
 */

package hortona;

/**
 * An enumeration of ansi escape codes for colors
 */
public enum Color {
    /**
     * Reset the color
     */
    RESET("\u001B[0m"),
    /**
     * Set the text color to black
     */
    BLACK("\u001B[30m"),
    /**
     * Set the text color to red
     */
    RED("\u001B[31m"),
    /**
     * Set the text color to green
     */
    GREEN("\u001B[32m"),
    /**
     * Set the text color to yellow
     */
    YELLOW("\u001B[33m"),
    /**
     * Set the text color to blue
     */
    BLUE("\u001B[34m"),
    /**
     * Set the text color to magenta
     */
    MAGENTA("\u001B[35m"),
    /**
     * Set the text color to cyan
     */
    CYAN("\u001B[36m"),
    /**
     * Set the text color to white
     */
    WHITE("\u001B[37m");

    private final String ansi;

    Color(String ansi) {
        this.ansi = ansi;
    }

    @Override
    public String toString() {
        return ansi;
    }
}
