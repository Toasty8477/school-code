/*
 * Course: CS1110 - 111
 * Fall 2024
 * Lab 13 - Payroll Processing
 * Name: Alexander Horton
 * Last Updated: 12/13/2024
 */

package hortona;

/**
 * Represents a Deduction item that has a type and amount.
 */
public class Deduction implements Comparable<Deduction> {
    private final String type;
    private final double amount;

    /**
     * Represents a Deduction item that has a type and amount.
     * @param type Type of the deduction
     * @param amount Amount of the deduction
     */
    public Deduction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    /**
     * Compares two Deduction objects based on their amount.
     * @param that the object to be compared.
     * @return Positive value, zero, or a negative value if the amount for this Deduction is
     * greater than, equal to, or less than the amount of the passed in Deduction that.
     */
    public int compareTo(Deduction that) {
        if (this.amount > that.amount) {
            return 1;
        } else if (this.amount < that.amount) {
            return -1;
        } else {
            return 0;
        }
    }
    public double getAmount() {
        return amount;
    }
    public String getType() {
        return type;
    }
}