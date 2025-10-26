/*
 * Course: CSC-1120
 */
package username;

/**
 * Simple Insertion Sort implementation
 */
public class InsertionSort implements SortAlgorithm {
    @Override
    public <T extends Comparable<? super T>> void sort(T[] table) {
        int numSwaps = 0;
        for(int i = 1; i < table.length; ++i) {
            numSwaps += insert(table, i);
        }
        System.out.println(numSwaps);
    }

    /**
     * Inserts an element at next where it belongs
     * @param table the array being sorted
     * @param next the position of the element to insert
     * @param <T> the element type
     * @return number of swaps
     */
    public static <T extends Comparable<? super T>> long insert(T[] table, int next) {
        long access = 0;
        T nextValue = table[next];
        while(next > 0 && nextValue.compareTo(table[next - 1]) < 0) {
            table[next] = table[next - 1];
            --next;
            ++access; // swap
        }
        table[next] = nextValue;
        ++access; // insert
        return access;
    }
}
