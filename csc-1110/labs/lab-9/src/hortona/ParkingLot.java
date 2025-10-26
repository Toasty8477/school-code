/*
 * Course: CSC1110 - 110
 * Fall 2024
 * Lab 8 - Parking Lots
 * Name: Alexander Horton
 * Created: 10/25/24
 */
package hortona;

import java.text.DecimalFormat;

/**
 * Handles cars entering and leaving a parking lot
 */
public class ParkingLot {
    /**
     * Percentage threshold for when the lot is considered closed.
     */
    public static final double CLOSED_THRESHOLD = 80.0;
    private final int toPercentage = 100;
    private final String name;
    private final int capacity;
    private int spotsFilled = 0;
    private boolean full = false;
    private boolean alreadyClosed = false;
    private int timeOfClose = 0;
    private int totalTimeClosed = 0;
    private int lastTimestamp;
    private final DecimalFormat formatter;

    ParkingLot(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        formatter = new DecimalFormat("#.#");
    }
    ParkingLot(int capacity) {
        this("test", capacity);
    }

    /**
     * Returns a string for the status of the parking lot
     * @return the status of the lot
     */
    @Override
    public String toString(){
        String ret = "Status for " + name + " parking lot: "+spotsFilled+" vehicles (";
        if (getPercentFull() <= CLOSED_THRESHOLD) {
            ret += formatter.format((double)spotsFilled/capacity * toPercentage) + "%)";
        } else {
            ret += "CLOSED)";
        }
        return ret;
    }
    public int getMinutesClosed(){
        return totalTimeClosed;
    }
    public String getName(){
        return this.name;
    }
    public int getNumberOfSpotsRemaining(){
        return capacity-spotsFilled;
    }
    public double getPercentFull(){
        return (double)spotsFilled/capacity * toPercentage;
    }

    /**
     * Checks if the lot is open
     * @return true if open, false if closed.
     */
    public boolean isClosed() {
        return getPercentFull() >= CLOSED_THRESHOLD;
    }

    /**
     * Handles the entry of vehicles into the lot
     * @param timestamp Time at which the vehicle entered.
     */
    public void markVehicleEntry(int timestamp){
        if (timestamp >= lastTimestamp) {
            spotsFilled++;
            lastTimestamp = timestamp;
        }
        if (getPercentFull() >= CLOSED_THRESHOLD && isClosed() && !alreadyClosed) {
            full = true;
            timeOfClose = timestamp;
            alreadyClosed = true;
        } else if (getPercentFull() >= CLOSED_THRESHOLD && isClosed()) {
            full = true;
        }

    }

    /**
     * Handles vehicles exiting the lot
     * @param timestamp Time at which the vehicle exited.
     */
    public void markVehicleExit(int timestamp) {
        if (timestamp >= lastTimestamp) {
            spotsFilled--;
        }
        if (getPercentFull() <= CLOSED_THRESHOLD && !isClosed()) {
            if (full) {
                totalTimeClosed += timestamp - timeOfClose;
                timeOfClose = timestamp;
            }
            full = false;
            alreadyClosed = false;

        }
    }
}
