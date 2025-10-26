/**************************
 * Course: CSC1110 - 111
 * Fall 2024
 * ASSIGNMENT # - REPLACE ME
 * Name: Alexander Horton
 * Created 10/18/2024
 **************************/

package hortona;

public class DieTest {
    public static void main(String[] args) {
        Die d1 = new Die(20);
        Die d2 = new Die(1);
        Die d3 = new Die(200);

        System.out.println("D1");
        for (int i = 0; i < 20; i++) {
            d1.roll();
            System.out.println(d1.getCurrentValue());
        }
        System.out.println("D2");
        for (int i = 0; i < 20; i++) {
            d2.roll();
            System.out.println(d2.getCurrentValue());
        }
        System.out.println("D3");
        for (int i = 0; i < 20; i++) {
            d3.roll();
            System.out.println(d3.getCurrentValue());
        }
    }
}
