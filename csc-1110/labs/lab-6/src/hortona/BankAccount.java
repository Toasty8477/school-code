/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 6 - Bank Account
 * Name: Alexander Horton
 * Last Updated: 10/11/2024
 */

package hortona;

/**
 * Simple class that emulates a bank account
 */
public class BankAccount {
    private double balance;
    private int age;
    private String name;
    private final String pin;

    BankAccount(String name, int age, String pin) {
        this.name = name;
        this.age = age;
        this.pin = pin;
    }

    /**
     * Deposit money into the account
     * @param amount The amount to deposit
     * @return 1 if valid, -1 if not
     */
    public int deposit(double amount) {
        if (amount >= 0) {
            balance = balance + amount;
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Withdraw money from the account
     * @param pin Pin entered by user
     * @param amount Amount to withdraw
     * @return 1 if valid, -1 if not
     */
    public int withdraw(String pin, double amount) {
        if (amount >= 0 && amount <= balance && pin.equals(this.pin)) {
            balance = balance - amount;
            return 1;
        } else {
            return -1;
        }
    }
    public int getAge() {
        return age;
    }
    public double getBalance() {
        return balance;
    }
    public String getName() {
        return name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
}
