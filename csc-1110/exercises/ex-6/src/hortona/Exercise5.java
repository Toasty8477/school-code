/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Exercise 5 - Dice
 * Name: Alexander Horton
 * Last Updated: 10/10/2024
 */

package hortona;

/**
 * Driver class for some dice
 */
public class Exercise5 {
    public static void main(String[] args) {
        final int twentySides = 20;
        Die coin = new Die(2);
        Die d6 = new Die();
        Die d20 = new Die(twentySides);

        System.out.println("Flipping a coin: " + flipCoin(coin));
        System.out.println("Rolling a d6 and d20: " + rollTwoDice(d6, d20));
    }

    private static String flipCoin(Die coin) {
        String side;
        if (coin.roll() == 1) {
            side = "Heads";
        } else {
            side = "Tails";
        }
        return side;
    }

    private static int rollTwoDice(Die d1, Die d2) {
        d1.roll();
        d2.roll();
        return d1.getCurrentValue() + d2.getCurrentValue();
    }
}