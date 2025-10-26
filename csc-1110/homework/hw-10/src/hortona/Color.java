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
enum Color {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String ansi;

    Color(String ansi){
        this.ansi = ansi;
    }

    public String toString() {
        return ansi;
    }
}