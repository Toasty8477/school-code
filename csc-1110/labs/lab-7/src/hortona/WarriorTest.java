/**************************
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 7 - Battle Simulator 3000
 * Name: Alexander Horton
 * Created 10/18/2024
 **************************/

package hortona;

public class WarriorTest {
    public static void main(String[] args) {
        Warrior player = new Warrior();

        System.out.println(player.getHitPoints());
        System.out.println(player.attack(1));
        player.takeDamage(10);
        System.out.println(player.getHitPoints());
    }
}
