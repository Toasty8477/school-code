/*
 * Course: CSC-1120
 */
package username;

/**
 * A simple MergeSort implementation
 */
public class MergeSort implements SortAlgorithm {
    private long numSwaps = 0;
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Comparable<? super T>> void sort(T[] table) {
        if(table.length > 1) {
            // split table into halves
            int half = table.length / 2;
            T[] left = (T[]) new Comparable[half];
            T[] right = (T[]) new Comparable[table.length - half];
            System.arraycopy(table, 0, left, 0, half);
            System.arraycopy(table, half, right, 0, table.length - half);
            // sort the halves
            sort(left);
            sort(right);

            // merge the halves
            numSwaps += merge(table, left, right);
        }
    }

    /**
     * Merges two arrays
     * @param result the resulting merged array
     * @param left the left array
     * @param right the right array
     * @param <T> the element type
     * @return number of swaps
     */
    public static <T extends Comparable<? super T>> long merge(T[] result, T[] left, T[] right) {
        int i = 0; // pointer to the current left index
        int j = 0; // pointer to the current right index
        int k = 0; // pointer to the result index
        long access = 0;

        // while there is data in both halves
        while(i < left.length && j < right.length) {
            // find smaller value and add to the result
            result[k++] = left[i].compareTo(right[j]) < 0 ? left[i++] : right[j++];
            ++access;
        }
        // add remaining values from left and right to result
        while(i < left.length) {
            result[k++] = left[i++];
            ++access;
        }
        while(j < right.length) {
            result[k++] = right[j++];
            ++access;
        }
        return access;
    }

    @Override
    public String toString() {
        return String.valueOf(numSwaps);
    }
}
