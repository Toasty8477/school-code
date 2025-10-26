/*
 TODO
 */
package username;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO
 */
public class Hourly extends Employee {
    private final double rate;
    private double hours;

    /**
     * Creates an Hourly Employee.
     * @param id ID of the employee
     * @param name Name of employee
     * @param address Address of the employee
     * @param deductions List of Deductions for the employee
     * @param rate Hourly rate
     * @throws IllegalArgumentException if salary is zero.
     */
    public Hourly(int id, String name, String address, double rate, List<Deduction> deductions) {
        super(id, name, address, deductions);
        if (rate > 0) {
            this.rate = rate;
        } else {
            throw new IllegalArgumentException("Rate must be greater than 0.");
        }
    }

    public void setHours(double hours){
        this.hours = hours;
    }

    @Override
    public double getPay() {
        return rate * hours;
    }
}