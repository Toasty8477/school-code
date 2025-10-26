/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 7
 * Name: Alexander Horton
 * Last Updated: 10/16/2024
 */
package hortona;

/**
 * Class that prints the time you set
 */
public class Time {
    private int hours;  
    private int minutes;  
    private int seconds;

    /**
     * sets the hours
     * @param hours set the hour number
     * @return Time object
     */
    public Time setHours(int hours){
        this.hours = hours;
        return this;
    }

    /**
     * sets the minutes
     * @param minutes set the minute number
     * @return Time object
     */
    public Time setMinutes(int minutes) {
        this.minutes = minutes;
        return this;  
    }

    /**
     * sets the seconds
     * @param seconds set the second number
     * @return Time object
     */
    public Time setSeconds(int seconds) {
        this.seconds = seconds;
        return this;  
    }

    /**
     * Print time in format hh:mm:ss
     */
    public void printTime() {
        System.out.printf("%02d:%02d:%02d\n", hours, minutes, seconds);  
    }
}
