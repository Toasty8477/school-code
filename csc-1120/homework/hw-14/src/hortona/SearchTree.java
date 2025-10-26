/*
 * Course: CSC-1120
 * Homework 14: Self-Balancing Trees
 * Name: Alexander Horton
 */

package hortona;

import java.util.List;

/**
 * Interface for an ordered search tree
 * @param <E> the element type stored in the tree
 */
public interface SearchTree<E extends Comparable<E>> {
    /**
     * Adds an element to the tree based on the rules of the search tree
     * @param item the element to add to the tree
     * @return true if the element is added, false otherwise
     */
    boolean add(E item);

    /**
     * Searches to see if an element exists in the tree
     * @param target the element to search for
     * @return true if the element exists in the tree, false otherwise
     */
    boolean contains(E target);

    /**
     * Searches for and returns an element that is defined as equal to the target
     * @param target an element defined as equal to the desired element
     * @return the desired element
     */
    E find(E target);

    /**
     * Deletes an element from the tree and returns it
     * @param target the element to remove
     * @return the removed element
     */
    E delete(E target);

    /**
     * Removes an element from the tree
     * @param target the element to remove
     * @return true if the element is removed, false otherwise
     */
    boolean remove(E target);

    /**
     * Returns the elements stored in the tree as a List
     * @return a List of elements
     */
    List<E> toList();

    /**
     * Clears the tree
     */
    void clear();
}
