/*
 * Course: CSC-1120
 * Heap Interface
 * Name: // FIXME
 */
package hortona;

/**
 * A simple Heap interface
 * @param <E> the type of element stored in the Heap
 */
public interface Heap<E extends Comparable<? super E>> {
    /**
     * Insert an item into the Heap
     * @param item the item to be inserted
     * @return true
     * @throws NullPointerException throws in the item is null
     */
    boolean offer(E item);

    /**
     * Remove an item from the priority queue
     * @return the item at the front of the priority queue
     */
    E poll();

    /**
     * Checks if the heap is empty
     * @return true if the hea is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns how many items are in the heap
     * @return the size of the heap
     */
    int size();
}
