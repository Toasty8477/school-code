/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 13 - More Autocompleter
 * Name: Alexander Horton
 * Updated: 4/24/2025
 */

package hortona.model;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Trie implementation of AutoCompleter
 */
public class Trie implements AutoCompleter {

    private boolean endsWord;
    private Map<Character, Trie> edges;
    private ArrayList<String> matches;
    private StringBuilder sb = new StringBuilder();

    /**
     * Trie constructor
     */
    public Trie() {
        this.endsWord = false;
        this.edges = new ListMap<>();
        matches = new ArrayList<>();
    }

    @Override
    public boolean add(String word) throws IllegalArgumentException {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty or null.");
        }
        word = word.toLowerCase();
        if (!exactMatch(word)) {
            return add(word, this);
        } else {
            return false;
        }
    }

    private boolean add(String word, Trie subTrie) {
        if (word.isEmpty()) {
            subTrie.endsWord = true;
            return true;
        } else {
            if (!subTrie.edges.containsKey(word.charAt(0))) {
                subTrie.edges.put(word.charAt(0), new Trie());
            }
            Trie nextSubTrie = subTrie.edges.get(word.charAt(0));
            return add(word.substring(1), nextSubTrie);
        }
    }

    @Override
    public boolean exactMatch(String target) {
        return exactMatch(target, this);
    }
    private boolean exactMatch(String target, Trie subTrie) {
        if (subTrie == null) {
            return false;
        }
        if (target.isEmpty()) {
            return subTrie.endsWord;
        }
        Trie newSubTrie = subTrie.edges.get(target.charAt(0));
        return exactMatch(target.substring(1), newSubTrie);
    }

    @Override
    public int size() {
        return edges.size();
    }

    @Override
    public String getBackingClass() {
        final int stripClassHeader = 6;
        return String.valueOf(edges.getClass()).substring(stripClassHeader);
    }

    @Override
    public String[] allMatches(String prefix) {
        if (prefix == null) {
            return new String[0];
        }
        sb.delete(0, sb.length());
        matches.clear();
        traverse(this, prefix);
        Collections.sort(matches);
        return matches.toArray(new String[0]);
    }

    private void traverse(Trie subTrie, String prefix) {
        if (subTrie != null) {
            Set<Map.Entry<Character, Trie>> entries = subTrie.edges.entrySet();
            if (subTrie.endsWord) {
                if(sb.toString().startsWith(prefix)) {
                    matches.add(sb.toString());
                }
            }
            for (Map.Entry<Character, Trie> e : entries) {
                sb.append(e.getKey());
                traverse(e.getValue(), prefix);
                sb.deleteCharAt(sb.length()-1);
            }
        }
    }
}
