/*
 * Course: SWE2721 - 121
 * Spring 2026
 * Lab 5
 * Name: Alex Horton
 * Created: 2/19/2025
 */

package msoe.swe2721.lab4_5;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import msoe.swe2721.lab4_5.provided.Customer;
import msoe.swe2721.lab4_5.provided.CustomerManagerInterface;
import msoe.swe2721.lab4_5.provided.InventoryItem;
import msoe.swe2721.lab4_5.provided.InventoryManagerInterface;
import msoe.swe2721.lab4_5.provided.Order;
import msoe.swe2721.lab4_5.provided.OrderManagerInterface;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;
import msoe.swe2721.lab4_5.provided.exceptions.InventoryOutOfStockException;

public class OrderManager implements OrderManagerInterface {

    private CustomerManagerInterface cmi;
    private InventoryManagerInterface imi;
    private List<Order> orders;

    OrderManager(CustomerManagerInterface cmi, InventoryManagerInterface imi) throws InvalidParameterException {
        if (cmi == null || imi == null) {
            throw new InvalidParameterException("CMI or IMI is null");
        }
        this.cmi = cmi;
        this.imi = imi;
        orders = new ArrayList<>();
    }

    @Override
    public int obtainOrderCount() {
        return orders.size();
    }

    @Override
    public Order obtainOrder(int orderSequence) throws InvalidParameterException {
        if (orderSequence < 1) {
            throw new InvalidParameterException("Order sequence must be greater than 1");
        }
        if (orderSequence > orders.size()) {
            throw new InvalidParameterException("Order sequence cannot be greater than the number of orders");
        }
        return orders.get(orderSequence - 1);
    }

    @Override
    public Order OrderItem(int customerID, int sku, int count)
            throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException {
        if (customerID <= 0) {
            throw new InvalidCustomerParameterException("Invalid customer ID");
        }

        Customer customer = cmi.findCustomerByID(customerID);
        if (customer == null) {
            throw new InvalidCustomerParameterException("Customer not in system");
        }
        return OrderItem(customer, sku, count);
    }

    @Override
    public Order OrderItem(String firstName, String lastName, int sku, int count)
            throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException {
        firstName.trim();
        lastName.trim();
        
        if (firstName.length() <= 0) {
            throw new InvalidCustomerParameterException("Invalid first name");
        }
        if (lastName.length() <= 0) {
            throw new InvalidCustomerParameterException("Invalid last name");
        }

        Customer customer = cmi.findCustomerByName(firstName, lastName);
        if (customer == null) {
            throw new InvalidCustomerParameterException("Customer not in system");
        }
        return OrderItem(customer, sku, count);
    }

    private Order OrderItem(Customer customer, int sku, int count)
            throws InventoryOutOfStockException, InvalidSKUException {
        if (sku < 0) {
            throw new InvalidSKUException("Invalid SKU");
        }

        InventoryItem item = imi.obtainItemBySKU(sku);
        if (item == null) {
            throw new InvalidSKUException("Item does not exist in inventory");
        }
        if (item.getStockCount() == 0) {
            throw new InventoryOutOfStockException("Item has no stock");
        }

        Order order = new Order(customer, item, Math.min(count, item.getStockCount()));
        orders.add(order);
        return order;
    }

}
