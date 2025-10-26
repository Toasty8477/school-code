/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 14 - Yet More Autocompleter
 * Name: Alexander Horton
 */

package hortona.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringJoiner;

/**
 * Driver that runs benchmarking tests
 */
public class Benchmarker {
    public static void main(String[] args) {
        try (PrintWriter pw = new PrintWriter("result.csv")) {
            final String[] structures = {"unordered", "ordered", "BST", "Trie", "Hash"};
            // size should contain the number of "words" to add to the AutoCompleter for each
            // successive test {100, 1_000, ...}
            // Experiment with a wide variety of sizes.
            final int[] size = {100, 1_000, 10_000, 50_000, 100_000};
            // length should be the length of the "words" added to the AutoCompleter
            // Should be AT LEAST 5. Experiment with different lengths.
            // with a length of 5 there are ~12 million permutations
            final int length = 5;
            final long[] exactResults = new long[structures.length];
            final long[] allResults = new long[structures.length];
            pw.println("n,Method,Unordered List, Ordered List, Binary Search Tree, Trie, HashMap");
            for (int i = 0; i < size.length; ++i) {
                System.out.println("Generating dictionary...");
                String[] words = new String[size[i]];
                for (int j = 0; j < size[i]; ++j) {
                    words[j] = AutoCompleter.getString(length);
                }
                System.out.println("Dictionary generated.");
                AutoCompleter a;
                int index = 0;
                for (String s : structures) {
                    System.out.println("Building " + s);
                    switch (s) {
                        case "unordered" -> a = new UnorderedList(new LinkedList<>());
                        case "ordered" -> a = new OrderedList(new LinkedList<>());
                        case "BST" -> a = new BinarySearchTree(new LinkedList<>());
                        case "Trie" -> a = new Trie();
                        default -> a = new HashTable();
                    }
                    for (String str : words) {
                        a.add(str);
                    }
                    System.out.println("Benchmarking..." + size[i]);
                    String target = AutoCompleter.getString(length + 1);
                    long start = System.nanoTime();
                    a.exactMatch(target);
                    long end = System.nanoTime();
                    exactResults[index] = end - start;
                    start = System.nanoTime();
                    a.allMatches(target);
                    end = System.nanoTime();
                    allResults[index++] = end - start;
                }
                printResults(exactResults, pw, size[i], "exactMatches");
                printResults(allResults, pw, size[i], "allMatches");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not find output file");
        }
    }

    private static void printResults(long[] results, PrintWriter pw, int size, String type) {
        StringJoiner sj = new StringJoiner(",");
        sj.add(Integer.toString(size));
        sj.add(type);
        for (long result : results) {
            sj.add(Long.toString(result));
        }
        pw.println(sj);
    }
}
