/*
 * Course: CSC-1120
 * MaxHeap
 * Name: // FIXME
 */
package hortona;

/**
 * A Heap that sorts highest to lowest, so the front of the queue is the largest value
 * @param <E> the type of element stored in the Heap
 */
public class MaxHeap<E extends Comparable<? super E>> extends AbstractHeap<E> {
    @Override
    protected int compare(E e1, E e2) {
        // TODO
        return -1;
    }
}
