package hortona;

import java.util.ArrayList;

public class Exercise10 {

    // TODO: Implement the method called shiftN that takes in
    // an ArrayList and an int n. Create a copy of the ArrayList.
    // Shift all the elements in the copy by n spaces. Return
    // the copy.


     // TODO Create a method called lastToMid that takes in an
     // ArrayList of Strings. Make a copy of the ArrayList.
     // Remove the last element of the copy and add it to the
     // middle of the copy. Return the copy.
     public static ArrayList<String> lastToMid(ArrayList<String> original) {
         ArrayList<String> ret = new ArrayList<>();
         for (int i = 0; i < original.size(); i++) {

         }
         return null;
     }


    // TODO: Create a new method called minValue() that takes in an
    // ArrayList of Integers and returns the min value.
    public static int minValue(ArrayList<Integer> nums) {
        int minValue = nums.getFirst();
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < minValue) {
                minValue = nums.get(i);
            }
        }
        return minValue;
    }

    // TODO: Creates a method call randList() that takes in
    // and int for the number of values and an int for the max value.
    // randList() returns a new ArrayList whose size is the
    // number of values and contains random ints from 1 up to and
    // including the max value.
    public static ArrayList<Integer> randList(int numValues, int maxValue) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i = 0; i < numValues; i++) {
            ret.add((int)(Math.random()*maxValue) + 1);
        }
        return ret;
    }

    public static void main(String[] args) {

        // TODO: Write the randList() method based
        //  on the JavaDoc above. Call randList()
        //  and store the returned ArrayList into
        //  an ArrayList called nums. Print out nums.
        System.out.println("ArrayList of random numbers.");
        ArrayList<Integer> nums = randList(5, 10);
        System.out.println(nums);


        // TODO: Write the minValue() method based on the
        //  comment above. Pass nums into this method,
        //  store the returned value in a int called
        //  minNum, and then print out minNum.
        System.out.println("Min number is.");
        int minNum = minValue(nums);
        System.out.println(minNum);

        // TODO: Implement lastToMid based on the comments
        //  above. Create an ArrayList of Strings called orig
        //  and add at least three Strings to it. Pass the ArrayList
        //  to lastToMid and stored the returned ArrayList
        //  in an ArrayList called lastCopy. Print out the
        //  orig ArrayList and lastCopy.
        System.out.println("Original ArrayList");
        System.out.println("Last element moved to middle");

        // TODO: Implement shiftN based on the comments
        //  above. Using three separate calls to
        //  shiftN, pass orig into shiftN with a shift of
        //  0, length() - 1 of orig, and 1. Store the
        //  returned ArrayList into shift0, shiftLen, and
        //  shift1. Print out the orig ArrayList and three
        //  returned ArrayList.
        System.out.println("Original ArrayList");
        System.out.println("Shift by 1");
        System.out.println("Shift by 0");
        System.out.println("Shift by length - 1");











    }
}
