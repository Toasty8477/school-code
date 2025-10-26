/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 7
 * Name: Alexander Horton
 * Last Updated: 10/16/2024
 */
package hortona;

/**
 * Sets time and then prints it
 */
public class TimeDriver {
    public static void main(String[] args) {
        Time time = new Time();
        final int hours = 8;
        final int minutes = 59;
        final int seconds = 42;

        time.setHours(hours).setMinutes(minutes).setSeconds(seconds).printTime();
    }
}
