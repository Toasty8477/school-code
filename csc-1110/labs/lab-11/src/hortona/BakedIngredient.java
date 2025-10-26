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
public class BakedIngredient implements Ingredient {

    private final Ingredient baseIngredient;
    private final double expansionFactor;

    public BakedIngredient(Ingredient ingredient, double expansionFactor) {
        this.baseIngredient = ingredient;
        this.expansionFactor = expansionFactor;
    }

    @Override
    public double getCalories() {
        return baseIngredient.getCalories();
    }

    @Override
    public double getCups() {
        return baseIngredient.getCups() * expansionFactor;
    }
    @Override
    public String getName() {
        return "Baked " + baseIngredient.getName();
    }

    @Override
    public boolean isDry() {
        return true;
    }
    @Override
    public void printRecipe() {
        System.out.println("====================================================");
        System.out.println(getName());
        System.out.println("====================================================");
        System.out.printf("Ingredient to be baked: %s\n", baseIngredient.getName());
        System.out.printf("Cups: %s Cups\n", Ingredient.CUP_FORMAT.format(
                baseIngredient.getCups()*expansionFactor));
        System.out.printf("Energy: %d Calories\n", Math.round(baseIngredient.getCalories()));
        System.out.println();
        baseIngredient.printRecipe();
        System.out.println();
    }
}
