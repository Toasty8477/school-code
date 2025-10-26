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
 * A simple single linked list
 * @param <E> Type of list
 */
public class SimpleLinkedList<E> implements SimpleList<E> {

    private static int accessCount = 0;
    private int size;
    private Node<E> head;

    private static class Node<E> {
        private E element;
        private Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        Node(E element) {
            this(element, null);
        }
    }

    /**
     * SimpleLinkedList Constructor
     */
    public SimpleLinkedList() {
        head = null;
    }

    public static int getAccessCount() {
        return accessCount;
    }

    /**
     * Resets the access count variable to 0
     */
    public void resetAccessCount() {
        accessCount = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E e) {
        if (size == 0) {
            this.head = new Node<>(e);
            accessCount++;
        } else {
            Node<E> walker = head;
            accessCount += 2;
            while(walker.next != null) {
                walker = walker.next;
                accessCount++;
            }
            walker.next = new Node<>(e);
            accessCount += 2;
        }
        size++;
        accessCount++;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        Node<E> walker = head;
        accessCount++;
        while (index > 0) {
            walker = walker.next;
            accessCount++;
            index--;
        }
        accessCount++;
        return walker.element;
    }

    @Override
    public E set(int index, E e) {
        validateIndex(index);
        Node<E> current = this.head;
        accessCount++;
        for (int i = 0; i < index; i++) {
            current = current.next;
            accessCount++;
        }
        E old = current.element;
        accessCount++;
        current.element = e;
        return old;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        Node<E> walker = this.head;
        while (walker.next != null) {
            sj.add("" + walker.element);
        }
        return sj.toString();
    }

    private Node<E> getNode(int index) {
        return null;
    }

    private void validateIndex(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
