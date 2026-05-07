/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Noah Dinan
 */

package finalproject;

import java.util.LinkedList;

/**
 * Creates and stores an array of train cars
 */
public class Train {
    private Traincar[] traincars;

    private final int offset = 4;

    /**
     * Create a new train, throw an IndexOutOfBoundsException if the train cannot
     * fit on the given path
     * 
     * @param length
     * @param path
     * @throws IndexOutOfBoundsException
     */
    public Train(int length, LinkedList<PathNode> path) throws IndexOutOfBoundsException {
        traincars = new Traincar[length];

        // make sure the FlyweightFactory knows the path
        FlyweightFactory.setPath(path);

        if (length * offset > path.size()) {
            throw new IndexOutOfBoundsException("Train too long for track");
        }

        for (int i = 0; i < length * offset; i += offset) {
            Traincar t = new Traincar(i, FlyweightFactory.getFactory());
            traincars[i / offset] = t;
        }
    }

    /**
     * get traincars[]
     * 
     * @return traincars
     */
    public Traincar[] getTraincars() {
        return traincars;
    }

}
