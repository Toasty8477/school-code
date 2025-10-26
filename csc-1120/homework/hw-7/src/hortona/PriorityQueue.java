/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Homework 7 - Queues
 * Name: Alexander Horton
 * Updated: 3/7/2025
 */

package hortona;

/**
 * Extension of the SJQueue with priority
 * @param <E>
 */
public class PriorityQueue<E extends Comparable<? super E>> extends SJQueue<E> {
    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (super.data.isEmpty()) {
            data.add(e);
            return true;
        }
        for (int i = 0; i < super.data.size(); i++) {
            if (data.get(i).compareTo(e) != -1) {
                data.add(i, e);
                return true;
            }
        }
        data.add(data.size(), e);
        return true;
    }
    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (super.data.isEmpty()) {
            data.offer(e);
            return true;
        }
        for (int i = 0; i < super.data.size(); i++) {
            if (data.get(i).compareTo(e) != -1) {
                data.add(i, e);
                return true;
            }
        }
        data.offerLast(e);
        return true;
    }
}
