/*
 TODO
 */
package username;

/**
 * TODO
 */
public class Deduction {
    private final String type;
    private final double amount;

    /**
     * TODO
     */
    public Deduction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Returns a string of the following format:
     * <br />
     * <pre>                Type : $10000.00</pre>
     * <br />
     * <pre>12345678901234567890    12345678</pre>
     * <br />
     * where there are twenty columns to represent the type and
     * the dollar amount is given eight columns and should display
     * two digits after the decimal place.
     * @return The string representation of the deduction
     */
    @Override
    public String toString() {
        return String.format("%20s : $%8.2f", type, amount);
    }
}