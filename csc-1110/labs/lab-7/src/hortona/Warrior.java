/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 7 - Battle Simulator 3000
 * Name: Alexander Horton
 * Created: 10/18/2024
 */
package hortona;

/**
 * Warrior class for battle sim 3000
 */
public class Warrior {
    private int hitPoints;
    private Die d20;
    private Die d10;
    private Die d8;
    private Die d4;

    /**
     * Sets dice and initial hp for warrior
     */
    public Warrior(){
        final int d20Sides = 20;
        final int d10Sides = 10;
        final int d8Sides = 8;
        final int d4Sides = 4;
        d20 = new Die(d20Sides);
        d10 = new Die(d10Sides);
        d8 = new Die(d8Sides);
        d4 = new Die(d4Sides);
        hitPoints = setInitialHitPoints();
    }
    public int getHitPoints(){
        return hitPoints;
    }

    /**
     * handles warrior taking damage
     * @param damage hit points of damage taken
     */
    public void takeDamage(int damage){
        if (damage > 0) {
            hitPoints = hitPoints - damage;
        }
    }

    /**
     * handles warrior's attack logic
     * @param type type of attack to use
     * @return damage dealt. 0 if attack missed.
     */
    public int attack(int type){
        final int swordThresh = 13;
        final int shieldThresh = 5;
        int damage = 0;
        if (type == 1) {
            d20.roll();
            if (d20.getCurrentValue() >= swordThresh){
                for (int i = 0; i<2; i++) {
                    d8.roll();
                    damage = damage + d8.getCurrentValue();
                }
            }
            if (damage > 0) {
                System.out.printf("You slash the mugwump with your sword dealing %d " +
                        "points of damage.\n", damage);
            } else {
                System.out.println("You swing your sword at the mugwump but it doesn't connect.");
            }
        } else if (type == 2) {
            d20.roll();
            if (d20.getCurrentValue() >= shieldThresh){
                d4.roll();
                damage = d4.getCurrentValue();
            }
            if (damage > 0) {
                System.out.printf("You slam the mugwump with your shield dealing %d " +
                        "points of damage.\n", damage);
            } else {
                System.out.println("You try to hit the mugwump with your shield but miss.");
            }
        }
        return damage;
    }
    private int setInitialHitPoints(){
        int hp = 0;
        for (int i = 0; i<4; i++) {
            d10.roll();
            hp = hp + d10.getCurrentValue();
        }
        return hp;
    }
}
