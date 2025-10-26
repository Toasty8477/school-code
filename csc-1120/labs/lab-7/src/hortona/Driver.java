/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 7 - Call Stack
 * Name: Alexander Horton
 * Updated: 3/11/2025
 */

package hortona;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver Class for Lab 7
 */
public class Driver {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        ProgramStack ps = new ProgramStack();

        System.out.print("Please enter the name of the input file: ");
        String filename = userInput.next();
        try(Scanner fileInput = new Scanner(new File(filename))) {

            while (fileInput.hasNext()) {
                String line = fileInput.nextLine();
                if (FileReaderUtils.parseMethodName(line).isEmpty()) {
                    if(FileReaderUtils.parseReturnValue(line).isPresent()) {
                        ps.returnFromMethod(FileReaderUtils.parseReturnValue(line).getAsInt());
                        System.out.println(line);
                        System.out.println(ps + "\n");
                    } else if (FileReaderUtils.isVoidReturn(line)) {
                        ps.returnFromMethod();
                        System.out.println(line);
                        System.out.println(ps + "\n");
                    } else {
                        System.out.println(line);
                        System.out.println("Invalid line, ignored\n");
                    }
                } else {
                    String name = FileReaderUtils.parseMethodName(line).get();
                    int[] arguments = FileReaderUtils.parseArguments(line);
                    ps.callMethod(name, arguments);
                    System.out.println(line);
                    System.out.println(ps + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
