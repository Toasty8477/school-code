/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 6 - Autocompleter
 * Name: Alexander Horton
 * Updated: 3/4/2025
 */

package test;

import hortona.AutoCompleter;
import hortona.UnorderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestSuite {
    private UnorderedList list;

    @BeforeEach
    public void setup() {
        list = new UnorderedList(new ArrayList<>());
    }

    @Test
    public void testFormat() {
        final long formatTest1 = 106_320_000_000_000L;
        final long formatTest2 = 50_468_000_000_000L;
        final long formatTest3 = 2_575_300_000_000L;
        final long formatTest4 = 18_800_000_000L;
        final long formatTest5 = 998_800_000;
        final long formatTest6 = 318_800;
        final long formatTest7 = 7;
        Assertions.assertEquals("1 Day, 5 Hours, 32 Minutes", AutoCompleter.format(formatTest1));
        Assertions.assertEquals("14 Hours, 1 Minute, 8 Seconds", AutoCompleter.format(formatTest2));
        Assertions.assertEquals("42 Minutes, 55.3 Seconds", AutoCompleter.format(formatTest3));
        Assertions.assertEquals("18.8 Seconds", AutoCompleter.format(formatTest4));
        Assertions.assertEquals("998.8 Milliseconds", AutoCompleter.format(formatTest5));
        Assertions.assertEquals("318.8 Microseconds", AutoCompleter.format(formatTest6));
        Assertions.assertEquals("7 Nanoseconds", AutoCompleter.format(formatTest7));

    }
    @Test
    @Order(1)
    public void testSize() {
        final int finalListSize = 7;
        Assertions.assertEquals(0, list.size());
        list.add("foo");
        list.add("bar");
        list.add("taco");
        Assertions.assertEquals(3, list.size());
        list.add("cat");
        list.add("foobar");
        list.add("food");
        list.add("town");
        Assertions.assertEquals(finalListSize, list.size());
    }
    @Test
    @Order(1)
    public void testAdd() {
        // Add items to the list
        Assertions.assertTrue(list.add("foo"));
        Assertions.assertTrue(list.add("bar"));
        Assertions.assertTrue(list.add("taco"));
        // Check for correct size
        Assertions.assertEquals(3, list.size());
        // Try and add a duplicate
        Assertions.assertFalse(list.add("foo"));
        Assertions.assertFalse(list.add("Foo"));
        Assertions.assertFalse(list.add("fOo"));
        Assertions.assertFalse(list.add("foO"));
        // Try and add null or empty string
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.add(null));
        Assertions.assertThrows(IllegalArgumentException.class, () ->list.add(""));
    }
    @Test
    @Order(2)
    public void testExactMatch() {
        // Add items to the list
        list.add("foo");
        list.add("bar");
        list.add("taco");
        list.add("cat");
        // Check for a target
        Assertions.assertTrue(list.exactMatch("bar"));
        Assertions.assertTrue(list.exactMatch("cat"));
        // Check for null or empty
        Assertions.assertFalse(list.exactMatch(null));
        Assertions.assertFalse(list.exactMatch(""));
    }
    @Test
    @Order(3)
    public void testAllMatches() {
        final String[] matchTest1 = {"foo", "foobar", "food"};
        final String[] matchTest2 = {"taco", "town"};
        final String[] emptyMatches = {};
        // Add items to the list
        list.add("foo");
        list.add("bar");
        list.add("taco");
        list.add("cat");
        list.add("foobar");
        list.add("food");
        list.add("town");
        // Check for matches
        Assertions.assertArrayEquals(matchTest1, list.allMatches("fo"));
        Assertions.assertArrayEquals(matchTest1, list.allMatches("foo"));
        Assertions.assertArrayEquals(matchTest2, list.allMatches("t"));
        Assertions.assertArrayEquals(emptyMatches, list.allMatches(null));
    }
    @Test
    @Order(4)
    public void testBackingStructure() {
        Assertions.assertEquals("java.util.ArrayList", list.getBackingClass());
    }
}

/*
  Discussion: What method did you find most difficult to test? Why?

Test all matches returns an Array and I was unsure what method to use

*/
