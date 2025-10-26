/*
 * Course: CSC-1120
 */
package username;

/**
 * A simple shell sort implementation
 */
public class ShellSort implements SortAlgorithm {
    @Override
    public <T extends Comparable<? super T>> void sort(T[] table) {
        long numSwaps = 0;
        final double shellConstant = 2.2; // used to calculate next gap
        int gap = table.length / 2; // gap between adjacent elements in sub-arrays
        while(gap > 0) {
            for(int i = gap; i < table.length; ++i) {
                // insert element at next position in its sub-array
                numSwaps += insert(table, i, gap);
            }
            // reset gap for next pass
            gap = gap == 2 ? 1 : (int) (gap / shellConstant);
        }
        System.out.println(numSwaps);
    }

    /**
     * Helper method that inserts element at next where it belongs in the array
     * @param table the table being sorted
     * @param next the position of the element to insert
     * @param gap the gap between elements
     * @param <T> the type in the array
     * @return the number of swaps
     */
    public static <T extends Comparable<? super T>> long insert(T[] table, int next, int gap) {
        long access = 0;
        T nextValue = table[next]; // store element to insert
        // shift all values > nextVal in sub-array down by gap
        while(next > gap - 1 && nextValue.compareTo(table[next - gap]) < 0) {
            table[next] = table[next - gap]; // shift down
            next -= gap; // check next position in sub-array
            ++access; // swap
        }
        table[next] = nextValue; // insert nextVal in proper location
        ++access; // insert
        return access;
    }
}
