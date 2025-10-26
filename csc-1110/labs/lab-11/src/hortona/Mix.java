/*
 * Course: CSC1110 - FIXME
 * Fall 2024
 * Lab 11 - Interfaces
 * Name: FIXME
 * Created: FIXME
 */
package hortona;

import java.util.ArrayList;
import java.util.List;

/**
 * FIXME
 */
public class Mix implements Ingredient {
    private final List<Ingredient> ingredients;
    private final String name;

    public Mix(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
    public boolean hasDryIngredient() {
        boolean ret = false;
        for(int i = 0; i<ingredients.size() && !ret; i++) {
            if(ingredients.get(i).isDry()) {
                ret = true;
            }
        }
        return ret;
    }
    public boolean hasWetIngredient() {
        boolean ret = false;
        for(int i = 0; i<ingredients.size() && !ret; i++) {
            if(!ingredients.get(i).isDry()) {
                ret = true;
            }
        }
        return ret;
    }
    @Override
    public double getCalories() {
        double ret = 0;
        for(Ingredient i: ingredients) {
            ret += i.getCalories();
        }
        return ret;
    }

    @Override
    public double getCups() {
        double ret = 0;
        for(Ingredient i: ingredients) {
            ret += i.getCups();
        }
        return ret;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isDry() {
        return hasDryIngredient() && !hasWetIngredient();
    }

    @Override
    public void printRecipe() {
        System.out.println("====================================================");
        System.out.println(getName());
        System.out.println("====================================================");
        if(hasDryIngredient()) {
            System.out.println("Dry Ingredients:");
            for(Ingredient i: ingredients) {
                if(i.isDry()) {
                    System.out.printf("  %s\n", i.getName());
                }
            }
            System.out.println();
        }
        if(hasWetIngredient()) {
            System.out.println("Wet Ingredients:");
            for(Ingredient i: ingredients) {
                if(!i.isDry()) {
                    System.out.printf("  %s\n", i.getName());
                }
            }
            System.out.println();
        }
        if (getCups() != Math.round(getCups())) {
            System.out.printf("Cups: %s Cups\n", CUP_FORMAT.format(getCups()));
        } else {
            System.out.printf("Cups: %d Cups\n", Math.round(getCups()));
        }
        System.out.printf("Energy: %d Calories\n", Math.round(getCalories()));
        System.out.println();
        for(Ingredient i: ingredients) {
            i.printRecipe();
            System.out.println();
        }
    }
}
