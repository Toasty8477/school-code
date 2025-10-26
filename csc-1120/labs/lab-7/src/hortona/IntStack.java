/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 7 - Call Stack
 * Name: Alexander Horton
 * Updated: 3/11/2025
 */

package hortona;

import java.util.EmptyStackException;

/**
 * Simulation Stack Using Ints
 */
public class IntStack {

    private static class Node {
        private int data;
        private Node next;

        Node() {
            data = 0;
            next = null;
        }
    }

    private Node top;

    /**
     * IntStack constructor
     */
    public IntStack() {
        top = null;
    }

    /**
     * Adds a value to the top of the stack
     * @param i value to add
     * @return the value pushed
     */
    public int push(int i) {
        Node temp = new Node();
        temp.data = i;
        temp.next = top;
        top = temp;
        return i;
    }

    /**
     * Returns the size of the stack
     * @return stack size
     */
    public int size() {
        int size = 0;
        Node walker = top;
        while (walker != null) {
            walker = walker.next;
            size++;
        }
        return size;
    }

    /**
     * Takes a value off the top of the stack
     * @return value removed
     * @throws EmptyStackException when called on an empty stack
     */
    public int pop() throws EmptyStackException {
        if (top == null) {
            throw new EmptyStackException();
        }
        int topData = top.data;
        top = top.next;
        return topData;
    }

    /**
     * Returns the value at the top of the stack
     * @return the top value of the stack
     * @throws EmptyStackException when called on an empty stack
     */
    public int peek() throws EmptyStackException {
        if (top == null) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node walker = top;
        while (walker != null) {
            sb.append(String.format("| %8d |\n", walker.data));
            walker = walker.next;
        }
        return sb.toString();
    }
}
