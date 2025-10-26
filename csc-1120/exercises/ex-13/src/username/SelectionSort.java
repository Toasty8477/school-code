/*
 * Course: CSC-1120
 */
package username;

/**
 * Simple SelectionSort implementation
 */
public class SelectionSort implements SortAlgorithm {
    @Override
    public <T extends Comparable<? super T>> void sort(T[] table) {
        long access = 0;
        for(int i = 0; i < table.length - 1; ++i) {
            int min = i;
            ++access;
            for(int j = 1; j < table.length; ++j) {
                if(table[j].compareTo(table[min]) < 0) {
                    min = j;
                }
                ++access;
            }
            T temp = table[i];
            table[i] = table[min];
            table[min] = temp;
        }
        System.out.println(access);
    }
}
