/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Homework 6 - Comparing Lists
 * Name: Alexander Horton
 * Updated: 2/28/25
 */

package hortona;

/**
 * Simplified implementation of the list interface
 * @param <E> Type of list
 */
public interface SimpleList<E> {
    /**
     * Size of the list
     * @return size of the list
     */
    int size();

    /**
     * Add to the list
     * @param e element to add
     */
    void add(E e);

    /**
     * Get the element at an index
     * @param index index to retrieve from
     * @return retrieved element
     */
    E get(int index);

    /**
     * Set the element at a given index
     * @param index index to set
     * @param e element to set at index
     * @return previous value of that index
     */
    E set(int index, E e);
}
