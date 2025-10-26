/*
 * Course: CSC1110 - FIXME
 * Fall 2024
 * Lab 7 - Battle Simulator 3000
 * Name: FIXME
 * Created: FIXME
 */
package hortona;

import java.util.Scanner;

/**
 * BattleSim Driver for Battle Simulator 3000
 */
public class BattleSim {
    /**
     * Ten-sided die to be used for checking initiative by all classes
     */
    public static final Die INITIATIVE_DIE = new Die(10);

    public static void main(String[] args) {
        // Local variables
        // Include any variable that will need to be accessed throughout the program here

        // sentinel value for the game loop
        boolean play = true;
        // String used to determine the winner of the epic battle
        String victor = "";
        // game loop
        do {
            // print the introduction and rules
            intro();

            // initialize game
            // Initialize the Warrior and Mugwump classes, set the current victor to "none"
            Warrior warrior = new Warrior();
            Mugwump mugwump = new Mugwump();
            Scanner in = new Scanner(System.in);
            victor = "none";


            // while neither combatant has lost all of their hit points, report status and battle!
            while (victor.equalsIgnoreCase("none")) {
                report(warrior, mugwump);
                victor = battle(warrior, mugwump, in);
            }
            // declare the winner
            victory(victor);
            // ask to play again
            play = playAgain(in);
        } while(play);
        // Thank the user for playing your game
        System.out.println("Thank you for playing Battle Simulator 3000!");
    }

    /**
     * This method displays the introduction to the game and gives a description of the rules.
     */
    private static void intro() {
        // Write a suitable introduction to your game
        System.out.println("Welcome to Battle Simulator 3000!" +
                " The world's more low tech battle simulator!");
        System.out.println("You are a Valiant Warrior defending " +
                "your humble village from an evil Mugwump!");
        System.out.println("Fight bravely, or the citizens of " +
                "your town will be the Mugwump's dinner!");
        System.out.println("\nYou have your Trusty Sword, which deals decent damage," +
                " but can be tough to hit with sometimes.");
        System.out.println("You also have your Shield of Light, " +
                "which is not as strong as your sword, but is easier to deal damage with.");
        System.out.println("\nLet the battle begin!");
    }

    /**
     * This method handles the battle logic for the game.
     * @param warrior The Warrior of Light!
     * @param mugwump The Evil Mugwump!
     * @return The name of the victor, or "none", if the battle is still raging on
     */
    private static String battle(Warrior warrior, Mugwump mugwump, Scanner in) {
        String victor = "none";
        // determine who attacks first (Roll! For! Initiative!) and store the result
        int firstAttack = initiative();
        // attack code
        // If the Warrior attacks first
        if (firstAttack == 1) {
            System.out.println("How would you like to attack?");
            System.out.println("1. Your Trusty Sword");
            System.out.println("2. Your Shield of Light");
            int type = attackChoice(in);
            // Warrior attacks and assigns the resulting damage to the mugwump
            mugwump.takeDamage(warrior.attack(type));
            // Check if the Mugwump has been defeated
            // If not, Mugwump attacks!
            if (mugwump.getHitPoints() > 0) {
                warrior.takeDamage(mugwump.attack());
                if (warrior.getHitPoints() <= 0) {
                    victor = "mugwump";
                }
            } else {
                // Otherwise, the Warrior wins!
                victor = "warrior";
            }
        }
        // Otherwise the Mugwump is first
        // see above
        if (firstAttack == 2) {
            warrior.takeDamage(mugwump.attack());
            if (warrior.getHitPoints() > 0) {
                System.out.println("How would you like to attack?");
                System.out.println("1. Your Trusty Sword");
                System.out.println("2. Your Shield of Light");
                int type = attackChoice(in);
                mugwump.takeDamage(warrior.attack(type));
                if (mugwump.getHitPoints() <= 0) {
                    victor = "warrior";
                }
            } else {
                victor = "mugwump";
            }
        }
        // If neither combatant is defeated, the battle rages on!
        return victor;
    }

    /**
     * This method reports the status of the combatants
     * @param warrior The Warrior of Light!
     * @param mugwump The Evil Mugwump!
     */
    private static void report(Warrior warrior, Mugwump mugwump) {
        System.out.printf("\nWarrior HP: %d\n", warrior.getHitPoints());
        System.out.printf("Mugwump HP: %d\n\n", mugwump.getHitPoints());
    }

    /**
     * This method asks the user what attack type they want to use and returns the result
     * @return 1 for sword, 2 for shield
     */
    private static int attackChoice(Scanner in) {
        int type = 0;
        do {
            System.out.print("Enter your choice: ");
            if (in.hasNextInt()) {
                type = in.nextInt();
            } else {
                in.nextLine();
            }
        } while (!(type == 1 || type == 2));
        return type;
    }

    /**
     * Determines which combatant attacks first and returns the result. In the case of a tie,
     * re-roll.
     * @return 1 if the warrior goes first, 2 if the mugwump goes first
     */
    private static int initiative() {
        // roll for initiative for both combatants
        // until one initiative is greater than the other
        int warriorInitiative = 0;
        int mugwumpInitiative = 0;
        int first = 0;
        while (warriorInitiative == mugwumpInitiative) {
            INITIATIVE_DIE.roll();
            warriorInitiative = INITIATIVE_DIE.getCurrentValue();
            INITIATIVE_DIE.roll();
            mugwumpInitiative = INITIATIVE_DIE.getCurrentValue();
        }
        if (warriorInitiative > mugwumpInitiative) {
            first = 1;
            System.out.println("The Warrior attacks first!");
        } else if (warriorInitiative < mugwumpInitiative) {
            first = 2;
            System.out.println("The Mugwump attacks first!");
        }
        return first;
    }

    /**
     * This method declares the victor of the epic battle
     * @param victor the name of the victor of the epic battle
     */
    private static void victory(String victor) {
        if (victor.equalsIgnoreCase("warrior")) {
            System.out.println("\nThe citizens cheer and invite you back to town for a " +
                    "feast as thanks for saving them (again)!");
        } else if (victor.equalsIgnoreCase("mugwump")) {
            System.out.println("\nThe mugwump walks past your limp body into the village. " +
                    "You have failed the townsfolk...");
        }
    }

    /**
     * This method asks the user if they would like to play again
     * @param in Scanner
     * @return true if yes, false otherwise
     */
    private static boolean playAgain(Scanner in) {
        boolean play = false;
        System.out.print("\nWould you like to play again? (yes/no): ");
        String choice = in.next();
        if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")){
            play = true;
        }
        return play;
    }
}
