/**
 * Course: SWE 2410
 */

package hortona;

import java.util.LinkedList;
import java.util.List;

/**
 * There needs to be javadoc here for checkstyle
 */
public class ThreadExercise2 {
    private static final int NUM_VALUES = 1000;
    private List<Integer> list;

    /**
     * Constructor for ThreadExercise2
     */
    public ThreadExercise2() {
        list = new LinkedList<>();
    }

    /**
     * Add elements to a list
     * @param numElements # of elements to add
     */
    public void addElements(int numElements) {
        for (int i = 0; i < numElements; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadExercise2 threadExercise1 = new ThreadExercise2();

        Thread t1 = new Thread(() -> threadExercise1.addElements(NUM_VALUES));
        Thread t2 = new Thread(() -> threadExercise1.addElements(NUM_VALUES));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(threadExercise1.list.size());
        for (Integer i : threadExercise1.list) {
            System.out.print(i);
            System.out.print(", ");
        }
    }
}
