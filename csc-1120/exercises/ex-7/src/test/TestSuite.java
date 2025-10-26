/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Exercise 7 - Iterator Test
 * Name: Alexander Horton
 * Updated: 3/5/2025
 */

package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import week7a.DoubleLinkedList;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TestSuite {

    private List<String> list;

    @BeforeEach
    public void setupList() {
        list = new DoubleLinkedList<>();
        list.add("foo");
        list.add("bar");
        list.add("taco");
        list.add("cat");
    }

    @Test
    public void testHasNext() {
        Iterator<String> iterator = list.iterator();
        Assertions.assertTrue(iterator.hasNext());
        while (iterator.hasNext()) {
            iterator.next();
        }
        Assertions.assertFalse(iterator.hasNext());
        list.clear();
        iterator = list.iterator();
        Assertions.assertFalse(iterator.hasNext());
    }
    @Test
    public void testNext() {
        Iterator<String> iterator = list.iterator();
        int index = 0;
        Assertions.assertEquals("foo", iterator.next());
        Assertions.assertEquals("bar", iterator.next());
        Assertions.assertEquals("taco", iterator.next());
        Assertions.assertEquals("cat", iterator.next());
        Assertions.assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
    @Test
    public void testRemove() {
        Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove();
        Assertions.assertFalse(list.contains("foo"));
        iterator.next();
        iterator.next();
        iterator.remove();
        Assertions.assertFalse(list.contains("taco"));
        Assertions.assertThrows(IllegalStateException.class, () -> iterator.remove());
        iterator.next();
        iterator.remove();
        Assertions.assertFalse(list.contains("cat"));
    }
}
