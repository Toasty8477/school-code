/*
 * Course: CSC1110 - 110
 * Fall 2024
 * Lab 7 - Battle Simulator 3000
 * Name: Alexander Horton
 * Created: 10/18/2024
 */
package hortona;

/**
 * Mugwump class for battle sim 3000
 */
public class Mugwump {
    private int hitPoints;
    private int maxHitPoints;
    private Die d100;
    private Die d20;
    private Die d10;
    private Die d6;
    // add methods here

    /**
     * Sets dice and initial hp for Mugwump
     */
    public Mugwump(){
        final int d100Sides = 100;
        final int d20Sides = 20;
        final int d10Sides = 10;
        final int d6Sides = 6;
        d100 = new Die(d100Sides);
        d20 = new Die(d20Sides);
        d10 = new Die(d10Sides);
        d6 = new Die(d6Sides);

        hitPoints = setInitialHitPoints();
    }

    public int getHitPoints() {
        return hitPoints;
    }
    private int setInitialHitPoints(){
        final int diceToRoll = 6;
        int hp = 0;
        for (int i = 0; i<diceToRoll; i++) {
            d10.roll();
            hp = hp + d10.getCurrentValue();
        }
        this.maxHitPoints = hp;
        return hp;
    }

    /**
     * Handles damage for the Mugwump
     * @param damage hit points of damage taken
     */
    public void takeDamage(int damage){
        hitPoints = hitPoints - damage;
    }
    /**
     * This method handles the attack logic
     * @return the amount of damage an attack has caused, 0 if the attack misses or
     *         a negative amount of damage if the Mugwump heals itself
     */
    public int attack() {
        // get attack type from AI
        final int clawThresh = 12;
        final int biteThresh = 16;
        int damage = 0;
        int attack = ai();
        // roll attack die
        d20.roll();
        // determine results of attack
        if (attack == 1){
            if (d20.getCurrentValue() >= clawThresh) {
                for (int i = 0; i<2; i++) {
                    d6.roll();
                    damage = damage + d6.getCurrentValue();
                }
            }
            if (damage > 0) {
                System.out.printf("The mugwump sinks its claws into you dealing %d " +
                        "points of damage.\n", damage);
            } else {
                System.out.println("The mugwump tries to attack and misses.");
            }
        } else if (attack == 2) {
            if (d20.getCurrentValue() >= biteThresh) {
                for (int i = 0; i < 3; i++) {
                    d6.roll();
                    damage = damage + d6.getCurrentValue();
                }
            }
            if (damage > 0) {
                System.out.printf("The mugwump bites down on you dealing %d points of damage.\n",
                        damage);
            } else {
                System.out.println("The mugwump tries to attack and misses.");
            }
        } else if (attack == 3) {
            d10.roll();
            damage = -1 * d10.getCurrentValue();
            hitPoints = hitPoints+d10.getCurrentValue();
            if (hitPoints > maxHitPoints){
                System.out.printf("The mugwump heals itself for %d hit points.\n",
                        (-1*damage)-(hitPoints-maxHitPoints));
                hitPoints = maxHitPoints;
            } else {
                System.out.printf("The mugwump heals itself for %d hit points.\n", -1 * damage);
            }
        }
        // return the damage
        return damage;
    }

    /**
     * This method determines what action the Mugwump performs
     * @return 1 for a Claw attack, 2 for a Bite, and 3 if the Mugwump licks its wounds
     */
    private int ai(){
        final int clawThresh = 40;
        final int healChance = 15;
        int type;
        d100.roll();
        if (d100.getCurrentValue() >= clawThresh) {
            type = 1;
        } else if (d100.getCurrentValue() <= clawThresh && d100.getCurrentValue() > healChance) {
            type = 2;
        } else {
            type = 3;
        }
        return type;
    }
}
