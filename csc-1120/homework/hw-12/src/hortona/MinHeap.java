/*
 * Course: CSC-1120
 * MinHeap
 * Name: // FIXME
 */
package hortona;

/**
 * A Heap that sorts lowest to highest, so the front of the queue is the smallest value
 * @param <E> the type of element stored in the Heap
 */
public class MinHeap<E extends Comparable<? super E>> extends AbstractHeap<E> {
    @Override
    protected int compare(E e1, E e2) {
        // TODO
        return -1;
    }
}
