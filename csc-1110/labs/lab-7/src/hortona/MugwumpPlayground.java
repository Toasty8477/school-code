package hortona;

public class MugwumpPlayground {
    public static void main(String[] args) {
        // testMakingMugwumps();
        // testTakingDamage();
        //testAttackChoice();
        testAttacks();
        // more tests...
    }

    /**
     * Creates twenty Mugwump objects and displays the number of hit points
     * for each mugwump.
     * <p></p>
     * When called, the method should display the hit points for each of the
     * twenty mugwump. You should verify that the twenty numbers displayed
     * are not all the same and that they are all in the expected range of values.
     */
    private static void testMakingMugwumps() {
        // Declare a variable, mugwump, of type Mugwump but do not initialize it.
        Mugwump player;
        // Create a loop that runs twenty times
        for (int i = 0; i < 20; i++) {
            // Create a new Mugwump object and assign it the player variable.
            player = new Mugwump();

            // Print out the hit points.
            System.out.println(player.getHitPoints());
        }
    }

    /**
     * Method used to verify the takeDamage() method is
     * working as expected.
     */
    private static void testTakingDamage() {
        // Initialize a new instance of a Mugwump.
        // Print out the hit points and then have it take some amount of damage like 5.
        // Print out the hit points again. Do the remaining hit points
        // make sense given the initial value and how much damage was taken.
        // Do this a couple of times with a
        // 1. Damage of 1.
        // 2. Damage equal to Mugwump's initial hit points.
        // 3. Damage equal to Mugwump's initial hit points minus 1 and then plus 1.
        // 4. Damage that is a negative number.
    }

    /**
     * Method used to verify the ai() method is working as expected.
     * <p></p>
     * In order to test this method, you'll need to temporarily change
     * the visibility modifier on the ai() method to be public, so it
     * can be called from this method. When you are done testing, you
     * should make ai() private and comment the code in this method
     * so that the compiler isn't grumpy. You can quickly comment the
     * code in this method by selecting it and then pressing CTRL-/ to
     * toggle // characters at the beginning of each line.
     */
    private static void testAttackChoice() {
        // Initialize a new instance of a Mugwump.
        // Create a loop that runs 10,000 times.
            // Each time through the loop call ai()
            // Keep track of the number of times ai() returns 1, 2, or 3
        // Verify that ai() returned 1 approximately 60% of the time
        // Verify that ai() returned 2 approximately 25% of the time
        // Verify that ai() returned 3 approximately 15% of the time
        Mugwump player = new Mugwump();
        int type1 = 0;
        int type2 = 0;
        int type3 = 0;
        for (int i = 0; i<10_000; i++){
            int attack = player.attack();
            if (attack == 1){
                type1++;
            } else if (attack == 2){
                type2++;
            } else {
                type3++;
            }
        }
        System.out.println(type1);
        System.out.println(type2);
        System.out.println(type3);
    }
    private static void testAttacks(){
        Mugwump player = new Mugwump();
        System.out.println(player.attack());
    }
}
