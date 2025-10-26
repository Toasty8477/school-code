/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Homework 7 - Queues
 * Name: Alexander Horton
 * Updated: 3/7/2025
 */

package test;

import hortona.PriorityQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import hortona.SJQueue;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Test quite for the SJQueue and PriorityQueue classes
 */
@SuppressWarnings("All")
public class TestSuite {
    SJQueue<String> queue;
    PriorityQueue<Integer> priorityQueue;
    String[] array = {"Never", "put", "off", "until", "tomorrow", "that", "which", "you", "can",
            "make", "someone", "else", "do"};
    Integer[] integers = {5, -3, 42, -8, 37, 19, -27, -15, 12, -50};
    Integer[] integersAfterAdds = {-50, -27, -15, -8, -3, 5, 5, 5, 5, 5, 5, 12, 19, 37, 42};
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        queue = new SJQueue<>();
        priorityQueue = new PriorityQueue<>();
    }

    @Test
    void size() {
        final int expectedSize = 13;
        Assertions.assertEquals(0, queue.size());
        Collections.addAll(queue, array);
        Assertions.assertEquals(expectedSize, queue.size());
    }

    @Test
    void queueAdd() {
        Assertions.assertThrows(NullPointerException.class, () -> queue.add(null));
        int size = 0;
        for(String s : array) {
            Assertions.assertTrue(queue.add(s));
            Assertions.assertEquals(++size, queue.size());
        }
    }

    @Test
    void queueOffer() {
        Assertions.assertThrows(NullPointerException.class, () -> queue.offer(null));
        int size = 0;
        for(String s : array) {
            Assertions.assertTrue(queue.offer(s));
            Assertions.assertEquals(++size, queue.size());
        }
    }

    @Test
    void remove() {
        Assertions.assertThrows(NoSuchElementException.class, () -> queue.remove());
        fillQueue();
        int size = queue.size();
        int currentSize = size;
        for(int i = 0; i < size; ++i) {
            Assertions.assertEquals(array[i], queue.remove());
            Assertions.assertEquals(--currentSize, queue.size());
        }
    }

    @Test
    void poll() {
        Assertions.assertNull(queue.poll());
        fillQueue();
        int size = queue.size();
        int currentSize = size;
        for(int i = 0; i < size; ++i) {
            Assertions.assertEquals(array[i], queue.poll());
            Assertions.assertEquals(--currentSize, queue.size());
        }
    }

    @Test
    void element() {
        Assertions.assertThrows(NoSuchElementException.class, () -> queue.element());
        fillQueue();
        int size = queue.size();
        for(int i = 0; i < size; ++i) {
            Assertions.assertEquals(array[i], queue.element());
            queue.poll();
        }
    }

    @Test
    void peek() {
        Assertions.assertNull(queue.peek());
        fillQueue();
        int size = queue.size();
        for(int i = 0; i < size; ++i) {
            Assertions.assertEquals(array[i], queue.peek());
            queue.poll();
        }
    }

    @Test
    void isEmpty() {
        Assertions.assertTrue(queue.isEmpty());
        queue.offer("Hello");
        Assertions.assertFalse(queue.isEmpty());
    }

    @Test
    void contains() {
        Assertions.assertThrows(NullPointerException.class, () -> queue.contains(null));
        fillQueue();
        for(String s : array) {
            Assertions.assertTrue(queue.contains(s));
        }
        Assertions.assertFalse(queue.contains("Hello!"));
    }

    @Test
    void iterator() {
        Assertions.assertTrue(queue.iterator() instanceof Iterator);
    }

    @Test
    void clear() {
        fillQueue();
        Assertions.assertNotEquals(0, queue.size());
        queue.clear();
        Assertions.assertEquals(0, queue.size());
    }

    @Test
    void toArray() {
        fillQueue();
        Object[] result = queue.toArray();
        for(int i = 0; i < result.length; ++i) {
            Assertions.assertEquals(result[i], array[i]);
        }
    }

    @Test
    void priorityAdd() {
        // Check null
        Assertions.assertThrows(NullPointerException.class, () -> priorityQueue.add(null));
        // Add items
        Assertions.assertTrue(priorityQueue.add(1));
        Assertions.assertTrue(priorityQueue.add(2));
        // Check for priority
        Assertions.assertEquals(1, priorityQueue.peek());
        // Add some more items
        priorityQueue.add(-1);
        priorityQueue.add(5);
        priorityQueue.add(3);
        Assertions.assertTrue(priorityQueue.add(4));
        // Check for priority again
        priorityQueue.poll();
        priorityQueue.poll();
        priorityQueue.poll();
        priorityQueue.poll();
        Assertions.assertEquals(4, priorityQueue.peek());
        priorityQueue.poll();
        Assertions.assertEquals(5, priorityQueue.poll());
        // Refill queue
        fillPriorityQueue();
        // Add a duplicate
        Assertions.assertTrue(priorityQueue.add(5));
        Assertions.assertTrue(priorityQueue.add(5));
        priorityQueue.add(5);
        priorityQueue.add(5);
        Assertions.assertTrue(priorityQueue.add(5));
        // Check order
        for (int i = 0; i < priorityQueue.size(); i++) {
            Assertions.assertEquals(integersAfterAdds[i], priorityQueue.poll());
        }
    }

    @Test
    void priorityOffer() {
        // Check null
        Assertions.assertThrows(NullPointerException.class, () -> priorityQueue.offer(null));
        // Add items
        Assertions.assertTrue(priorityQueue.offer(1));
        Assertions.assertTrue(priorityQueue.offer(2));
        // Check for priority
        Assertions.assertEquals(1, priorityQueue.peek());
        // Add some more items
        priorityQueue.offer(-1);
        priorityQueue.offer(5);
        priorityQueue.offer(3);
        Assertions.assertTrue(priorityQueue.offer(4));
        // Check for priority again
        priorityQueue.poll();
        priorityQueue.poll();
        priorityQueue.poll();
        priorityQueue.poll();
        Assertions.assertEquals(4, priorityQueue.peek());
        priorityQueue.poll();
        Assertions.assertEquals(5, priorityQueue.poll());
        // Refill queue
        fillPriorityQueue();
        // Add a duplicate
        Assertions.assertTrue(priorityQueue.offer(5));
        Assertions.assertTrue(priorityQueue.offer(5));
        priorityQueue.offer(5);
        priorityQueue.offer(5);
        Assertions.assertTrue(priorityQueue.offer(5));
        // Check order
        for (int i = 0; i < priorityQueue.size(); i++) {
            Assertions.assertEquals(integersAfterAdds[i], priorityQueue.poll());
        }
    }

    private void fillQueue() {
        for (String s : array) {
            queue.offer(s);
        }
    }

    private void fillPriorityQueue() {
        for (Integer i : integers) {
            priorityQueue.offer(i);
        }
    }
}
