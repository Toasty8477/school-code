package hortona;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderedList implements AutoCompleter {

    private List<String> items;

    public OrderedList(List<String> items) {
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
            int insertionPoint = Collections.binarySearch(items, lowerCaseWord);
            if (insertionPoint / Math.abs(insertionPoint) == -1) {
                items.add(-1 * (insertionPoint + 1), lowerCaseWord);
            } else {
                items.add(insertionPoint, lowerCaseWord);
            }
        }
        return !duplicate;
    }

    @Override
    public boolean exactMatch(String target) {
        int position = Collections.binarySearch(items, target);
        if (position / Math.abs(position) == -1) {
            return false;
        }
        return true;
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
        return matches;
    }
}
