/*
 * Course: CSC1120
 * Homework 5: SJArrayList
 * Name: TODO
 * Last Updated: TODO
 */
package test;

import hortona.SJArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * Simple test suite that runs bare-bones validation tests on the
 * assigned methods
 */
public class TestSuite {
    private SJArrayList<Integer> list;
    private final Integer[] array = {1, 7, 6, 4, 9, 3, 6, 3, 0, 2, 5, 9};

    @BeforeEach
    void setUp() {
        list = new SJArrayList<>();
        Collections.addAll(list, array);
    }

    @Test
    @DisplayName("Testing indexOf")
    @Order(1)
    void indexOf() {
        final Integer[] expected = {8, 0, 9, 5, 3, 10, 2, 1, -1, 4};
        Assertions.assertEquals(-1, list.indexOf(null),
                "If a value does not exist in the List, -1 should be returned");
        for(int i = 0; i < expected.length; ++i) {
            Assertions.assertEquals(expected[i], list.indexOf(i),
                    "Expected index " + i + " for value "
                            + expected[i] + " but returned " + list.indexOf(i));
        }
    }

    @Test
    @DisplayName("Testing middleToEnd on a List of even length")
    @Order(2)
    void middleToEndEven() {
        list.middleToEnd();
        final Integer[] expected = {1, 7, 6, 4, 9, 3, 3, 0, 2, 5, 9, 6};
        for(int i = 0; i < expected.length; ++i) {
            Assertions.assertEquals(expected[i], list.get(i),
                    "Expected value " + expected[i] + " but returned " + list.get(i));
        }
    }

    @Test
    @DisplayName("Testing middleToEnd on a List of odd length")
    @Order(3)
    void middleToEndOdd() {
        list.removeFirst();
        list.middleToEnd();
        final Integer[] expected = {7, 6, 4, 9, 3, 3, 0, 2, 5, 9, 6};
        for(int i = 0; i < expected.length; ++i) {
            Assertions.assertEquals(expected[i], list.get(i),
                    "Expected value " + expected[i] + " but returned " + list.get(i));
        }
    }

    @Test
    @DisplayName("Testing toArray")
    @Order(4)
    void toArray() {
        Object[] array = list.toArray();
        Assertions.assertEquals(list.size(), array.length,
                "List should have as size " + array.length + " but was " + list.size());
        for(int i = 0; i < array.length; ++i) {
            Assertions.assertEquals(array[i], list.get(i),
                    "Value at index " + i + " should be " + array[i] + " but is " + list.get(i));
        }
    }
}
