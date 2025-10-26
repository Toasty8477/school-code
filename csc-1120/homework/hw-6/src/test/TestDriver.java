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

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Test driver for the SimpleList interface
 */
public class TestDriver {
    public static void main() {
        SimpleArrayList<Double> times1 = new SimpleArrayList<>();
        SimpleLinkedList<Double> times2 = new SimpleLinkedList<>();
        try {
            addTimes(times1);
            addTimes(times2);
            System.out.println("After add:\nArray List: " + times1.getAccessCount() +
                    "\nLinked List: " + times2.getAccessCount());

            times1.resetAccessCount();
            times2.resetAccessCount();
            System.out.println();

            sort(times1);
            sort(times2);
            System.out.println("After sort:\nArray List: " + times1.getAccessCount() +
                    "\nLinked List: " + times2.getAccessCount());
        } catch (IOException e) {
            System.err.println("Could not load file.");
        } catch (NumberFormatException e) {
            System.err.println("Bad data in file");
        }
    }

    /**
     * Adds a file of doubles to a SimpleList
     *
     * @param list  a SimpleList
     * @throws IOException           if the file cannot be read
     * @throws NumberFormatException if a non-numeric value is encountered
     */
    public static void addTimes(SimpleList<Double> list)
            throws IOException, NumberFormatException {
        try (Scanner in = new Scanner(Paths.get("src", "test", "times.txt").toFile())) {
            String line;
            while (in.hasNextLine()) {
                line = in.nextLine();
                list.add(Double.parseDouble(line));
            }
        }
    }

    /**
     * Simple selection sort
     *
     * @param list SimpleList to sort
     */
    public static void sort(SimpleList<Double> list) {
        int length = list.size();
        for (int i = 0; i < length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (list.get(j) < list.get(min)) {
                    min = j;
                }
            }
            Double temp = list.set(min, list.get(i));
            list.set(i, temp);
        }
    }
}
