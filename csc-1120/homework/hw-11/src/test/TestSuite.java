/*
 * Course: CSC-1120
 * HashMap Homework
 * Name: Alexander Horton
 * Last Updated: 04/11/2025
 */
package test;

import hortona.SJHashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Assertions;

/**
 * Suite of JUnit tests for the SJHashMap class
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSuite {
    private static final int[] KEYS = {4, 14, 24, 5, 15, 25, 3, 13, 23, 49};
    private static final String[] VALUES = {"apple", "jacks", "cocoa", "puffs", "hungry?",
            "SUGAR!!!!!!!", "oatmeal", "trix", "granola", "eggs"};
    private static final String OUTPUT1 = "{23=granola, 3=oatmeal, 4=apple, 24=cocoa, 5=puffs, " +
            "25=SUGAR!!!!!!!, 49=eggs, 13=trix, 14=jacks, 15=hungry?}";
    private static final String OUTPUT2 = "{23=granola, 3=oatmeal, 4=apple, 24=cocoa, 5=puffs, " +
            "25=SUGAR!!!!!!!, 49=eggs, null, 14=jacks, 15=hungry?}";
    private SJHashMap<Integer, String> map;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws NoSuchFieldException {
        map = new SJHashMap<>();
    }

    @Test
    @DisplayName("Test containsKey")
    void containsKey() {
        fillTable();
        for(Integer key : KEYS) {
            Assertions.assertTrue(map.containsKey(key));
        }
        Assertions.assertFalse(map.containsKey(0));
    }

    @Test
    @DisplayName("Test containsValue")
    void containsValue() {
        fillTable();
        for(String value : VALUES) {
            Assertions.assertTrue(map.containsValue(value));
        }
        Assertions.assertFalse(map.containsValue("Hello!"));
    }

    @Test
    @DisplayName("Test get")
    void get() {
        fillTable();
        for(int i = 0; i < KEYS.length; ++i) {
            Assertions.assertEquals(map.get(KEYS[i]), VALUES[i]);
        }
        Assertions.assertNull(map.get(0));
    }

    @Test
    @DisplayName("Test put")
    void put() {
        for(int i = 0; i < KEYS.length; ++i) {
            String s = map.put(KEYS[i], VALUES[i]);
            Assertions.assertTrue(map.containsKey(KEYS[i]));
            Assertions.assertTrue(map.containsValue(VALUES[i]));
            //Assertions.assertNull(s);
        }
        String s = map.put(4, "pear");
        Assertions.assertEquals("apple", s);
    }

    @Test
    @DisplayName("Test remove")
    void remove() {
        fillTable();
        int size = map.size();
        Assertions.assertNull(map.remove(0));
        String s = map.remove(4);
        Assertions.assertEquals(size - 1, map.size());
        Assertions.assertEquals("apple", s);
        Assertions.assertNull(map.get(4));
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        fillTable();
        Assertions.assertEquals(OUTPUT1, map.toString());
        final int keyToRemove = 13;
        map.remove(keyToRemove);
        Assertions.assertEquals(OUTPUT2, map.toString());
    }

    @Test
    @DisplayName("Test probing count")
    void probingCount() {
        fillTable();
        final double expectedNumProbes = 26.0;
        final int numFinds = 18;
        Assertions.assertEquals(expectedNumProbes / numFinds, map.averageProbes());
    }

    /**
     * Fills the table with the starting values
     */
    private void fillTable() {
        for(int i = 0; i < KEYS.length; ++i) {
            map.put(KEYS[i], VALUES[i]);
        }
    }
}
