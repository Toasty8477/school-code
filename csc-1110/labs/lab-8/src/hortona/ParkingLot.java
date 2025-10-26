/*
 * Course: CSC1110 - 110
 * Fall 2024
 * Lab 8 - Parking Lots
 * Name: Alexander Horton
 * Created: 10/25/24
 */
package hortona;

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

    ParkingLot(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
    ParkingLot(int capacity) {
        this("test", capacity);
    }

    /**
     * Prints whether the lot is closed or open
     */
    public void displayStatus(){
        if (full) {
            System.out.println("CLOSED");
        } else {
            System.out.println("OPEN");
        }
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
        return ((double) spotsFilled / capacity) * toPercentage >= CLOSED_THRESHOLD;
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
        if (((double) spotsFilled / capacity) * toPercentage >= CLOSED_THRESHOLD
                && isClosed() && !alreadyClosed) {
            full = true;
            timeOfClose = timestamp;
            alreadyClosed = true;
        } else if (((double) spotsFilled / capacity) * toPercentage >= CLOSED_THRESHOLD
                && isClosed()) {
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
        if (((double) spotsFilled / capacity) * toPercentage <= CLOSED_THRESHOLD
                && !isClosed()) {
            if (full) {
                totalTimeClosed += timestamp - timeOfClose;
                timeOfClose = timestamp;
            }
            full = false;
            alreadyClosed = false;

        }
    }
}
