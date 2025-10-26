/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 6 - Autocompleter
 * Name: Alexander Horton
 * Updated: 3/4/2025
 */

package hortona.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple implementation of autocompleter
 */
public class UnorderedList implements AutoCompleter {

    private List<String> items;

    /**
     * Unordered List Constructor
     * @param items Initial items in the list
     */
    public UnorderedList(List<String> items) {
        this.items = items.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(String word) throws IllegalArgumentException {
        boolean duplicate = false;
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty or null.");
        }
        String lowerCaseWord = word.toLowerCase();
        for (int i = 0; i < items.size() && !duplicate; i++) {
            duplicate = this.items.get(i).equals(lowerCaseWord);
        }
        if (!duplicate) {
            items.add(lowerCaseWord);
        }
        return !duplicate;
    }

    @Override
    public boolean exactMatch(String target) {
        for (int i = 0; i < items.size(); i++) {
            if (this.items.get(i).equals(target)) {
                return true;
            }
        }
        return false;
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
        return matches;
    }
}
