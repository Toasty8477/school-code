/*
 * Course: CS1110 - 111
 * Fall 2024
 * Lab 13 - Payroll Processing
 * Name: Alexander Horton
 * Last Updated: 12/13/2024
 */

package hortona;

import java.util.List;

/**
 * Represents an Hourly employee with an hourly rate and hours.
 */
public class Hourly extends Employee {
    private final double rate;
    private double hours;

    /**
     * Creates an Hourly Employee with an id, name, address, rate, and list of Deductions.
     * @param id ID of the employee.
     * @param name Name of employee.
     * @param address Address of the employee.
     * @param rate Hourly rate of the employee.
     * @param deductions List of Deductions for the Employee.
     * @throws IllegalArgumentException if rate is less than or equal to 0.
     */
    public Hourly(int id, String name, String address, double rate,
                  List<Deduction> deductions) throws IllegalArgumentException {
        super(id, name, address, deductions);
        if (rate > 0) {
            this.rate = rate;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getHours() {
        return hours;
    }
    @Override
    public double getPay() {
        return getRate() * getHours();
    }
    public double getRate() {
        return rate;
    }
    @Override
    protected String paymentBreakdown() {
        double netPay = getPay() - totalDeductions();
        return String.format("Payment Details:\n%s\n%s\n%s\n%s\n",
                Employee.formatLine("Hours:", getHours()),
                Employee.formatLine("Rate:", getRate()),
                Employee.formatLine("Gross Pay:", getPay()),
                Employee.formatLine("Net Pay:", netPay));
    }
    public void setHours(double hours) {
        this.hours = hours;
    }
    @Override
    public String toString() {
        return String.format("""
                        Employee ID: %d
                        \tName: %s
                        \tAddress: %s
                        \tType: %s
                        \tRate: %.2f
                        """,
                getId(), getName(), getAddress(), "Hourly", getRate());
    }
}
