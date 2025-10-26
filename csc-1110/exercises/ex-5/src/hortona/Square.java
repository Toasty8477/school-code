/**************************************
 * Course: CSC1110 - 110
 * Fall 2024
 * Exercise 5 - Simple Class and Driver
 * Name: Alexander Horton
 * Created 9/30/2024
 *************************************/

package hortona;

/**
 * This class creates a square with color and perimeter values
 */
public class Square {
    private String color;
    private double perimeter;

    public void setColor(String color){
        this.color = color;
    }
    public void setPerimeter(double perimeter){
        this.perimeter = perimeter;
    }

    /**
     * Sets both the perimeter and color of the square
     * @param perimeter perimeter of the square
     * @param color color of the square
     */
    public void setValues(double perimeter, String color){
        this.perimeter = perimeter;
        this.color = color;
    }
    public String getColor(){
        return color;
    }
    public double getPerimeter(){
        return perimeter;
    }

    /**
     * Prints the color, perimeter, and area of the square
     */
    public void printValues(){
        System.out.println("\tColor: "+color);
        System.out.println("\tPerimeter: "+perimeter);
        System.out.println("\tArea: "+Math.pow(perimeter/4, 2));
    }
    private double calcSide(){
        return perimeter/4;
    }

    /**
     * Returns the area of the square using the calcSide method
     * @return Area of the Square
     */
    public double calcArea(){
        return Math.pow(calcSide(), 2);
    }

    /**
     * Takes a square as an argument and compares the area of the passed square to the area of the square that the argument is called from.
     * @param that A Square Object
     * @return true or false
     */
    public boolean biggerThan(Square that){
        if (this.calcArea() > that.calcArea()){
            return true;
        } else {
            return false;
        }
    }
}