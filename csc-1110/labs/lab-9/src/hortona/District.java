/*
 * Course: CSC1110 - 110
 * Fall 2024
 * Lab 8 - ParkingLots
 * Name: Alexander Horton
 * Created: 10/25/2024
 */
package hortona;

/**
 * Manages parking lots within a district.
 * @author [Alexander Horton]
 */
public class District {
    /**
     * Maximum lots allowed
     */
    public static final int MAX_LOTS = 20;
    private ParkingLot[] lots;
    private int numLots;
    private int timeOfClose = 0;
    private int totalMinutesClosed = 0;
    private boolean alreadyClosed = false;

    /**
     * Create a district with the max number of lots
     */
    public District() {
        lots = new ParkingLot[MAX_LOTS];
    }

    @Override
    public String toString() {
        String ret = "District status:\n";
        for (int i = 0; i < numLots; i++) {
            ret += "  " + lots[i].toString() + "\n";
        }
        return ret;
    }

    /**
     * Adds a lot to the array of lots
     * @param name name of the lot
     * @param capacity the lot's capacity
     * @return returns the index of the new lot or -1 if invalid
     */
    public int addLot(String name, int capacity) {
        int newIndex = this.numLots;
        if(newIndex < MAX_LOTS) {
            this.lots[newIndex] = new ParkingLot(name, capacity);
            this.numLots++;
        }
        // return the index of the new lot or -1 if the lot was not added.
        return newIndex < MAX_LOTS ? newIndex : -1;
    }

    /**
     * returns a ParkingLot object at the given index
     * @param index the index of the lot in the array
     * @return ParkingLot at given index or null if invalid
     */
    public ParkingLot getLot(int index) {
        if (index > MAX_LOTS - 1) {
            return null;
        } else {
            return lots[index];
        }
    }
    /**
     * Returns the number of remaining parking spots in the district
     * @return the number of remaining parking spots in the district
     */
    public int getNumberOfSpotsRemaining() {
        int remaining = 0;
        for (int i = 0; i < numLots; i++) {
            remaining += getLot(i).getNumberOfSpotsRemaining();
        }
        return remaining;
    }

    /**
     * Returns the amount of time all three lots have been
     * simultaneously closed.
     * @return number of minutes all three lots have been closed
     */
    public int getMinutesClosed() {
        return totalMinutesClosed;
    }

    /**
     * Checks the status of all three lots in the district and
     * returns true if they are all closed and false otherwise.
     * @return whether all three lots in the district are closed
     */
    public boolean isClosed() {
        boolean anyOpen = false;
        for (int i = 0; i < numLots && !anyOpen; i++) {
            if (!getLot(i).isClosed()) {
                anyOpen = true;
            }
        }
        return !anyOpen;
    }

    /**
     * Record a vehicle entering a lot at a specified timestamp.
     * <p></p>
     * This calls ParkingLot.markVehicleEntry for the lot corresponding
     * to lotNumber (e.g., if lotNumber==1, call markVehicleEntry on lot1).
     * <p></p>
     * If lotNumber is out of range, the method should return without
     * doing anything else.
     * @param lotNumber Number of lot (should be 1, 2, or 3)
     * @param timestamp Entry timestamp in minutes since all lots were opened.
     */
    public void markVehicleEntry(int lotNumber, int timestamp) {
        getLot(lotNumber).markVehicleEntry(timestamp);
        for (int i = 0; i < numLots; i++) {
            if(getLot(i).isClosed() && !alreadyClosed) {
                timeOfClose = timestamp;
                alreadyClosed = true;
            }
        }
    }

    /**
     * Record a vehicle exiting a lot at a specified timestamp.
     * <p></p>
     * This calls ParkingLot.markVehicleExit for the lot corresponding
     * to lotNumber (e.g., if lotNumber==1, call markVehicleExit on lot1).
     * <p></p>
     * If lotNumber is out of range, the method should return without
     * doing anything else.
     * @param lotNumber Number of lot (should be 1, 2, or 3)
     * @param timestamp Exit timestamp in minutes since all lots were opened.
     */
    public void markVehicleExit(int lotNumber, int timestamp) {
        getLot(lotNumber).markVehicleExit(timestamp);
        boolean anyOpen = false;
        for (int i = 0; i < numLots && !anyOpen; i++) {
            if (!getLot(i).isClosed()) {
                anyOpen = true;
            }
        }
        if (anyOpen && alreadyClosed) {
            totalMinutesClosed = timestamp - timeOfClose;
            alreadyClosed = false;
        }
    }
}