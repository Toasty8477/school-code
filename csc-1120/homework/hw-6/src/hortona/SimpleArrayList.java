/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Homework 6 - Comparing Lists
 * Name: Alexander Horton
 * Updated: 2/28/25
 */

package hortona;

import java.util.StringJoiner;

/**
 * Simple implementation of an ArrayList
 * @param <E> element type
 */
public class SimpleArrayList<E> implements SimpleList<E> {

    private static final int INITIAL_CAPACITY = 10;
    private static int accessCount = 0;
    private E[] data;
    private int size;
    private int capacity;

    /**
     * Constructor for SimpleArrayList
     */
    public SimpleArrayList() {
        size = 0;
        capacity = INITIAL_CAPACITY;
        data = (E[]) new Object[INITIAL_CAPACITY];
    }

    public static int getAccessCount() {
        return accessCount;
    }

    /**
     * Resets access count variable to 0
     */
    public void resetAccessCount() {
        accessCount = 0;
    }

    @Override
    public int size() {
        accessCount++;
        return size;
    }

    @Override
    public void add(E e) {
        if (this.size == this.data.length) {
            reallocate();
        }
        this.data[size] = e;
        size++;
        accessCount += 2;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        accessCount += 2;
        return data[index];
    }

    @Override
    public E set(int index, E e) {
        validateIndex(index);
        E oldValue = this.data[index];
        accessCount++;
        this.data[index] = e;
        accessCount++;
        return oldValue;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < this.size; i++) {
            sj.add("" + data[i]);
        }
        return sj.toString();
    }

    private void reallocate() {
        capacity = data.length * 2 + 1;
        E[] newData = (E[]) new Object[capacity];
        for (int i = 0; i < data.length; i++) {
            newData[i] = this.data[i];
            accessCount += 2;
        }
        this.data = newData;
    }

    private void validateIndex(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
