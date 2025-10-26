/************************
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 5 - Growth Rates
 * Name: Alexander Horton
 * Created 10/4/2024
 ***********************/

package hortona;

import java.util.Scanner;

public class GrowthRate {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        boolean playAgain;

        do {
            int promotionTime = (int) (Math.random() * 11) + 1;
            int linearRate = (int) (Math.random() * 19) + 1;
            int linearTotal = 0;
            int exponentialTotal = 0;
            int previousMonth = 1;

            // preamble and choice
            System.out.println("\nYou won free ice cream in a local contest but you don't know for how long.");
            System.out.println("The company providing the ice cream offers you one of two ways to claim your prize.");
            System.out.println("Option 1: You get " + linearRate + " pints of ice cream every month.");
            System.out.println("Option 2: You get 1 pint of ice cream the first month and then doubled every month after.");
            System.out.print("Choose and option (1/2): ");
            int choice = scan.nextInt();

            // Prints table header
            System.out.println("\nMonth  #  |  Option 1 Total  |  Option 2 Total");

            // find totals
            for (int i = 1; i <= promotionTime; i++) {
                linearTotal = linearTotal + linearRate;
                exponentialTotal = exponentialTotal + previousMonth;
                previousMonth = previousMonth * 2;
                // adds a row to the table
                if (linearTotal > exponentialTotal){
                    System.out.printf("Month %2d  |  %8d Pints  |  %8d Pints\n", i, linearTotal, exponentialTotal);
                }
            }

            // output depending on results
            System.out.println("\nYou chose Option " + choice);
            System.out.println("You got ice cream for " + promotionTime + " months");
            if (choice == 1) {
                if (linearTotal < exponentialTotal) {
                    System.out.printf("You missed out on %d pints of ice cream.", exponentialTotal - linearTotal);
                } else {
                    System.out.printf("You got %d more pints than option 2.", linearTotal - exponentialTotal);
                }
            } else if (choice == 2) {
                if (linearTotal > exponentialTotal) {
                    System.out.printf("You missed out on %d pints of ice cream.", linearTotal - exponentialTotal);
                } else {
                    System.out.printf("You got %d more pints of ice cream than option 1.", exponentialTotal - linearTotal);
                }
            }

            System.out.print("\nDo you want to play again? (y/n): ");
            playAgain = scan.next().equalsIgnoreCase("y");

        } while (playAgain);
    }
}