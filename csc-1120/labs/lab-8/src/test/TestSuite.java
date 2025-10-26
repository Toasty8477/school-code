/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 6 - Autocompleter
 * Name: Alexander Horton
 * Updated: 3/4/2025
 */

package test;

import hortona.AutoCompleter;
import hortona.OrderedList;
import hortona.UnorderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.LinkedList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestSuite {
    private AutoCompleter arrayList;
    private AutoCompleter linkedList;

    @BeforeEach
    public void setup() {
        arrayList = new UnorderedList(new ArrayList<>());
        linkedList = new UnorderedList(new LinkedList<>());
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
        Assertions.assertEquals(0, arrayList.size());
        arrayList.add("foo");
        arrayList.add("bar");
        arrayList.add("taco");
        Assertions.assertEquals(3, arrayList.size());
        arrayList.add("cat");
        arrayList.add("foobar");
        arrayList.add("food");
        arrayList.add("town");
        Assertions.assertEquals(finalListSize, arrayList.size());
    }
    @Test
    @Order(1)
    public void testAdd() {
        // Add items to the list
        Assertions.assertTrue(arrayList.add("foo"));
        Assertions.assertTrue(arrayList.add("bar"));
        Assertions.assertTrue(arrayList.add("taco"));
        // Check for correct size
        Assertions.assertEquals(3, arrayList.size());
        // Try and add a duplicate
        Assertions.assertFalse(arrayList.add("foo"));
        Assertions.assertFalse(arrayList.add("Foo"));
        Assertions.assertFalse(arrayList.add("fOo"));
        Assertions.assertFalse(arrayList.add("foO"));
        // Try and add null or empty string
        Assertions.assertThrows(IllegalArgumentException.class, () -> arrayList.add(null));
        Assertions.assertThrows(IllegalArgumentException.class, () ->arrayList.add(""));
    }
    @Test
    @Order(2)
    public void testExactMatch() {
        // Add items to the list
        arrayList.add("foo");
        arrayList.add("bar");
        arrayList.add("taco");
        arrayList.add("cat");
        // Check for a target
        Assertions.assertTrue(arrayList.exactMatch("bar"));
        Assertions.assertTrue(arrayList.exactMatch("cat"));
        // Check for null or empty
        Assertions.assertFalse(arrayList.exactMatch(null));
        Assertions.assertFalse(arrayList.exactMatch(""));
    }
    @Test
    @Order(3)
    public void testAllMatches() {
        final String[] matchTest1 = {"foo", "foobar", "food"};
        final String[] matchTest2 = {"taco", "town"};
        final String[] emptyMatches = {};
        // Add items to the list
        arrayList.add("foo");
        arrayList.add("bar");
        arrayList.add("taco");
        arrayList.add("cat");
        arrayList.add("foobar");
        arrayList.add("food");
        arrayList.add("town");
        // Check for matches
        Assertions.assertArrayEquals(matchTest1, arrayList.allMatches("fo"));
        Assertions.assertArrayEquals(matchTest1, arrayList.allMatches("foo"));
        Assertions.assertArrayEquals(matchTest2, arrayList.allMatches("t"));
        Assertions.assertArrayEquals(emptyMatches, arrayList.allMatches(null));
    }
    @Test
    @Order(4)
    public void testBackingStructure() {
        Assertions.assertEquals("java.util.ArrayList", arrayList.getBackingClass());
    }
}

/*
  Discussion: What method did you find most difficult to test? Why?

Test all matches returns an Array and I was unsure what method to use

*/