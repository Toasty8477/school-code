/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 14 - Yet More Autocompleter
 * Name: Alexander Horton
 * Updated: 05/09/2025
 */

package hortona.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * BST implementation of AutoCompleter
 */
public class BinarySearchTree implements AutoCompleter {

    private final TreeSet<String> items;

    /**
     * BinarySearchTree Constructor
     * @param items list of words
     */
    public BinarySearchTree(List<String> items) {
        this.items = new TreeSet<>();
        items = items.stream()
                .distinct()
                .collect(Collectors.toList());
        this.items.addAll(items);
    }

    @Override
    public boolean add(String word) {
        return items.add(word);
    }

    @Override
    public boolean exactMatch(String target) {
        return items.contains(target);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public String getBackingClass() {
        return String.valueOf(items.getClass()).replace("class ", "");
    }

    @Override
    public String[] allMatches(String prefix) {
        ArrayList<String> matches = new ArrayList<>();
        for (String s : items) {
            if (s.startsWith(prefix)) {
                matches.add(s);
            }
        }
        return matches.toArray(new String[0]);
    }
}
