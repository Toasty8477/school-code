/**
 * Course: SWE 2410
 */

package hortona;

import java.util.ArrayList;
import java.util.List;

/**
 * There needs to be javadoc here for checkstyle
 */
public class ThreadExercise1 {
    private static final int NUM_VALUES = 1000;
    private List<Integer> list;

    /**
     * Constructor for ThreadExercise1
     */
    public ThreadExercise1() {
        list = new ArrayList<>();
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
        ThreadExercise1 threadExercise1 = new ThreadExercise1();

        Thread t1 = new Thread(() -> threadExercise1.addElements(NUM_VALUES));
        Thread t2 = new Thread(() -> threadExercise1.addElements(NUM_VALUES));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(threadExercise1.list.size());
        System.out.println(threadExercise1.list.toString());
    }
}
