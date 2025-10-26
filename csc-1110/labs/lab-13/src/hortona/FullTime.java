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
 * Represents a full time Employee with a salary.
 */
public class FullTime extends Employee {
    private final double salary;

    /**
     * Creates a FullTime Employee.
     * @param id ID of the employee
     * @param name Name of employee
     * @param address Address of the employee
     * @param salary Yearly salary.
     * @param deductions List of Deductions for the Employee
     * @throws IllegalArgumentException if salary is less than or equal to zero.
     */
    public FullTime(int id, String name, String address, double salary,
                    List<Deduction> deductions) throws IllegalArgumentException {
        super(id, name, address, deductions);
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary is less than or equal to zero");
        }
        this.salary = salary;
    }

    @Override
    public double getPay() {
        final double payPeriods = 24;
        return salary / payPeriods;
    }
    public double getSalary() {
        return salary;
    }
    protected String paymentBreakdown() {
        double netPay = getPay() - totalDeductions();
        return String.format("Payment Details:\n%s\n%s\n%s\n",
                Employee.formatLine("Salary:", this.getSalary()),
                Employee.formatLine("Gross Pay:", getPay()),
                Employee.formatLine("Net Pay:", netPay));
    }
    @Override
    public String toString() {
        return String.format(super.toString() + "\tType: %s\n\tSalary: %.2f\n",
                "FullTime", getSalary());
    }
}
