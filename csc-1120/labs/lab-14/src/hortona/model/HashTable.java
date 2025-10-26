/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 14 - Yet More Autocompleter
 * Name: Alexander Horton
 * Updated: 5/1/2025
 */

package hortona.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * HashTable implementation of AutoCompleter
 */
public class HashTable implements AutoCompleter {

    private final HashSet<String> items;

    /**
     * HashTable constructor
     */
    public HashTable() {
        items = new HashSet<>();
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
        String[] all = items.toArray(new String[0]);
        ArrayList<String> matches = new ArrayList<>();
        for(String s : all) {
            if (s.startsWith(prefix)) {
                matches.add(s);
            }
        }
        return matches.toArray(new String[0]);
    }
}
