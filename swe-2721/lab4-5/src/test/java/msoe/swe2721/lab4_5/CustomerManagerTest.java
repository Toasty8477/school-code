package msoe.swe2721.lab4_5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import msoe.swe2721.lab4_5.provided.Customer;
import msoe.swe2721.lab4_5.provided.exceptions.DuplicateCustomerException;
import msoe.swe2721.lab4_5.provided.exceptions.InvalidCustomerParameterException;

public class CustomerManagerTest {
    private CustomerManager m;
    private final Class<InvalidCustomerParameterException> invalid = InvalidCustomerParameterException.class;
    private final Class<DuplicateCustomerException> duplicate = DuplicateCustomerException.class;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        m = new CustomerManager();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        m = null;
    }

    @DataProvider(name = "addNewCustomerDP")
    public Object[][] addNewCustomerDP() {
        return new Object[][] {
                // FIRST
                { "Noah", "Dinan", 100, "1234 S Street", "Milwaukee", "WI", 53202, null },
                { "No", "Dinan", 50, "1234 S Street", "Milwaukee", "WI", 53202, null },
                { "N", "Dinan", 50, "1234 S Street", "Milwaukee", "WI", 53202, invalid },
                { null, "Dinan", 50, "1234 S Street", "Milwaukee", "WI", 53202, invalid },

                // LAST
                { "Noah", "Dinan", 100, "1234 S Street", "Milwaukee", "WI", 53202, null },
                { "Noah", "Di", 50, "1234 S Street", "Milwaukee", "WI", 53202, null },
                { "Noah", "D", 50, "1234 S Street", "Milwaukee", "WI", 53202, invalid },
                { "Noah", null, 50, "1234 S Street", "Milwaukee", "WI", 53202, invalid },

                // AGE
                { "Noah", "Dinan", 200, "1234 S Street", "Milwaukee", "WI", 53202, null },
                { "Noah", "Dinan", 21, "1234 S Street", "Milwaukee", "WI", 53202, null },
                { "Noah", "Dinan", 20, "1234 S Street", "Milwaukee", "WI", 53202, invalid },

                // ADDRESS
                { "Noah", "Dinan", 22, "1234 Street", "Milwaukee", "WI", 53202, null },
                { "Noah", "Dinan", 22, "1234", "Milwaukee", "WI", 53202, invalid },
                { "Noah", "Dinan", 22, "1234 S Milwaukee St", "Milwaukee", "WI", 53202, null },
                { "Noah", "Dinan", 22, null, "Milwaukee", "WI", 53202, invalid },

                // CITY
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "WI", 53202, null },
                { "Noah", "Dinan", 22, "1234 Street", "Milwaukee", "WI", 53202, null },
                { "Noah", "Dinan", 22, "1234 Street", "Mi", "WI", 53202, invalid },
                { "Noah", "Dinan", 22, "1234 Street", null, "WI", 53202, invalid },

                // STATE
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "ab", 53202, invalid },
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "a", 53202, invalid },
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "abc", 53202, invalid },
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "WI", 53202, null },
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "XY", 53202, invalid },
                { "Noah", "Dinan", 22, "1234 Street", "Mil", null, 53202, invalid },

                // ZIP
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "WI", 53202, null },
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "WI", 532021, invalid },
                { "Noah", "Dinan", 22, "1234 Street", "Mil", "WI", 3210, invalid },
                { "Noah", "Dinan", 22, "1234 Street", "Camp Lake", "IL", 53109, invalid },
                { "Noah", "Dinan", 22, "1234 Street", "Town", "IL", 53202, invalid},
        };
    }

    @Test(dataProvider = "addNewCustomerDP", groups = { "student" })
    public void testAddNewCustomer(String first, String last, int age, String streetAddress, String city,
            String state, int zip, Class<Exception> exception)
            throws InvalidCustomerParameterException, DuplicateCustomerException {

        if (exception != null) {
            assertThrows(exception, () -> m.addNewCustomer(first, last, age, streetAddress, city, state, zip));
        } else {
            m.addNewCustomer(first, last, age, streetAddress, city, state, zip);
        }
    }

    @Test(groups = { "student" })
    public void testDuplicateCustomers() throws InvalidCustomerParameterException, DuplicateCustomerException {
        String first = "Noah";
        String last = "Noah";
        String city = "Noah";
        String state = "WI";
        int zip = 53202;
        int age = 53202;
        String streetAddress = "1234 Street";
        m.addNewCustomer(first, last, age, streetAddress, city, state, zip);
        assertThrows(duplicate, () -> m.addNewCustomer(first, last, age, streetAddress, city, state, zip));
    }

    @DataProvider(name = "findCustomerByNameDP")
    public Object[][] findCustomerByNameDP() {

        return new Object[][] {
                { "N", "Dinan", null },
                { "No", null, null },

                { "Alex", "Horton", null },
                { "Alex", "Ho", null },
                { "Alex", "H", null },
                { "Alex", null, null },
        };
    }

    @Test(dataProvider = "findCustomerByNameDP", groups = { "student" })
    public void testFindCutomerByName(String first, String last, Customer expected) {
        assertEquals(m.findCustomerByName(first, last), expected);
    }

    @Test(groups = { "student" })
    public void testFindValidCustomerByName() throws InvalidCustomerParameterException, DuplicateCustomerException {
        int id = m.addNewCustomer("Noah", "Dinan", 22, "1234 S Street St.", "Milwaukee", "WI", 53207);
        Customer n = m.findCustomerByID(id);
        assertEquals(m.findCustomerByName("Noah", "Dinan"), n);
    }

    @DataProvider(name = "findCustomerByIDDP")
    public Object[][] findCustomerByIDDP() {
        return new Object[][] {
                { 1, null, null },
                { 0, null, InvalidCustomerParameterException.class },
                { -1, null, InvalidCustomerParameterException.class },
        };
    }

    @Test(dataProvider = "findCustomerByIDDP", groups = { "student" })
    public void testFindCutomerByID(int id, Customer expected, Class<Exception> exception)
            throws InvalidCustomerParameterException {
        if (exception != null) {
            assertThrows(exception, () -> m.findCustomerByID(id));
        } else {
            assertEquals(m.findCustomerByID(id), expected);
        }
    }

    @Test(groups = { "student" })
    public void testFindValidCustomerByID() throws InvalidCustomerParameterException, DuplicateCustomerException {
        int id = m.addNewCustomer("Noah2", "Dinan2", 2000, "1234 S Street St.", "Milwaukee", "WI", 53207);
        Customer n = m.findCustomerByName("Noah2", "Dinan2");
        assertEquals(m.findCustomerByID(id), n);
    }

    @Test(groups = { "student" })
    public void testgetCustomerCount() throws InvalidCustomerParameterException, DuplicateCustomerException {
        assertEquals(m.getCustomerCount(), 0);

        m.addNewCustomer("Noah", "Dinan", 22, "1234 S Street St.", "Milwaukee", "WI", 53207);
        assertEquals(m.getCustomerCount(), 1);
    }
}
