/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 14 - Yet More Autocompleter
 * Name: Alexander Horton
 * Updated: 05/09/2025
 */

package hortona.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderList implementation of Autocompleter
 */
public class OrderedList implements AutoCompleter {

    private List<String> items;

    /**
     * Constructor for OrderedList
     * @param items list of words
     */
    public OrderedList(List<String> items) {
        if (items.getClass().toString().equals("class java.util.ArrayList")) {
            this.items = items.stream()
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            this.items = items.stream()
                    .distinct()
                    .collect(Collectors.toCollection(LinkedList::new));
        }
    }

    @Override
    public boolean add(String word) throws IllegalArgumentException {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty or null.");
        }
        String lowerCaseWord = word.toLowerCase();
        if (!exactMatch(lowerCaseWord)) {
            int insertionPoint = Collections.binarySearch(items, lowerCaseWord);
            if (insertionPoint < 0) {
                items.add(-1 * (insertionPoint + 1), lowerCaseWord);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean exactMatch(String target) {
        if (target != null) {
            int position = Collections.binarySearch(items, target);
            if (position != 0 && position / Math.abs(position) == -1) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public String getBackingClass() {
        final int stripClassHeader = 6;
        return String.valueOf(items.getClass()).substring(stripClassHeader);
    }

    @Override
    public String[] allMatches(String prefix) {
        if (prefix == null) {
            return new String[0];
        }
        int numMatches = (int) this.items.stream()
                .filter(word -> word.startsWith(prefix))
                .count();
        String[] matches = new String[numMatches];
        int matchesIndex = 0;
        for (int i = 0; i < items.size(); i++) {
            if (this.items.get(i).startsWith(prefix)) {
                matches[matchesIndex] = this.items.get(i);
                matchesIndex++;
            }
        }
        matches = this.items.stream()
                .filter(word -> word.startsWith(prefix))
                .toArray(String[]::new);
        return matches;
    }
}
