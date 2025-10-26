/*
 TODO
 */
package username;

import java.util.List;

/**
 * TODO
 */
public class FullTime extends Employee {
    private final double salary;

    /**
     * Creates a FullTime Employee.
     * @param id ID of the employee
     * @param name Name of employee
     * @param address Address of the employee
     * @param deductions List of Deductions for the employee
     * @param salary Yearly salary.
     * @throws IllegalArgumentException if salary is zero.
     */
    public FullTime(int id, String name, String address, double salary,
                    List<Deduction> deductions) throws IllegalArgumentException {
        super(id, name, address, deductions);
        if (salary > 0) {
            this.salary = salary;
        } else {
            throw new IllegalArgumentException("Salary must be greater than 0.");
        }
    }

    @Override
    public double getPay() {
        final int payPeriods = 24;
        return salary / payPeriods;
    }
}