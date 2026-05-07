package msoe.swe2721.lab4_5;

import static org.testng.Assert.assertThrows;

import java.security.InvalidParameterException;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import msoe.swe2721.lab4_5.provided.InventoryItem;
import msoe.swe2721.lab4_5.provided.Money;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateCustomerException;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateItemEntryException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidInventoryParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;

public class InventoryManagerTest {
    private InventoryManager i;

    private Class<InvalidInventoryParameterException> invalid_inventory_except = InvalidInventoryParameterException.class;
    private Class<DuplicateItemEntryException> duplicate_item_except = DuplicateItemEntryException.class;
    private Class<InvalidSKUException> sku_except = InvalidSKUException.class;
    private Class<InvalidParameterException> invalid_param = InvalidParameterException.class;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        i = new InventoryManager();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        i = null;
    }

    @DataProvider(name = "addNewItemToStoreDP")
    public Object[][] addNewItemToStoreDP() {
        return new Object[][] {
                // name
                { "L", "A Sticky Desk Lamp", 1099829822, 1000, 20, invalid_inventory_except },
                { "La", "A Sticky Desk Lamp", 1099829822, 1000, 20, null },
                { "Lamp", "A Sticky Desk Lamp", 1099829822, 1000, 20, null },

                // description
                { "Lamp", "A", 1099829822, 1000, 20, invalid_inventory_except },
                { "Lamp", "AL", 1099829822, 1000, 20, invalid_inventory_except },
                { "Lamp", "A Sticky Desk Lamp", 1099829822, 1000, 20, null },

                // sku
                { "Lamp", "A Sticky Desk Lamp", 1, 1000, 20, null },
                { "Lamp", "A Sticky Desk Lamp", 0, 1000, 20, sku_except },
                { "Lamp", "A Sticky Desk Lamp", -1, 1000, 20, sku_except },
                { "Lamp", "A Sticky Desk Lamp", 111, 1000, 20, null },

                // price
                { "Lamp", "A Sticky Desk Lamp", 111, 1, 20, null },
                { "Lamp", "A Sticky Desk Lamp", 111, 0, 20, null },
                { "Lamp", "A Sticky Desk Lamp", 111, -1, 20, invalid_inventory_except },
                { "Lamp", "A Sticky Desk Lamp", 111, 1001, 20, invalid_inventory_except },

                // stock
                { "Lamp", "A Sticky Desk Lamp", 111, 100, 0, null },
                { "Lamp", "A Sticky Desk Lamp", 111, 100, 2, null },
                { "Lamp", "A Sticky Desk Lamp", 111, 100, -2, invalid_inventory_except },
        };
    }

    @Test(dataProvider = "addNewItemToStoreDP", groups = { "student" })
    public void testAddNewItemToStore(String name, String description, int sku, int price, int initialStock,
            Class<Exception> exception) throws InvalidCustomerParameterException, DuplicateCustomerException,
            InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {

        if (exception != null) {
            assertThrows(exception, () -> i.addNewItemToStore(name, description, sku, price, initialStock));
        } else {
            i.addNewItemToStore(name, description, sku, price, initialStock);
        }
    }

    @Test(groups = { "student" })
    public void testAddDuplicateItemToStore()
            throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        i.addNewItemToStore("thing", "this is a thing", 1, 100);
        assertThrows(duplicate_item_except, () -> i.addNewItemToStore("thing", "this is a duplicate thing", 1, 100));
    }

    @Test(groups = { "student" })
    public void testAddNewItemToStoreWithoutInitialStock()
            throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        i.addNewItemToStore("thing", "this is a thing", 1, 100);
    }

    @DataProvider(name = "skuDP")
    public Object[][] skuDP() {
        return new Object[][] {
                { -1, sku_except, 0 },
                { 0, sku_except, 0 },
                { 1, null, 0 },
        };
    }

    @Test(dataProvider = "skuDP", groups = { "student" })
    public void testCheckForSKUInSystem(int sku, Class<Exception> exception, int returnValue)
            throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        if (exception != null) {
            assertThrows(exception, () -> i.checkForSKUInSystem(sku));
        } else {
            i.checkForSKUInSystem(sku);
        }
    }

    @Test(groups = { "student" })
    public void testCheckForSKUActuallyInSystem()
            throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        i.addNewItemToStore("thing", "this is a thing", 10089, 100);
        assertEquals(i.checkForSKUInSystem(10089), true);
    }

    @Test(groups = { "student" })
    public void testObtainItemBySKU()
            throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        i.addNewItemToStore("Cat", "A fluffy beast", 19087, 1);

        InventoryItem item = new InventoryItem("Cat", "beast", 19087, 1, new Money(1.0));
        assertEquals(i.obtainItemBySKU(19087).getItemNumber(), item.getItemNumber());
        assertEquals(i.obtainItemBySKU(19088), null);

        assertThrows(sku_except, () -> i.obtainItemBySKU(-1));
        assertThrows(sku_except, () -> i.obtainItemBySKU(0));
    }

    @DataProvider(name = "skuDP2")
    public Object[][] skuDP2() {
        return new Object[][] {
                { -1, sku_except, 0 },
                { 0, sku_except, 0 },
                { 1, sku_except, 0 },
                { 1, sku_except, 1 },
                { 1, invalid_param, -1 },
        };
    }

    @Test(dataProvider = "skuDP2", groups = { "student" })
    public void testReturnStock(int sku, Class<Exception> exception, int returnedStock)
            throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        if (exception != null) {
            assertThrows(exception, () -> i.returnStock(sku, returnedStock));
        } else {
            i.returnStock(sku, returnedStock);
        }
    }


    @Test(dataProvider = "skuDP2", groups = { "student" })
    public void testAddStock(int sku, Class<Exception> exception, int newStock)
            throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        if (exception != null) {
            assertThrows(exception, () -> i.addStock(sku, newStock));
        } else {
            i.addStock(sku, newStock);
        }
    }
}
