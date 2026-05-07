package msoe.swe2721.lab4_5;

import static org.testng.Assert.assertThrows;

import java.security.InvalidParameterException;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import msoe.swe2721.lab4_5.provided.Order;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateCustomerException;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateItemEntryException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidInventoryParameterException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidSKUException;
import msoe.swe2721.lab4_5.provided.exceptions.InventoryOutOfStockException;

public class OrderManagerTest {
    private OrderManager o;
    private InventoryManager i;
    private CustomerManager c;
    private int id;
    private final int CAT_ID = 100;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws InvalidCustomerParameterException, DuplicateCustomerException,
            DuplicateItemEntryException, InvalidInventoryParameterException, InvalidSKUException {
        c = new CustomerManager();
        i = new InventoryManager();
        o = new OrderManager(c, i);

        id = c.addNewCustomer("Noah", "Dinan", 22, "123 Street", "Milwaukee", "WI", 53202);
        i.addNewItemToStore("Cat Toy", "it looks dangerous", CAT_ID, 10.00, 1);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        o = null;
    }

    @DataProvider(name = "obtainOrderDP")
    public Object[][] obtainOrderDP() {
        return new Object[][] {
                // just right
                { 1, null },
                // zero
                { 0, InvalidParameterException.class },
                // too small
                { -1, InvalidParameterException.class },
                // too big
                { 2, InvalidParameterException.class },
        };
    }

    @Test(groups = { "student" })
    public void testObtainOrderCount()
            throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException {
        assertEquals(o.obtainOrderCount(), 0);
        o.OrderItem(id, CAT_ID, 5);
        assertEquals(o.obtainOrderCount(), 1);
    }

    @Test(groups = { "student" }, dataProvider = "obtainOrderDP")
    public void testObtainOrder(int sku, Class<Exception> exception)
            throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException {
        Order order = o.OrderItem(id, CAT_ID, 5);

        if (exception != null) {
            assertThrows(InvalidParameterException.class, () -> o.obtainOrder(sku));
        } else {
            assertEquals(o.obtainOrder(sku), order);
        }
    }

    @DataProvider(name = "orderItemWithNameDP")
    public Object[][] obtainItemWithNameDP() {
        return new Object[][] {
                // sku
                { "Noah", "Dinan", -1, 1, InvalidSKUException.class },
                { "Noah", "Dinan", 0, 1, InvalidSKUException.class },
                { "Noah", "Dinan", 101, 1, InvalidSKUException.class },
                // count
                { "Noah", "Dinan", CAT_ID, -1, InvalidParameterException.class },
                { "Noah", "Dinan", CAT_ID, 100, null },
                { "Noah", "Dinan", CAT_ID, 1, null },
        };
    }

    @DataProvider(name = "orderItemWithIDDP")
    public Object[][] obtainItemWithIDDP() {
        return new Object[][] {
                // customerID
                { 1, CAT_ID, 1, InvalidCustomerParameterException.class },
                { 0, CAT_ID, 1, InvalidCustomerParameterException.class },
                { -1, CAT_ID, 1, InvalidCustomerParameterException.class },
        };
    }

    @Test(groups = { "student" }, dataProvider = "orderItemWithIDDP")
    public void testOrderItemWithID(int customerID, int sku, int count, Class<Exception> exception)
            throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException,
            DuplicateCustomerException {
        if (exception != null) {
            assertThrows(exception, () -> o.OrderItem(customerID, sku, count));
        } else {
            o.OrderItem(customerID, sku, count);
        }
    }

    @Test(groups = { "student" }, dataProvider = "orderItemWithNameDP")
    public void testOrderItemWithName(String firstName, String lastName, int sku,
            int count,
            Class<Exception> exception)
            throws InvalidCustomerParameterException, InventoryOutOfStockException, InvalidSKUException {

        if (exception != null) {
            assertThrows(exception, () -> o.OrderItem(firstName, lastName, sku, count));
        } else {
            o.OrderItem(firstName, lastName, sku, count);
        }
    }


    @Test(groups = { "student" })
    public void testOrderItemNoStock() throws InvalidInventoryParameterException, DuplicateItemEntryException, InvalidSKUException {
        i.addNewItemToStore("Cat Toy", "it looks dangerous", CAT_ID + 1, 10.00, 0);
        assertThrows(InventoryOutOfStockException.class, () -> o.OrderItem("Noah", "Dinan", CAT_ID + 1, 1));
    }
}
