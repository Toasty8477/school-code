/*
 * Course: CSC-1120 - 131
 * Lab 2 - Image Displayer 3001
 * Name: Alexander Horton
 * Last Updated: 01/30/2025
 */

package hortona;

/**
 * Custom exception for Invalid data inputs for the Image class
 */
public class InvalidFormatException extends RuntimeException {

    /**
     * A generic error message
     */
    public InvalidFormatException() {
        super();
    }

    /**
     * A custom error message
     * @param message message shown when error is thrown
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}