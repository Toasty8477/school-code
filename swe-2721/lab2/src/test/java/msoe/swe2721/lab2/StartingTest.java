package msoe.swe2721.lab2;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class StartingTest {

    private CircularQueue<Integer> c;

    /**
     * This method will setup the tests to be ran later on.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            c = new CircularQueue<>(10);
        } catch (Exception ex) {
            fail();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        c = null;
    }


    /**
     * This method will verify that the size and remaining size are correct for
     * the size 10 test scenario used here.
     */
    @Test(groups = { "all", "demo" })
    public void testConstructorSize10() {
        // Arrange
        c = null;

        // Act
        c = new CircularQueue<>(10);

        // Assert
        assertEquals(c.getQueueCapacity(), 10, "Capacity incorrect.");
    }

     /**
     * This method will verify that an invalid constructor which allows invalid
     * sizes is caught.
     */
    @Test(expectedExceptions = {IllegalArgumentException.class}, groups = { "all", "demo" })
    public void testConstructorInvalidSizeNegative() throws IllegalArgumentException {
        // Arrange
        c = null;

        // Act
        c = new CircularQueue<>(-5);

        // Assert - None here, because is nothing to check.
    }

    /**
     * This is a data provider for testing the constructor.
     * @return An array of input values and expected values in the format
     * The structure for the following is the desired capacity of the queue | whether that size is valid or not (i.e. will it throw an exception)
     */
    @DataProvider(name= "testConstructorValidAndInvalidValuesDP")
    public Object[][] validInvalidConstructorTestDataProvider() {
        return new Object [][] {
                {-10, true},
                {1, false},
                {10, false},
                {0, true}
            };
    }

    /**
     * This method will test the constructor with both valid and invalid values.  It also demonstrates one usage of a data provider.
     * @param queueCapacity This is the expected queue capacity.
     * @param exceptionExpected This will be true if the given capacity is expected to throw an exception and false otherwise.
     */
    @Test(dataProvider = "testConstructorValidAndInvalidValuesDP", groups = { "all", "demo" })
    public void testConstructorValidAndInvalidValues(int queueCapacity, boolean exceptionExpected) {
        // Arrange
        c = null;

        if (!exceptionExpected) {
            // Act
            c = new CircularQueue<>(queueCapacity);

            // Assert
            assertEquals(queueCapacity, c.getQueueCapacity(), "The queue capacity is incorrect.");
        }
        else {
            // Act and Assert are combined in this case, as we should see an exception thrown.
            // This is an alternative way of testing for exceptions in testNG.
            assertThrows(IllegalArgumentException.class, () -> c=new CircularQueue<>(queueCapacity));
        }
    }

    @DataProvider(name = "testAddingDP")
    public Object[][] addingTestProvider() {
        return new Object[][] {
            {1, 0, true, true, false},
            {1, 0, true, false, false},
            {1, 9, true, true, false},
            {1, 9, true, false, false},
            {1, 10, false, true, true},
            {1, 10, false, false, false},
            {null, 0, false, true, true},
            {null, 0, false, false, true}
        };
    }
    @Test(groups = {"all", "student"}, dataProvider = "testAddingDP")
    public void testAdding(Integer toAdd, int numExtraValues, boolean expectedResult, boolean useAdd, boolean exceptionExpected) {
        // Arrange
        c = new CircularQueue<Integer>(10);
        for (int i = 0; i < numExtraValues; i++) {
            c.offer((int)Math.random() * 10);
        }
        boolean result = false;


        if (!exceptionExpected) {
            // Act
            if (useAdd && expectedResult) {
                result = c.add(toAdd);
            } else {
                result = c.offer(toAdd);
            }
            // Assert
            assertEquals(result, expectedResult);
        } else {
            // Act and Assert for exceptions
            if (useAdd && toAdd != null) { // add should throw an exception when over capacity
                assertThrows(IllegalStateException.class, () -> c.add(toAdd));
            } else {
                if (useAdd) {
                    assertThrows(NullPointerException.class, () -> c.add(toAdd));
                } else {
                    assertThrows(NullPointerException.class, () -> c.offer(toAdd));
                }
            }
        }
    }

    @DataProvider(name = "testRemovingDP")
    public Object[][] testRemovingProvider() {
        return new Object[][] {
            {4, true, false},
            {5, false, false},
            {null, true, true},
            {null, false, false}
        };
    }

    @Test(groups = {"all", "student"}, dataProvider = "testRemovingDP")
    public void testRemoving(Integer toAdd, boolean useRemove, boolean exceptionExpected) {
        // Arrange
        c = new CircularQueue<Integer>(10);
        if (toAdd != null) {
            c.add(toAdd);
        }
        Integer result;

        if (!exceptionExpected) {
            // Act
            if (useRemove) {
                result = c.remove();
            } else {
                result = c.poll();
            }

            // Assert
            if (toAdd == null) {
                assertNull(result);
            } else {
                assertEquals(result, toAdd);
            }
        } else {
            // Act and Assert
            assertThrows(NoSuchElementException.class, () -> c.remove());
        }
    }

    @DataProvider(name = "removingManyDP")
    public int[][] testRemoveManyProvider() {
        return new int[][] {
            {1, 2, 3, 4},
            {5, 10, 1, 25},
            {60, 90, 20},
            {30, 45, 90, 81, 30, 29, 60}
        };
    }

    @Test(groups = {"all", "student"}, dataProvider = "removingManyDP")
    public void testRemoveMany(int[] toAdd) {
        // Arrange
        c = new CircularQueue<Integer>(10);
        for (int i : toAdd) {
            c.offer(i);
        }

        Integer[] result = new Integer[toAdd.length];

        // Act
        for (int i = 0; i < toAdd.length; i++) {
            result[i] = c.poll();
        }

        // Assert
        assertEquals(result, toAdd);
    }

    @DataProvider(name = "peekDataProvider")
    public Object[] testPeekProvider() {
        return new Object[][] {
            {1, true},
            {5, true},
            {10, true},
            {4, true},
            {9, true},
            {0, true},
            {1, false},
            {5, false},
            {10, false},
            {4, false},
            {9, false},
            {0, false}};
    }

    @Test(groups = {"all", "student"}, dataProvider = "peekDataProvider")
    public void testPeek(int numToAdd, boolean usePeek) {
        // Arrange
        c = new CircularQueue<Integer>(10);
        Integer[] expectedResults = new Integer[numToAdd];
        Integer[] peekResults = new Integer[numToAdd];

        if (numToAdd > 0) {
            for (int i = 0; i < numToAdd; i++) {
                int toAdd = (int)Math.ceil(Math.random()*10);
                c.offer(toAdd);
                expectedResults[i] = toAdd;
            }
        } else {
            expectedResults = new Integer[1];
            expectedResults[0] = null;
            peekResults = new Integer[1];
        }


        // Act
        if (numToAdd > 0) {
            for (int i = 0; i < numToAdd; i++) {
                if (usePeek) {
                    peekResults[i] = c.peek();
                } else {
                    peekResults[i] = c.element();
                }
                c.poll();
            }
        } else if (numToAdd == 0 && !usePeek) {
            assertThrows(NoSuchElementException.class, () -> c.element());
        } else {
            peekResults[0] = c.peek();
        }

        // Assert
        assertEquals(peekResults, expectedResults);
    }

    @DataProvider(name = "capacityDP")
    public Object[][] testCapacityFunsDataProvider() {
        return new Object[][] {
            // For isEmpty
            {10, 0, true, 0, 10, false},
            {10, 5, false, 5, 5, false},
            {10, 10, false, 10, 0, true}
        };
    }

    @Test(groups = {"all", "student"}, dataProvider = "capacityDP")
    public void testCapacityFuns(int capacity, int numToAdd, boolean isEmpty, int size, int remaining, boolean isFull) {
        // Arrange
        c = new CircularQueue<>(capacity);
        for (int i = 0; i < numToAdd; i++) {
            c.offer((int)Math.random()*10);
        }

        // Act
        boolean isEmptyResult = c.isEmpty();
        int sizeResult = c.size();
        int capacityResult = c.getQueueCapacity();
        int remainingResult = c.getRemainingQueueSpace();
        boolean isFullResult = c.isQueueFull();

        // Assert
        assertEquals(isEmptyResult, isEmpty);
        assertEquals(sizeResult, size);
        assertEquals(capacityResult, capacity);
        assertEquals(remainingResult, remaining);
        assertEquals(isFullResult, isFull);
    }

    @DataProvider(name = "toArrayDP")
    public Object[] toArrayDataProvider() {
        return new Object[][] {
            {1, 2, 3, 4},
            {1, 500, 20, 70},
            {30, 20, 90, 1000},
            {40, 1, 4, 3}
        };
    }

    @Test(groups = {"all", "student"}, dataProvider = "toArrayDP")
    public void testToArray(Integer[] expected) {
        // Arrange
        c = new CircularQueue<Integer>(10);
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != null) {
                c.offer(expected[i]);
            }
        }
        Object[] toArrayResult;
        Integer[] toArrayResultsInt = new Integer[expected.length];

        // Act
        toArrayResult = c.toArray();
        for (int i = 0; i < toArrayResult.length; i++) {
            if (toArrayResult[i] != null) {
                toArrayResultsInt[i] = (Integer)toArrayResult[i];
            }
        }

        // Assert
        assertEquals(toArrayResultsInt, expected);
    }

    @DataProvider(name = "containsDP")
    public Object[] containsDataProvider() {
        return new Object[][] {
            {4, 4, true},
            {5, 4, false}
        };
    }

    @Test(groups = {"all", "student"}, dataProvider = "containsDP")
    public void testContains(int numberToAdd, int numberToTest, boolean expectedResult) {
        // Arrange
        c = new CircularQueue<Integer>(10);
        c.offer(numberToAdd);
        boolean result;

        // Act
        result = c.contains(numberToTest);

        // Assert
        assertEquals(result, expectedResult);

    }

    @Test(groups = {"all", "student"})
    public void testUnsupported() {
        c = new CircularQueue<>(10);

        assertThrows(UnsupportedOperationException.class, () -> c.toArray(new Integer[5]));
        assertThrows(UnsupportedOperationException.class, () -> c.iterator());
        assertThrows(UnsupportedOperationException.class, () -> c.remove(new Object()));
        assertThrows(UnsupportedOperationException.class, () -> c.removeAll(new ArrayList<Object>()));
        assertThrows(UnsupportedOperationException.class, () -> c.retainAll(new ArrayList<Object>()));
        assertThrows(UnsupportedOperationException.class, () -> c.containsAll(new ArrayList<Object>()));
        assertThrows(UnsupportedOperationException.class, () -> c.addAll(new ArrayList<Integer>()));
    }
}