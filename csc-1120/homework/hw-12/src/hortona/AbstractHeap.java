/*
 * Course: CSC-1120
 * Abstract Heap
 * Name: // FIXME
 */
package hortona;

import java.util.ArrayList;

/**
 * An abstract Heap class that defers the compare method to the concrete implementation
 * @param <E> the type of element in the heap
 */
public abstract class AbstractHeap<E extends Comparable<? super E>> implements Heap<E> {
    private static final int INITIAL_CAPACITY = 10;
    private final ArrayList<E> data;

    /**
     * No param constructor for the AbstractHeap
     */
    public AbstractHeap() {
        data = new ArrayList<>(INITIAL_CAPACITY);
    }
    @Override
    public boolean offer(E item) {
        // TODO
        // Add item to the bottom of the heap (the end of the list)
        // child is the index of the newly added item
        // the parent index is the next level above the child
        // Re-heap while the child is not root and the child is "larger" than the parent

        data.add(item);
        int childIndex = data.size() - 1;
        int parentIndex = (childIndex - 1) / 2;
        int comparison = data.get(childIndex).compareTo(data.get(parentIndex));

        if (comparison < 0) {
            E temp1 = data.get(childIndex);
            E temp2 = data.get(parentIndex);
            data.add(childIndex, temp2);
            data.remove(childIndex+1);
            data.add(parentIndex, temp1);
            data.remove(parentIndex+1);
        }

        return true;
    }

    @Override
    public E poll() {
        // TODO
        if(isEmpty()) {
            return null;
        }
        // store the value to remove (the top of the heap)
        // If there is only one item in the heap, just remove it
        // Remove the last item in the array (the rightmost node on the lowest level) and replace
        // the root node we are removing
        // Start with the parent index at the root (the front of the list)
        // while still in the bounds of the heap, find the correct index for the current parent
            // find the left child index (the inverse of finding the parent index)
            // if the left child would be out of the heap, we're done. return the item that was at
            // the top of the heap
            // Get the possible right child index of the parent
            // find the minimum between the left and right children
            // if the parent is less than the smaller child, swap and keep looking
            // otherwise we have found the location and we stop looking
        // return the item that was at the top of the heap
        E toRemove = data.getFirst();
        if (data.size() == 1) {
            data.removeFirst();
            return toRemove;
        } else {
            E temp = data.get(data.size()-1);
            data.removeLast();
            data.addFirst(temp);
            data.remove(1);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int size() {
        return data.size();
    }

    protected abstract int compare(E e1, E e2);

    private void swap(int a, int b) {
        E temp = data.get(a);
        data.set(a, data.get(b));
        data.set(b, temp);
    }
}
