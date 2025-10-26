/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Homework 6 - Comparing Lists
 * Name: Alexander Horton
 * Updated: 2/28/25
 */

package test;

import hortona.SimpleArrayList;
import hortona.SimpleLinkedList;
import hortona.SimpleList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Basic set of unit tests for the SimpleList classes
 */
public class TestSuite {
    private final int[] numbers = {8, 6, 7, 5, 3, 0, 9, 9, 0, 1, 2, 5};
    private SimpleArrayList<Integer> array;
    private SimpleLinkedList<Integer> linked;

    @BeforeEach
    void setup() {
        array = new SimpleArrayList<>();
        linked = new SimpleLinkedList<>();
    }

    @Test
    @DisplayName("Test ArrayList Construction")
    @Order(1)
    void arrayList() throws NoSuchFieldException, IllegalAccessException {
        final int initialCapacity = 10;
        Field size = SimpleArrayList.class.getDeclaredField("size");
        Field capacity = SimpleArrayList.class.getDeclaredField("capacity");
        Field data = SimpleArrayList.class.getDeclaredField("data");
        Field accessCount = SimpleArrayList.class.getDeclaredField("accessCount");
        data.setAccessible(true);
        size.setAccessible(true);
        capacity.setAccessible(true);
        accessCount.setAccessible(true);
        Assertions.assertEquals(0, size.get(array),
                "Initial size should be 0 but is " + size.get(array));
        Assertions.assertEquals(initialCapacity, capacity.get(array),
                "Default initial capacity should be 10 but is " + capacity.get(array));
        Assertions.assertEquals(initialCapacity, ((Object[]) data.get(array)).length,
                "Backing array length should be set to the initial capacity");
        Assertions.assertEquals(0, array.getAccessCount(),
                "Initial access count should be 0 but is " + array.getAccessCount());
    }

    @Test
    @DisplayName("Testing Node class")
    @Order(2)
    void node() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        String p = SimpleLinkedList.class.getPackageName();
        Class<?> nodeClass = Class.forName(p + ".SimpleLinkedList$Node");
        Field element = nodeClass.getDeclaredField("element");
        element.setAccessible(true);
        Field next = nodeClass.getDeclaredField("next");
        next.setAccessible(true);
        Constructor<?> oneParam = nodeClass.getDeclaredConstructor(Object.class);
        oneParam.setAccessible(true);
        Constructor<?> twoParam = nodeClass.getDeclaredConstructor(Object.class, nodeClass);
        twoParam.setAccessible(true);
        Object oneParamInstance = oneParam.newInstance("Test");
        Assertions.assertEquals("Test", element.get(oneParamInstance),
                "Element should be \"Test\", but is " + element.get(oneParamInstance));
        Assertions.assertNull(next.get(oneParamInstance),
                "next should be null but is " + next.get(oneParamInstance));
        Object twoParamInstance = twoParam.newInstance("Test2", oneParamInstance);
        Assertions.assertEquals("Test2", element.get(twoParamInstance),
                "Element should be \"Test2\", but is " + element.get(twoParamInstance));
        Assertions.assertNotNull(next.get(twoParamInstance),
                "next should contain a Node but is null");
        Assertions.assertEquals(oneParamInstance, next.get(twoParamInstance),
                "Incorrect next node");
    }

    @Test
    @DisplayName("Test LinkedList Construction")
    @Order(3)
    void linkedList() throws NoSuchFieldException, IllegalAccessException {
        Field size = SimpleLinkedList.class.getDeclaredField("size");
        Field head = SimpleLinkedList.class.getDeclaredField("head");
        size.setAccessible(true);
        head.setAccessible(true);
        Assertions.assertNull(head.get(linked),
                "Initial head should be null");
        Assertions.assertEquals(0, size.get(linked),
                "Initial size should be 0 but is " + size.get(linked));
    }

    @Test
    @DisplayName("Test ArrayList add")
    @Order(4)
    void addArray() throws NoSuchFieldException, IllegalAccessException {
        final int initialCapacity = 10;
        final int reallocatedCapacity = 21;
        Field size = SimpleArrayList.class.getDeclaredField("size");
        Field data = SimpleArrayList.class.getDeclaredField("data");
        data.setAccessible(true);
        size.setAccessible(true);
        for (int i = 0; i < numbers.length; ++i) {
            Assertions.assertEquals(i, size.get(array),
                    "Size should be " + i + " but is " + size.get(array));
            array.add(numbers[i]);
            Assertions.assertEquals(i + 1, size.get(array),
                    "Size should be " + (i + 1) + " but is " + size.get(array));
            Assertions.assertEquals(numbers[i], ((Object[]) data.get(array))[i],
                    "Element at index " + i + " should be " + numbers[i] +
                            " but is " + ((Object[]) data.get(array))[i]);
            if (i == initialCapacity) {
                Assertions.assertEquals(reallocatedCapacity,
                        ((Object[]) data.get(array)).length,
                        "Backing array should have been reallocated when filled. New capacity "
                                + "should be 21 but is " + ((Object[]) data.get(array)).length);
            }
        }
    }

    @Test
    @DisplayName("Test LinkedList add")
    @Order(5)
    void linkedAdd() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Field size = SimpleLinkedList.class.getDeclaredField("size");
        Field head = SimpleLinkedList.class.getDeclaredField("head");
        size.setAccessible(true);
        head.setAccessible(true);
        String p = SimpleLinkedList.class.getPackageName();
        Class<?> node = Class.forName(p + ".SimpleLinkedList$Node");
        Field element = node.getDeclaredField("element");
        Field next = node.getDeclaredField("next");
        element.setAccessible(true);
        next.setAccessible(true);
        linked.add(numbers[0]);
        Object linkedHead = head.get(linked);
        Assertions.assertNotNull(linkedHead,
                "head should not be null after adding an element");
        Assertions.assertEquals(numbers[0], element.get(linkedHead),
                "head element incorrect");
        for (int i = 1; i < numbers.length; ++i) {
            Assertions.assertEquals(i, size.get(linked),
                    "size should be " + i + " but is " + size.get(linked));
            linked.add(numbers[i]);
            Assertions.assertEquals(i + 1, size.get(linked));
            linkedHead = next.get(linkedHead);
            Assertions.assertEquals(element.get(linkedHead), numbers[i]);
        }

    }

    /**
     * Testing size updates
     */
    @Test
    @DisplayName("Test Size")
    @Order(6)
    public void arraySize() {
        Assertions.assertEquals(0, array.size(),
                "Initial call to ArrayList size() should return 0 but returns " + array.size());
        Assertions.assertEquals(0, linked.size(),
                "Initial call to LinkedList size() should return 0 but returns " + linked.size());
        fillList(array);
        fillList(linked);
        Assertions.assertEquals(numbers.length, array.size(),
                "ArrayList should have a size of " + numbers.length + " but is " + array.size());
        Assertions.assertEquals(numbers.length, linked.size(),
                "LinkedList should have a size of " + numbers.length + " but is " + linked.size());
    }

    @Test
    @DisplayName("Test Get")
    @Order(7)
    void get() {
        fillList(array);
        fillList(linked);
        for (int i = 0; i < array.size(); ++i) {
            Assertions.assertEquals(numbers[i], array.get(i),
                    "Element at ArrayList index " + i + " should be " + numbers[i] +
                    " but is " + array.get(i));
        }
        for (int i = 0; i < linked.size(); ++i) {
            Assertions.assertEquals(numbers[i], linked.get(i),
                    "Element at LinkedList index " + i + " should be " + numbers[i] +
                            " but is " + array.get(i));
        }
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1),
                "ArrayList get() at index -1 should produce an IndexOutOfBoundsException");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> array.get(array.size()),
                "ArrayList get() at an index above size - 1 " +
                        "should produce an IndexOutOfBoundsException");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> linked.get(-1),
                "LinkedList get() at index -1 should produce an IndexOutOfBoundsException");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> linked.get(linked.size()),
                "LinkedList get() at an index above size - 1 " +
                        "should produce an IndexOutOfBoundsException");

    }


    @Test
    @DisplayName("Test Set")
    @Order(8)
    void set() {
        fillList(array);
        fillList(linked);
        for (int i = 0; i < numbers.length; ++i) {
            int oldArray = array.set(i, i);
            int oldLinked = linked.set(i, i);
            Assertions.assertEquals(oldArray, numbers[i],
                    "ArrayList set at index " + i + " should return the original value of "
                            + numbers[i] + " but returns " + array.get(i));
            Assertions.assertEquals(oldLinked, numbers[i],
                    "LinkedList set at index " + i + " should return the original value of "
                            + numbers[i] + " but returns " + linked.get(i));
            Assertions.assertEquals(i, array.get(i),
                    "ArrayList set at index " + i + " should set the value " + i + " at index "
                            + i + " but is " + array.get(i));
            Assertions.assertEquals(i, linked.get(i),
                    "LinkedList set at index " + i + " should set the value " + i + " at index "
                            + i + " but is " + linked.get(i));
        }
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, -1),
                "ArrayList set() at index -1 should produce an IndexOutOfBoundsException");
        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> array.set(numbers.length, -1),
                "ArrayList set() at an index above size - 1 " +
                        "should produce an IndexOutOfBoundsException");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> linked.set(-1, -1),
                "LinkedList set() at index -1 should produce an IndexOutOfBoundsException");
        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> linked.set(numbers.length, -1),
                "LinkedList get() at an index above size - 1 " +
                        "should produce an IndexOutOfBoundsException");
    }

    @Test
    @DisplayName("Test access count methods")
    @Order(9)
    void count() {
        fillList(array);
        fillList(linked);
        array.get(0);
        linked.get(0);
        Assertions.assertTrue(array.getAccessCount() > 0,
                "ArrayList accessCount should be incremented after a call to get()");
        Assertions.assertTrue(linked.getAccessCount() > 0,
                "LinkedList accessCount should be incremented after a call to get()");
        array.resetAccessCount();
        linked.resetAccessCount();
        Assertions.assertEquals(0, array.getAccessCount(),
                "A call to resetAccessCount for ArrayList should set the accessCount back to 0");
        Assertions.assertEquals(0, linked.getAccessCount(),
                "A call to resetAccessCount for LinkedList should set the accessCount back to 0");

    }

    @Test
    @DisplayName("Testing toString")
    @Order(10)
    void testToString() {
        fillList(array);
        String numbersToString = "[8, 6, 7, 5, 3, 0, 9, 9, 0, 1, 2, 5]";
        Assertions.assertEquals(numbersToString, array.toString(),
                "toString method returns unexpected format");
    }

    @Test
    @DisplayName("Testing accessCounts")
    @Order(11)
    void testCounts() throws IOException {
        final int expectedArrayAdd = 121;
        final int expectedLinkedAdd = 583;
        final int expectedArraySort = 1973;
        final int expectedLinkedSort = 19140;
        final double delta = 0.1;
        SimpleArrayList<Double> times1 = new SimpleArrayList<>();
        SimpleLinkedList<Double> times2 = new SimpleLinkedList<>();
        TestDriver.addTimes(times1);
        TestDriver.addTimes(times2);
        Assertions.assertEquals(expectedArrayAdd, times1.getAccessCount(),
                expectedArrayAdd * delta,
                "accessCount after adding to ArrayList outside of expected range.");
        Assertions.assertEquals(expectedLinkedAdd, times2.getAccessCount(),
                expectedLinkedAdd * delta,
                "accessCount after adding to LinkedList outside of expected range.");
        TestDriver.sort(times1);
        TestDriver.sort(times2);
        Assertions.assertEquals(expectedArraySort, times1.getAccessCount(),
                expectedArraySort * delta,
                "accessCount after sorting ArrayList outside of expected range.");
        Assertions.assertEquals(expectedLinkedSort, times2.getAccessCount(),
                expectedLinkedSort * delta,
                "accessCount after sorting LinkedList outside of expected range.");

    }

    private void fillList(SimpleList<Integer> list) {
        for (int n : numbers) {
            list.add(n);
        }
    }
}