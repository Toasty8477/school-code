/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 12 - Smaller Bigger Sort
 * Name: Alexander Horton
 * Updated: 4/22/2025
 */

package test;

import hortona.SmallerBiggerSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * JUnit tests for SmallerBiggerSort
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSuite {

    private final Integer[] unsorted = {6, 5, 1, 9, 7, 15, 21, 3, 16};
    private final Integer[] sorted = {1, 3, 5, 6, 7, 9, 15, 16, 21};
    private final Integer[] reverse = {21, 16, 15, 9, 7, 6, 5, 3, 1};
    private final Integer[] allButOne = {10, 10, 10, 7, 10, 10, 10};
    private final Integer[] allButOneSorted = {7, 10, 10, 10, 10, 10, 10};
    private final Integer[] smallerBiggerTest = {8, 3, 8, 5, 12, 1, 8, 18, 13};
    private final Integer[] smallerBiggerFirstRun = {8, 3, 5, 1, 8, 12, 8, 18, 13}; // 2 to 6
    private final Integer[] smallerBiggerSecondRun = {8, 3, 5, 1, 8, 8, 12, 18, 13}; // 5 to 8
    private List<Integer> list;

    @BeforeEach
    void setup() {
        list = new ArrayList<>();
    }

    @Test
    @DisplayName("Sort Random Order List")
    @Order(2)
    void testSorting() {
        list.addAll(List.of(unsorted));
        SmallerBiggerSort.sort(list);
        Assertions.assertEquals(List.of(sorted), list);
    }

    @Test
    @DisplayName("Sort Reverse Order List")
    @Order(3)
    void testReverse() {
        list.addAll(List.of(reverse));
        SmallerBiggerSort.sort(list);
        Assertions.assertEquals(List.of(sorted), list);
    }

    @Test
    @DisplayName("Sort List Of All Same Elements But One")
    @Order(4)
    void testAllButOne() {
        list.addAll(List.of(allButOne));
        SmallerBiggerSort.sort(list);
        Assertions.assertEquals(List.of(allButOneSorted), list);
    }

    @Test
    @DisplayName("Test SmallerBigger")
    @Order(1)
    void testSmallerBigger() {
        final int firstStart = 2;
        final int secondStart = 5;
        final int firstEnd = 6;
        final int secondEnd = 8;
        list.addAll(List.of(smallerBiggerTest));
        Assertions.assertEquals(4, SmallerBiggerSort.smallerBigger(list, firstStart, firstEnd));
        Assertions.assertEquals(List.of(smallerBiggerFirstRun), list);
        Assertions.assertEquals(firstEnd,
                SmallerBiggerSort.smallerBigger(list, secondStart, secondEnd));
        Assertions.assertEquals(List.of(smallerBiggerSecondRun), list);

    }

    @Test
    @DisplayName("Test True Random")
    @Order(5)
    void testTrueRandom() {
        final int listSize = 25;
        final int numRuns = 10;
        for (int i = 0; i < numRuns; i++) {
            int rand = (int) (Math.random() * listSize) + 1;
            List<Integer> random = randomize(rand);
            List<Integer> sorted = new ArrayList<>(random);
            Collections.sort(sorted);
            SmallerBiggerSort.sort(random);
            Assertions.assertEquals(sorted, random);
        }
    }

    private List<Integer> randomize(int size) {
        final int bound = 50;
        List<Integer> randomNums = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomNums.add(random.nextInt(bound));
        }
        return randomNums;
    }

}
