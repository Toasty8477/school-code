/*
 * Course: CSC-1120
 * Abstract Heap tests
 */
package test;

import java.util.StringJoiner;

import hortona.AbstractHeap;
import hortona.MaxHeap;
import hortona.MinHeap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for Heaps
 */
public class TestSuite {
    private final String input = "A Z B Y C X D W E";
    
    @Test
    @DisplayName("Testing MinHeap")
    void testMinHeap() {
        final String inOrder = "A B C D E W X Y Z";
        AbstractHeap<String> heap = new MinHeap<>();
        String[] letters = input.split(" ");
        for (String letter : letters) {
            heap.offer(letter);
        }
        StringJoiner sj = new StringJoiner(" ");
        while (!heap.isEmpty()) {
            sj.add(heap.poll());
        }
        Assertions.assertEquals(inOrder, sj.toString());
    }
    
    @Test
    @DisplayName("Testing MaxHeap")
    void testMaxHeap() {
        final String reverse = "Z Y X W E D C B A";
        AbstractHeap<String> heap = new MaxHeap<>();
        String[] letters = input.split(" ");
        for (String letter : letters) {
            heap.offer(letter);
        }
        StringJoiner sj = new StringJoiner(" ");
        while (!heap.isEmpty()) {
            sj.add(heap.poll());
        }
        Assertions.assertEquals(reverse, sj.toString());
    }

    @Test
    @DisplayName("Testing Heaps")
    void testHeaps() {
        AbstractHeap<String> heap = new MaxHeap<>();
        Assertions.assertThrows(NullPointerException.class, () ->heap.offer(null));
        Assertions.assertEquals(0, heap.size());
        Assertions.assertTrue(heap.isEmpty());
        Assertions.assertTrue(heap.offer("Hello"));
        Assertions.assertEquals(1, heap.size());
        Assertions.assertFalse(heap.isEmpty());
    }
}
