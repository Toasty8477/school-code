/*
 * Course: CSC-1120 - 131
 * Lab 1 - Reading and Writing
 * Name: Alexander Horton
 * Last Updated: 1/29/2025
 */

package hortona;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Image image = null;
        String choice;
        boolean running = true;
        while (running) {
            try {
                System.out.println("\nWhat would you like to do?");
                System.out.println("(L)oad image");
                System.out.println("(S)ave image");
                System.out.println("(E)xit");
                choice = scanner.next();
                if (choice.equalsIgnoreCase("L")) {
                    System.out.print("Enter the path of the file you would like to open: ");
                    image = new Image(Path.of(scanner.next()));
                    System.out.println(image);
                } else if (choice.equalsIgnoreCase("S")) {
                    if (image == null) {
                        image = new Image(Path.of("src/hortona/dummy.txt"));
                    }
                    System.out.print("Enter the filename to save to: ");
                    String filename = scanner.next();
                    System.out.print("Which Format? (`01text` or `unicode`): ");
                    String format = scanner.next();
                    File file = Path.of(filename + ".txt").toFile();
                    if(file.exists()) {
                        System.out.println("\n"+filename+".txt already exists.");
                        System.out.print("Would you like to overwrite it? (y/n): ");
                        if (scanner.next().equalsIgnoreCase("y")) {
                            image.save(filename, format);
                        }
                    } else {
                        image.save(filename, format);
                    }
                } else {
                    running = false;
                }
            } catch (NoSuchFileException e) {
                System.out.println("The file could not be opened");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
