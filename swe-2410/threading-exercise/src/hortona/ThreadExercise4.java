/**
 * Course: SWE 2410
 */

package hortona;

import java.util.ArrayList;
import java.util.List;

/**
 * There needs to be javadoc here for checkstyle
 */
public class ThreadExercise4 {
    private static final int NUM_VALUES = 1000;
    private List<Integer> list;

    /**
     * Constructor for ThreadExercise4
     */
    public ThreadExercise4() {
        list = new ArrayList<>();
    }

    /**
     * Add elements to a list
     * @param numElements # of elements to add
     */
    public void addElements(int numElements) {
        for (int i = 0; i < numElements; i++) {
            synchronized(this) {
                list.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadExercise4 threadExercise1 = new ThreadExercise4();

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
