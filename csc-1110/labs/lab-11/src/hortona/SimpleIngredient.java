/*
 * Course: CSC1110 - FIXME
 * Fall 2024
 * Lab 11 - Interfaces
 * Name: FIXME
 * Created: FIXME
 */
package hortona;

/**
 * FIXME
 */
public class SimpleIngredient implements Ingredient {

    private final double calories;
    private final double cups;
    private final boolean isDry;
    private final String name;

    public SimpleIngredient(double calories, double cups, boolean isDry, String name) {
        this.calories = calories;
        this.cups = cups;
        this.isDry = isDry;
        this.name = name;
    }

    @Override
    public double getCalories() {
        return calories;
    }
    @Override
    public double getCups() {
        return cups;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public boolean isDry() {
        return isDry;
    }
    @Override
    public void printRecipe() {
        System.out.println("====================================================");
        System.out.println(name);
        System.out.println("====================================================");
        if (cups != Math.round(cups)) {
            System.out.printf("Cups: %s Cups\n", CUP_FORMAT.format(cups));
        } else {
            System.out.printf("Cups: %d Cups\n", Math.round(cups));
        }
        System.out.printf("Energy: %d Calories\n", Math.round(calories));
    }
}
