/*
 * Course: SWE2410 
 * Final Project: Flyweight Pattern
 * Horton, Dinan
 */

package finalproject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * FlyweightFactory
 */
public class FlyweightFactory {
    private static FlyweightFactory factory = null;
    private static List<Flyweight> flyweights;
    private static LinkedList<PathNode> path;

    private FlyweightFactory() {
        flyweights = new ArrayList<>();
    }

    public static void setPath(LinkedList<PathNode> p) {
        path = p;
    }

    /**
     * Returns a unique instance of the flyweight factory
     * 
     * @return Unique instance of the factory
     */
    public static FlyweightFactory getFactory() {
        if (factory == null) {
            factory = new FlyweightFactory();
        }
        return factory;
    }

    /**
     * Returns an instance of a flyweight or null if the type is not supported by
     * this factory
     * 
     * @param type The type of flyweight you would like
     * @return A Flywirght or null
     */
    public Flyweight getFlyweight(String type) {
        if (type.equals("train")) {
            for (Flyweight flyweight : flyweights) {
                if (flyweight instanceof ConcreteFlyweight) {
                    return flyweight;
                }
            }
            return new ConcreteFlyweight(path);
        } else {
            return null;
        }
    }
}
