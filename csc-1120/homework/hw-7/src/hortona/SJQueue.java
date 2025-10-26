/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Homework 7 - Queues
 * Name: Alexander Horton
 * Updated: 3/7/2025
 */

package hortona;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * A partial concrete implementation of the Java Queue interface
 *
 * @param <E> Generic type passed in during construction. If no type specified on
 *           construction, Object will be used.
 */
public class SJQueue<E> implements Queue<E> {
    protected final LinkedList<E> data = new LinkedList<>();

    /**
     * Inserts the specified element into this queue if it is possible to do so immediately
     * without violating capacity restrictions, returning true upon success and throwing an
     * IllegalStateException if no space is currently available.
     *
     * @param e - the element to return
     * @return true
     * @throws NullPointerException - if the specified element is null and this queue does
     * not permit null elements
     */
    @Override
    public boolean add(E e) throws NullPointerException {
        if (e == null) {
            throw new NullPointerException();
        }
        data.add(e);
        return true;
    }

    /**
     * Inserts the specified element into this queue if it is possible to do so immediately
     * without violating capacity restrictions. When using a capacity-restricted queue, this
     * method is generally preferable to add(E), which can fail to insert an element only by
     * throwing an exception.
     *
     * @param e - the element to add
     * @return true if the element was added to the queue, else false
     * @throws NullPointerException - if the specified element is null and this queue does
     * not permit null elements
     */
    @Override
    public boolean offer(E e) throws NullPointerException {
        if (e == null) {
            throw new NullPointerException();
        }
        return data.offer(e);
    }

    /**
     * Retrieves and removes the head of this queue. This method differs from poll() only
     * in that it throws an exception if this queue is empty.
     *
     * @return the head of the queue
     * @throws NoSuchElementException - if this queue is empty
     */
    @Override
    public E remove() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return data.remove();
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public E poll() {
        return data.poll();
    }

    /**
     * Retrieves, but does not remove, the head of this queue. This method differs from peek
     * only in that it throws an exception if this queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException - if this queue is empty
     */
    @Override
    public E element() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return data.element();
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue
     * is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public E peek() {
        return data.peek();
    }

    /**
     * Returns the number of elements in this collection. If this collection contains more
     * than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return the number of elements in this collection
     */
    @Override
    public int size() {
        return Math.min(data.size(), Integer.MAX_VALUE);
    }
    /**
     * Returns true if this collection contains no elements
     *
     * @return true if this collection contains no elements, else false
     */
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns true if this collection contains the specified element. More formally,
     * returns true if and only if this collection contains at least one element e such
     * that Objects.equals(o, e).
     *
     * @param o element whose presence in this collection is to be tested
     * @return true if this collection contains the specified element
     * @throws NullPointerException - if the specified element is null and this collection
     * does not permit null elements
     */
    @Override
    public boolean contains(Object o) throws NullPointerException {
        if(o == null) {
            throw new NullPointerException();
        }
        return data.contains(o);
    }

    /**
     * Returns an iterator over the elements in this collection. There are no guarantees
     * concerning the order in which the elements are returned (unless this collection is
     * an instance of some class that provides a guarantee).
     *
     * @return an Iterator over the elements in this collection
     */
    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }

    /**
     * Removes all of the elements from this collection (optional operation). The
     * collection will be empty after this method returns.
     */
    @Override
    public void clear() {
        data.clear();
    }

    /**
     * Returns a copy of the queue as an Object array
     * @return an Object array containing all the elements of the Queue, in the order
     * they were added to the Queue
     */
    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    /**
     * Unsupported by this class
     * @param a unused
     * @param <T> unused
     * @return unused
     * @throws UnsupportedOperationException unused
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported by this class
     * @param o unused
     * @return unused
     * @throws UnsupportedOperationException unused
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported by this class
     * @param c unused
     * @return unused
     * @throws UnsupportedOperationException unused
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported by this class
     * @param c unused
     * @return unused
     * @throws UnsupportedOperationException unused
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported by this class
     * @param c unused
     * @return unused
     * @throws UnsupportedOperationException unused
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported by this class
     * @param c unused
     * @return unused
     * @throws UnsupportedOperationException unused
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
