/**************************
 * Course: CSC1110 - 111
 * Fall 2024
 * ASSIGNMENT # - REPLACE ME
 * Name: Alexander Horton
 * Created 12/6/2024
 **************************/

package username;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        boolean enteringEmployees = true;

        while (enteringEmployees) {
            String employeeType;
            String name;
            int id = 0;
            String address;
            double pay = 0;
            double hours = 0;
            Scanner scan = new Scanner(System.in);
            boolean enteringDeductions = true;


            System.out.println("(H)ourly or (F)ull Time Employee?");
            employeeType = scan.next();
            System.out.println("Employee's Name?");
            name = scan.next();
            boolean validID = false;
            while (!validID) {
                try {
                    System.out.println("Employee's ID?");
                    id = scan.nextInt();
                    validID = true;
                } catch (InputMismatchException e) {
                    System.out.println("ID must be an int");
                    scan.nextLine();
                }
            }
            scan.nextLine();
            System.out.println("Employee's Address?");
            address = scan.nextLine();
            if (employeeType.equalsIgnoreCase("f")) {
                System.out.println("Salary?");
                pay = scan.nextDouble();
            } else if (employeeType.equalsIgnoreCase("h")) {
                System.out.println("Hourly Rate?");
                pay = scan.nextDouble();
                System.out.println("Hours worked?");
                hours = scan.nextDouble();
            }
            List<Deduction> deductions = new ArrayList<>();
            while (enteringDeductions) {
                System.out.println("Enter a deduction? (y/n)");
                if (scan.next().equalsIgnoreCase("y")) {
                    String deductionType;
                    double deductionAmount;
                    System.out.println("Deduction Type?");
                    deductionType = scan.next();
                    System.out.println("Deduction Amount?");
                    try {
                        deductionAmount = scan.nextDouble();
                        deductions.add(new Deduction(deductionType, deductionAmount));
                    } catch (InputMismatchException e) {
                        System.out.println("Amount was not parsable to a double");
                        scan.nextLine();
                    }
                } else {
                    enteringDeductions = false;
                    if (employeeType.equalsIgnoreCase("h")) {
                        try {
                            employees.add(new Hourly(id, name, address, pay, deductions));
                            ((Hourly)employees.getLast()).setHours(hours);
                            System.out.println();
                        } catch (InputMismatchException e) {
                            System.out.println("Unable to add employee.");
                            System.out.println(e.getMessage());
                            System.out.println();
                        }
                    } else if (employeeType.equalsIgnoreCase("f")) {
                        try {
                            employees.add(new FullTime(id, name, address, pay, deductions));
                            System.out.println();
                        } catch (IllegalArgumentException e) {
                            System.out.println("Unable to add employee.");
                            System.out.println(e.getMessage());
                            System.out.println();
                        }
                    }
                }
            }
            System.out.println("Enter another employee? (y/n)");
            if (scan.next().equalsIgnoreCase("n")) {
                enteringEmployees = false;
            }
        }
        for(Employee e: employees) {
            System.out.println(e);
            System.out.println();
        }
    }
}
