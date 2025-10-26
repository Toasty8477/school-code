/*
 * Course: CS1110 - 111
 * Fall 2024
 * Lab 13 - Payroll Processing
 * Name: Alexander Horton
 * Last Updated: 12/13/2024
 */

package hortona;

import java.util.Collections;
import java.util.List;

/**
 * Base class for an employee
 */
public abstract class Employee {

    private final int id;
    private final String name;
    private final String address;
    private final List<Deduction> deductions;

    /**
     * Creates an employee
     * @param id id of employee
     * @param name name of employee
     * @param address employee's address
     * @param deductions employee's deductions
     * @throws IllegalArgumentException if any arguments are null or 0
     */
    public Employee(int id, String name, String address, List<Deduction> deductions)
            throws IllegalArgumentException {
        if (id <= 0 || name == null || address == null || deductions == null) {
            throw new IllegalArgumentException();
        } else {
            this.id = id;
            this.name = name;
            this.address = address;
            this.deductions = deductions;
            this.deductions.sort(Collections.reverseOrder());
        }
    }

    /**
     * Generates a String that consists of a String and number with the correct formating.
     * @param type Type of the value.
     * @param value Value to be formatted.
     * @return Formatted String with the correct spacing and decimal places.
     */
    public static String formatLine(String type, double value) {
        return String.format("\t%-20s%8.2f", type, value);
    }

    /**
     * Generates a String that can be written to the paystub.
     * @return String of information to be written to the paystub
     * which include information about the Employee,
     * list of Deductions sorted from largest to smallest,
     * and a breakdown of the amount to be paid.
     */
    public String generatePayStub() {
        StringBuilder sb = new StringBuilder();
        sb.append(this);
        if (!deductions.isEmpty()) {
            sb.append("Deductions:\n");
            for (Deduction d:deductions) {
                sb.append(String.format("\t%-20s%8.2f\n", d.getType(), d.getAmount()));
            }
            sb.append(String.format("\t%-20s%8.2f\n", "Total", totalDeductions()));
        }
        sb.append(paymentBreakdown());
        return sb.toString();
    }
    public String getAddress() {
        return address;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    /**
     * Returns the gross pay for this Employee
     * @return Gross pay.
     */
    public abstract double getPay();
    protected abstract String paymentBreakdown();
    @Override
    public String toString() {
        return String.format("Employee ID: %d\n\tName: %s\n\tAddress: %s\n", id, name, address);
    }
    protected double totalDeductions() {
        double ret = 0;
        for(Deduction d: deductions) {
            ret += d.getAmount();
        }
        return ret;
    }
}
