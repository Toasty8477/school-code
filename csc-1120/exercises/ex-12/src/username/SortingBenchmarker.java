/*
 * Course: CSC-1120
 */
package username;

/**
 * Counts number of swaps per sorting algorithm
 */
public class SortingBenchmarker {

    public static void main(String[] args) {
        //final Integer[] sourceArray = {40, 35, 80, 75, 60, 90, 70, 65, 50, 22};
        final int size = 100_000_000;
        Integer[] sourceArray = new Integer[size];
        fillArray(sourceArray);
        //Integer[] selection = new Integer[sourceArray.length];
        //Integer[] insertion = new Integer[sourceArray.length];
        Integer[] shell = new Integer[sourceArray.length];
        Integer[] merge = new Integer[sourceArray.length];
        //System.arraycopy(sourceArray, 0, selection, 0, sourceArray.length);
        //System.arraycopy(sourceArray, 0, insertion, 0, sourceArray.length);
        System.arraycopy(sourceArray, 0, shell, 0, sourceArray.length);
        System.arraycopy(sourceArray, 0, merge, 0, sourceArray.length);
        //SelectionSort ss = new SelectionSort();
        //ss.sort(selection);
        //InsertionSort is = new InsertionSort();
        //is.sort(insertion);
        ShellSort shs = new ShellSort();
        shs.sort(shell);
        MergeSort ms = new MergeSort();
        ms.sort(merge);
        System.out.println(ms);
    }

    private static void fillArray(Integer[] arr) {
        final int range = 50_000;
        for(int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * range + 1);
        }
    }
}
