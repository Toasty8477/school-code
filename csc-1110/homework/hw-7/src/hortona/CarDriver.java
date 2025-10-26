/*
 * Course: CSC1110 - FIXME
 * Fall 2024
 * Homework 7
 * Name: FIXME
 * Last Updated: FIXME
 */
package hortona;

/**
 * This class demonstrates copying an object
 */
public class CarDriver {
    public static void main(String[] args) {
        Car johnCar = new Car();
        Car jordanCar;

        johnCar.setMake("Honda");
        final int year = 2015;
        johnCar.setYear(year);
        johnCar.setColor("silver");
        jordanCar = johnCar.makeCopy();
        jordanCar.setColor("peach");
        System.out.println("John's car:");
        johnCar.display();
        System.out.println("Jordan's car:");
        jordanCar.display();
    }
}
