/*
 * Course: SWE2721 - 121
 * Spring 2026
 * Lab 5
 * Name: Alex Horton
 * Created: 2/19/2025
 */

package msoe.swe2721.lab4_5;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import msoe.swe2721.lab4_5.provided.InventoryItem;
import msoe.swe2721.lab4_5.provided.InventoryManagerInterface;
import msoe.swe2721.lab4_5.provided.Money;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateItemEntryException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidInventoryParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;

public class InventoryManager implements InventoryManagerInterface {

    private Map<Integer, InventoryItem> stock;

    InventoryManager() {
        stock = new HashMap<>();
    }

    @Override
    public void addNewItemToStore(String name, String description, int sku, double price)
            throws DuplicateItemEntryException, InvalidInventoryParameterException, InvalidSKUException {

        addNewItemToStore(name, description, sku, price, 0);
    }

    @Override
    public void addNewItemToStore(String name, String description, int sku, double price, int initialStock)
            throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        // Trim whitespace
        name.trim();
        // Check name
        if (name.length() < 2) {
            throw new InvalidInventoryParameterException("Item name must be at least 2 characters");
        }
        // Check description
        if (description.length() < 10) {
            throw new InvalidInventoryParameterException("Item description must be at least 10 characters");
        }
        // Check price
        if (price < 0 || price > 1000) {
            throw new InvalidInventoryParameterException("Price must be between $0 and $1000");
        }
        // Check Stock
        if (initialStock < 0) {
            throw new InvalidInventoryParameterException("Stock must be positive");
        }
        // Check SKU
        if (checkForSKUInSystem(sku)) {
            throw new DuplicateItemEntryException("Item of same SKU already in system");
        }

        InventoryItem item = new InventoryItem(name, description, sku, initialStock, new Money(price));
        stock.put(sku, item);
    }

    @Override
    public boolean checkForSKUInSystem(int sku) throws InvalidSKUException {
        // obtainItemBySKU already checks for valid sku
        return obtainItemBySKU(sku) != null;
    }

    @Override
    public InventoryItem obtainItemBySKU(int sku) throws InvalidSKUException {
        if (sku <= 0) {
            throw new InvalidSKUException("Invalid SKU");
        }
        for (InventoryItem inventoryItem : stock.values()) {
            if (inventoryItem.getItemNumber() == sku) {
                return inventoryItem;
            }
        }
        return null;
    }

    @Override
    public int addStock(int sku, int newStockReceived) throws InvalidSKUException, InvalidParameterException {
        if (newStockReceived < 0) {
            throw new InvalidParameterException("Stock must be positive");
        }
        // obtainItemBySKU already checks for valid sku
        InventoryItem item = obtainItemBySKU(sku);
        if (item == null) {
            throw new InvalidSKUException("Item with given sku does not exist");
        }
        item.addStock(newStockReceived);
        return item.getStockCount();
    }

    @Override
    public int returnStock(int sku, int returnedStockCount) throws InvalidSKUException, InvalidParameterException {
        // Basicaly the same method so just call the other one
        return addStock(sku, returnedStockCount);
    }

}
