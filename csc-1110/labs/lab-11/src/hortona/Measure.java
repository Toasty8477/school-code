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
public class Measure implements Ingredient {
    private final Ingredient baseIngredient;
    private final int numerator;
    private final int denominator;

    public Measure(int numerator, int denominator, Ingredient ingredient) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.baseIngredient = ingredient;
    }
    public Measure(int numerator, Ingredient ingredient) {
        this(numerator, 1, ingredient);
    }

    @Override
    public double getCalories() {
        return (double)numerator/denominator * baseIngredient.getCalories() / baseIngredient.getCups();
    }

    @Override
    public double getCups() {
        return (double)numerator/denominator;
    }

    @Override
    public String getName() {
        return formatQuantity() + baseIngredient.getName();
    }

    @Override
    public boolean isDry() {
        return baseIngredient.isDry();
    }

    @Override
    public void printRecipe() {
        System.out.println("====================================================");
        System.out.println(getName());
        System.out.println("====================================================");
        System.out.printf("Measured ingredient: %s\n", baseIngredient.getName());
        System.out.printf("Quantity: %s(%s Cups)\n", formatQuantity(), Ingredient.CUP_FORMAT.format(
                getCups()));
        System.out.printf("Energy: %s Calories\n", Math.round(getCalories()));
        System.out.println();
        baseIngredient.printRecipe();
    }

    private String formatQuantity() {
        if (denominator == 1 && numerator != 1) {
            return numerator + " Cups ";
        } else if (denominator != 1 && numerator > 1) {
            return numerator + "/" + denominator + " Cups ";
        } else if (numerator == 1 && denominator == 1) {
            return "1 Cup ";
        } else {
            return numerator + "/" + denominator + " Cup ";
        }
    }
}
