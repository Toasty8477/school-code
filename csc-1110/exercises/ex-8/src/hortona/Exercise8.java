/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Exercise 8
 * Name: Alexander Horton
 * Created: 10/24/2024
 */
package hortona;

import java.util.Scanner;

/**
 * Exercise for creating and interacting
 * with basic arrays.
 */
public class Exercise8 {

    /**
     * Asks the user to enter a series of numbers and
     * store them in an array. Compute and prints out
     * the max values of the numbers in the array
     * and how many of the numbers are even.
     * @param scan Scanner for getting input from the user
     * @param numValues Number of values to get from the user
     */
    private static void prompt1(Scanner scan, int numValues){
        System.out.println("You will be asked for "+numValues+" ints.");
        int[] ints = new int[numValues];
        for (int i = 0; i < ints.length; i++) {
            System.out.print("Enter an int: ");
            ints[i] = scan.nextInt();
        }
        int maxValue = ints[0];
        int evens = 0;
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] > maxValue) {
                maxValue = ints[i];
            }
            if (ints[i]%2 == 0) {
                evens++;
            }
        }
        System.out.println("Number of evens is: "+evens);
        System.out.println("Max is: "+maxValue);
    }
    /**
     * Ask the user to enter a series of words and store
     * them in an array. Print out whether any of the words
     * entered match the passed in password. Create a new
     * array of chars that holds the last letter of each
     * String. Print out each element of this new array.
     * @param scan Scanner for getting input from the user
     * @param numValues Number of values to get from the user
     * @param password Password to compare to
     */
    private static void prompt2(Scanner scan, int numValues, String password){
        System.out.println("You will be asked for "+numValues+" Strings.");
        String[] words = new String[numValues];
        boolean hasPassword = false;
        for (int i = 0; i < words.length; i++) {
            System.out.print("Enter a string: ");
            words[i] = scan.next();
        }
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(password)) {
                hasPassword = true;
            }
        }
        char[] chars = new char[words.length];
        for (int i = 0; i < words.length; i++) {
            chars[i] = words[i].charAt(words[i].length()-1);
        }
        System.out.println("Was the password entered: "+hasPassword);
        System.out.println("Array of last letters:");
        for (int i = 0; i < chars.length; i++) {
            System.out.print(chars[i]+" ");
        }
        System.out.println();
    }

    /**
     * Ask the user for how many numbers they want to enter.
     * Create a new array of that size of ints. Ask the user
     * to enter numbers and store in the array. Assume you
     * completely fill the array. Create a new array
     * that contains all the elements of the first
     * array shifted to the right by 1. Print out the elements
     * of the shifted array.
     * @param scan Scanner for getting input from the user
     */
    private static void prompt3(Scanner scan){
        System.out.println("How many numbers do you want to enter?");
        int arrayLength = scan.nextInt();
        int[] ints = new int[arrayLength];
        for (int i = 0; i < ints.length; i++) {
            System.out.print("Enter an int: ");
            ints[i] = scan.nextInt();
        }
        int[] shifted = new int[ints.length];
        for (int i = 0; i < arrayLength-1; i++) {
            shifted[i+1] = ints[i];
        }
        shifted[0] = ints[ints.length-1];
        System.out.println("First array is:");
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i]+" ");
        }
        System.out.println();
        System.out.println("Shifted array is:");
        for (int i = 0; i < shifted.length; i++) {
            System.out.print(shifted[i]+" ");
        }
        System.out.println();
    }

    /**
     * Asks the user for an int and create a new
     * array of that size. Fill the array with random
     * numbers from 1 to 10. Create a new array that
     * has the same elements as the original array
     * multiplied by -1.
     * Print out both arrays.
     * @param scan Scanner for getting input from the user
     */
    private static void prompt4(Scanner scan){
        System.out.println("Enter a length");
        int arrayLength = scan.nextInt();
        int[] ints = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            ints[i] = (int)(Math.random()*10) +1;
        }
        int[] negated = new int[ints.length];
        for (int i = 0; i < negated.length; i++) {
            negated[i] = ints[i]*-1;
        }
        System.out.println("Original array:");
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i]+" ");
        }
        System.out.println();
        System.out.println("Negated copy:");
        for (int i = 0; i < negated.length; i++) {
            System.out.print(negated[i]+" ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        prompt1(scan, 3);
        prompt2(scan, 3, "1234");
        prompt3(scan);
        prompt4(scan);
    }
}
