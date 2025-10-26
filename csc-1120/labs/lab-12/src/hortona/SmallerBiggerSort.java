/*
 * Course: CSC-1120
 * Smaller Bigger Sort
 * Name: Alexander Horton
 * Updated: 4/22/2025
 */

package hortona;

import java.util.ArrayList;
import java.util.List;

/**
 * Sort designed by Dr. Taylor
 */
public class SmallerBiggerSort {

    /**
     * Sorts smaller elements to the left of the starting index and larger to the right
     * @param list any list
     * @param startInclusive place in the list to start
     * @param endExclusive place in the list to end
     * @param <T> List type
     * @return new index of the starting element
     */
    public static <T extends Comparable<T>> int smallerBigger(
            List<T> list, int startInclusive, int endExclusive) {
        int smaller = 0;
        ArrayList<T> smallerItems = new ArrayList<>();
        ArrayList<T> largerItems = new ArrayList<>();
        for (int i = startInclusive + 1; i < endExclusive; i++) {
            if (list.get(startInclusive).compareTo(list.get(i)) > 0) {
                smaller++;
                smallerItems.add(list.get(i));
            } else {
                largerItems.add(list.get(i));
            }
        }

        ArrayList<T> newList = new ArrayList<>();

        for (int i = 0; i < startInclusive; i++) {
            newList.add(list.get(i));
        }
        newList.addAll(smallerItems);
        newList.add(list.get(startInclusive));
        newList.addAll(largerItems);
        for (int i = endExclusive; i < list.size(); i++) {
            newList.add(list.get(i));
        }

        for (int i = 0; i < newList.size(); i++) {
            list.set(i, newList.get(i));
        }

        return smaller+startInclusive;
    }

    /**
     * Recursive version of SmallerBigger
     * @param list any list
     * @param startInclusive index to start at
     * @param endExclusive index to end before
     * @param <T> type
     */
    public static <T extends Comparable<T>> void sort(
            List<T> list, int startInclusive, int endExclusive) {

        if (endExclusive - startInclusive > 1) {
            ArrayList<T> smallerItems = new ArrayList<>();
            ArrayList<T> largerItems = new ArrayList<>();
            for (int i = startInclusive + 1; i < endExclusive; i++) {
                if (list.get(startInclusive).compareTo(list.get(i)) > 0) {
                    smallerItems.add(list.get(i));
                } else {
                    largerItems.add(list.get(i));
                }
            }

            ArrayList<T> newList = new ArrayList<>();

            for (int i = 0; i < startInclusive; i++) {
                newList.add(list.get(i));
            }
            newList.addAll(smallerItems);
            newList.add(list.get(startInclusive));
            newList.addAll(largerItems);
            for (int i = endExclusive; i < list.size(); i++) {
                newList.add(list.get(i));
            }

            for (int i = 0; i < newList.size(); i++) {
                list.set(i, newList.get(i));
            }

            sort(list, startInclusive, smallerItems.size() + startInclusive);
            sort(list, smallerItems.size() + startInclusive + 1, endExclusive);
        }
    }

    /**
     * Starts recursive sort method
     * @param list any list
     * @param <T> type
     */
    public static <T extends Comparable<T>> void sort(List<T> list) {
        sort(list, 0, list.size());
    }

}
