/*
 * Course: SWE2721 - 121
 * Spring 2026
 * Lab 5
 * Name: Alex Horton
 * Created: 2/19/2025
 */

package msoe.swe2721.lab4_5;

import java.util.HashMap;
import java.util.Map;

import msoe.swe2721.lab4_5.provided.Customer;
import msoe.swe2721.lab4_5.provided.CustomerManagerInterface;
import msoe.swe2721.lab4_5.provided.State;
import msoe.swe2721.lab4_5.provided.ZipCodeValidator;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateCustomerException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;

public class CustomerManager implements CustomerManagerInterface {

    private Map<Integer, Customer> customers;

    CustomerManager() {
        customers = new HashMap<>();
    }

    @Override
    public int addNewCustomer(String firstName, String lastName, int age, String streetAddress, String city,
            String state, int zip) throws InvalidCustomerParameterException, DuplicateCustomerException {

        final int minAge = 21;

        if (firstName == null) {
            throw new InvalidCustomerParameterException("First Name cannot be null");
        }

        if (lastName == null) {
            throw new InvalidCustomerParameterException("Last Name cannot be null");
        }

        if (streetAddress == null) {
            throw new InvalidCustomerParameterException("Street Address cannot be null");
        }

        if (city == null) {
            throw new InvalidCustomerParameterException("City cannot be null");
        }

        if (state == null) {
            throw new InvalidCustomerParameterException("State cannot be null");
        }

        // Trim whitespace
        firstName = firstName.trim();
        lastName = lastName.trim();

        // Check name length
        if (firstName.length() < 2 || lastName.length() < 2) {
            throw new InvalidCustomerParameterException("Invalid First or Last Name");
        }
        // Check age
        if (age < minAge) {
            throw new InvalidCustomerParameterException("Age is under minimum age for this system");
        }
        // Check address word ammount
        if (streetAddress.split(" ").length < 2) {
            throw new InvalidCustomerParameterException("Address must be at least two words");
        }

        boolean hasDigit = false;
        boolean hasLetter = false;

        for (int i = 0; i < streetAddress.length() && !hasDigit; i++) {
            if (Character.isDigit(streetAddress.charAt(i))) {
                hasDigit = true;
            }
        }
        for (int i = 0; i < streetAddress.length() && !hasLetter; i++) {
            if (Character.isLetter(streetAddress.charAt(i))) {
                hasLetter = true;
            }
        }
        if (!(hasDigit && hasLetter)) {
            throw new InvalidCustomerParameterException("Address must have a letter and a number");
        }
        // Check city length
        if (city.length() < 3) {
            throw new InvalidCustomerParameterException("City name must be at least 3 characters");
        }
        // Check state validity
        if (state.length() != 2 || State.valueOfAbbreviation(state).equals(State.UNKNOWN)) {
            throw new InvalidCustomerParameterException("State must be a two letter USPS abreviation");
        }
        // Check zip code
        ZipCodeValidator validator = ZipCodeValidator.getSingleton();
        if (!(validator.isValidZipcode(zip) && validator.getStateByZip(zip).equals(state))) {
            throw new InvalidCustomerParameterException("Zip code is not valid");
        }

        // Check duplicates
        if (findCustomerByName(firstName, lastName) != null) {
            throw new DuplicateCustomerException("Customer already exists in the system");
        }

        Customer customer = new Customer(firstName, lastName, age, streetAddress, city, State.valueOfAbbreviation(state), zip);
        customers.put(customer.getId(), customer);
        return customer.getId();
    }

    @Override
    public Customer findCustomerByName(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            return null;
        }
        firstName = firstName.trim();
        lastName = lastName.trim();
        for (Customer customer : customers.values()) {
            if (customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public Customer findCustomerByID(int id) throws InvalidCustomerParameterException {
        if (id <= 0) {
            throw new InvalidCustomerParameterException("ID Must be greater than 0");
        }
        return customers.get(id);
    }

    @Override
    public int getCustomerCount() {
        return customers.size();
    }
    
}
