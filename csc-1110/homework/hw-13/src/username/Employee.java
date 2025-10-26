/*
 TODO
 */
package username;

import java.util.List;

/**
 * TODO
 */
public abstract class Employee {
    private final String name;
    private final String address;
    private final int id;
    private final List<Deduction> deductions;

    /**
     * Creates a new Employee object.
     * @param id ID of the employee
     * @param name Name of employee
     * @param address Address of the employee
     * @param deductions List of Deductions for the employee
     * @throws IllegalArgumentException If any of the passed in arguments are invalid.
     */
    public Employee(int id, String name, String address, List<Deduction> deductions)
            throws IllegalArgumentException {
        if (name == null || address == null || deductions == null) {
            throw new IllegalArgumentException("One or more arguments was null.");
        } else {
            this.name = name;
            this.address = address;
            this.id = id;
            this.deductions = deductions;
        }
    }

    public int getId() {
        return id;
    }

    protected double totalDeductions() {
        double ret = 0;
        for(Deduction d: deductions) {
            ret += d.getAmount();
        }
        return ret;
    }

    /**
     * Returns a string representing the employee in the following format:
     * Employee ID: 313
     *     Name: John Zoidberg
     *     Address: 13 Krusty Crab Ln., New York, NY
     *     Deductions:
     *           Pet sitter : $  200.00
     *     Health Insurance : $ 1000.00
     *                Total : $ 1200.00
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Employee ID: %d\n\tName: %s\n\tAddress: %s\n\tDeductions:\n",
                id, name, address));
        for(Deduction d:deductions) {
            sb.append(String.format("%s\n", d.toString()));
        }
        sb.append(String.format("%20s : $ %7.2f", "Total", totalDeductions()));
        return sb.toString();
    }

    /**
     * Returns the gross pay for this Employee
     * @return Gross pay.
     */
    public abstract double getPay();
}