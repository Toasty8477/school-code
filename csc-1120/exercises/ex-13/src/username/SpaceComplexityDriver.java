/*
 * Course: CSC-1120
 * Space Complexity Exercise
 */
package username;

import java.util.Scanner;

/**
 * 1.  Start running this program, but do not press enter
 * 2.  Launch jconsole from the command prompt (not the terminal inside IntelliJ).
 * 3.  In jconsole, select the SpaceComplexityDriver process from the list and  click "connect"
 * 4.  Click "insecure connection"
 * 5.  Once it is running, you will see several windows with measurements
 * 6.  Note the Heap Memory Usage.
 * 7.  Press enter and let each sort algorithm run.
 * 8.  Note the Heap Memory Usage after each algorithm has run
 * 9.  Also note the CPU usage
 * 10. Be aware that the heap usage will rise as the program idles, so wait
 *     for the sort to finish, give the console a moment to catch up, then
 *     run the next sort
 */
public class SpaceComplexityDriver {
    public static void main(String[] args) {
        final int numElements = 200_000_000; // change this value to increase n
        final int[] source = new int[numElements];
        fill(source);
        final Integer[] arr = new Integer[numElements];
        for(int i = 0; i < source.length; ++i) {
            arr[i] = source[i];
        }
        boolean run = true;
        try(Scanner in = new Scanner(System.in)) {
            while(run) {
                System.out.println("Press enter to begin testing");
                in.nextLine();
//                System.out.println("Running Selection Sort on n=" + numElements);
//                SelectionSort ss = new SelectionSort();
//                Integer[] ssArray = arr.clone();
//                ss.sort(ssArray);
//                System.out.println("Press enter to continue");
//                in.nextLine();
//                System.out.println("Running Insertion Sort on n=" + numElements);
//                InsertionSort is = new InsertionSort();
//                Integer[] isArray = arr.clone();
//                is.sort(isArray);
//                System.out.println("Press enter to continue");
//                in.nextLine();
//                System.out.println("Running Shell Sort on n=" + numElements);
//                ShellSort shs = new ShellSort();
//                Integer[] shsArray = arr.clone();
//                shs.sort(shsArray);
//                System.out.println("Press enter to continue");
//                in.nextLine();
                System.out.println("Running Merge Sort on n=" + numElements);
                MergeSort ms = new MergeSort();
                Integer[] msArray = arr.clone();
                ms.sort(msArray);
                System.out.println(ms);
                System.out.println("Press enter to continue");
                in.nextLine();
                System.out.println("All sorts run");
                System.out.println("Run again?(y/n)");
                String again = in.nextLine();
                run = again.equalsIgnoreCase("y");
            }
        }
    }

    private static void fill(int[] arr) {
        final int max = 50_000;
        for(int i = 0; i < arr.length; ++i) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
    }
}
