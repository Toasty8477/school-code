/*
 * Course: CSC1110 - 110
 * Term: Fall 2024
 * Exercise 9
 * Name: Alexander Horton
 * Created: 10/31/2024
 */
package hortona;

import java.util.ArrayList;

/**
 * Exercie 9 starter code that has stub out methods
 * for ArrayLists.
 */
public class Exercise9 {
    /**
     * Returns the index of the first occurence of the target
     * in the list or -1 if the target is not in the list.
     * @param list Array of Strings
     * @param target String to be found
     * @return index of target or -1 if target not in array
     */
    private static int indexOf(String[] list, String target){
        boolean found = false;
        int ret = -1;
        for (int i = 0; i < list.length && !found; i++) {
            if (list[i].equals(target)) {
                found = true;
                ret = i;
            }
        }
        return ret;
    }
    /**
     * Adds the element to the list at the given index. All elements
     * to the right of the index are shifted to the right.
     * @param list Array of Strings. Assume the length of the list is at
     *             least 1.
     * @param index Location where to add the String. Assume index is not negative
     *              and less than or equal to the length of the list.
     * @param element Element to insert in the list
     * @return New list that contains the elements of the passed in list with
     * the element added at the specified index.
     */
    private static String[] add(String[] list, int index, String element){
        String[] ret = new String[list.length+1];
        boolean added = false;
        for (int i = 0; i < list.length; i++) {
            if (i == index) {
                added = true;
                ret[i] = element;
            }
            if (!added) {
                ret[i] = list[i];
            } else {
                ret[i+1] = list[i];
            }
        }
        return ret;
    }
    /**
     * Removes the element at the given index. All elements
     * to the right of the index are shifted to the left.
     * @param list Array of Strings. Assume the length of the list is at
     *             least 1.
     * @param index Location of the element to remove.Assume index is
     *              not negative and less than or equal to the length of the list.
     * @return New list that contains the elements of the passed in list minus
     * the element at the specified index.
     */
    private static String[] remove(String[] list, int index){
        String[] ret = new String[list.length-1];
        for (int i = 0; i < list.length; i++) {
            if (i <= index) {
                ret[i] = list[i];
            } else {
                ret[i-1] = list[i];
            }
        }
        return ret;
    }

    /**
     * Helper method that nicely prints out an Array.
     * You must use this method to print out your Arrays.
     * @param list Array of Strings.
     */
    private static void printArray(String[] list){
        for(int i = 0; i<list.length; i++){
            if(i == 0){
                System.out.print("["+list[i]+",");
            } else if(i != list.length-1) {
                System.out.print(list[i] + ",");
            } else {
                System.out.print(list[i] + "]\n");
            }
        }
    }

    /**
     * The following driver creates an Array of Strings
     * and then tests the indexOf(), add(), and remove() methods
     * you implemented for the Array.
     */
    private static void part1(){
        System.out.println("Part1: Using custom methods for an Array\n");
        System.out.println("Array of Strings");

        String[] words = {"foo", "cat", "bar", "cat"};
        String target1 = "cat";
        String target2 = "taco";
        int removeAt = 1;
        int addAt = 2;
        String toAdd = "taco";

        printArray(words);
        System.out.println("Index of first occurrence of "+target1);
        System.out.println(indexOf(words, target1));

        System.out.println("Index of first occurrence of "+target2);
        System.out.println(indexOf(words, target2));

        System.out.println("List after removing element at index "+removeAt);
        String[] removedList = remove(words, removeAt);
        printArray(removedList);

        System.out.println("List after adding "+toAdd+" at index "+addAt);
        String[] addedList = add(removedList, addAt, toAdd);
        printArray(addedList);
    }

    /**
     * The following prompts have you practice
     * the indexOf(), add(), and remove() methods
     * of the ArrayList class.
     */
    private static void part2(){
        System.out.println("\nPart2: Using builtin methods for an ArrayList\n");
        System.out.println("ArrayList of Strings");

        //TODO: Create an ArrayList of Strings and add
        // 'foo', 'cat', 'bar', and 'cat' in that order.
        // Print out the resulting ArrayList. Note,
        // ArrayList have a toString() method, so you
        // can do System.out.println(list); to print
        // out an ArrayList called list.
        ArrayList<String> list = new ArrayList<>();
        list.add("foo");
        list.add("cat");
        list.add("bar");
        list.add("cat");
        System.out.println(list);

        //TODO: Print out the index of "cat" in the ArrayList
        // using the ArrayList's indexOf() method.
        System.out.println("Index of cat is " + list.indexOf("cat"));

        //TODO: Print out the index of "taco" in the ArrayList
        // using the ArrayList's indexOf() method.
        System.out.println("Index of taco is " + list.indexOf("taco"));

        //TODO: Remove the element at index 1 from the ArrayList
        // using the ArrayList's remove() method.
        // Print out the resulting ArrayList.
        // Note, that unlike the remove() method that you made
        // for an Array, the ArrayList's remove() method does
        // not return an ArrayList. It modifies the ArrayList
        // it was called on.
        System.out.println("List after remove element from index 1");
        list.remove(1);
        System.out.println(list);

        //TODO: Add the element "taco" at index 2 from the ArrayList
        // using the ArrayList's add() method.
        // Print out the result ArrayList.
        // Note, that unlike the add() method that you made
        // for an Array, the ArrayList's add() method does
        // not return an ArrayList. It modifies the ArrayList
        // it was called on.
        System.out.println("List after adding taco at index 2");
        list.add(2, "taco");
        System.out.println(list);
    }
    
    public static void main(String[] args) {
        part1();
        part2();
    }
}
