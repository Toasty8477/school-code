/*
 * Course: CSC-1120
 */
package username;

/**
 * Sort algorithm interface
 */
public interface SortAlgorithm {
    /**
     * Generic sort method that requires a Comparable
     * @param table the array to sort
     * @param <T> the Comparable elements in the array
     */
    <T extends Comparable<? super T>> void sort(T[] table);
}
