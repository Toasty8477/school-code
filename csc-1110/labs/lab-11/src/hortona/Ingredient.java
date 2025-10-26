/*
 * Course: CSC1110 - FIXME
 * Fall 2024
 * Lab 11 - Interfaces
 * Name: FIXME
 * Created: FIXME
 */
package hortona;

import java.text.DecimalFormat;

public interface Ingredient {
    DecimalFormat CUP_FORMAT = new DecimalFormat("0.##");

    double getCalories();
    double getCups();
    String getName();
    boolean isDry();
    void printRecipe();
}
