/*
 * Course: CS1110 - 111
 * Fall 2024
 * Lab 13 - Payroll Processing
 * Name: Alexander Horton
 * Last Updated: 12/13/2024
 */

package hortona;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for parsing an XML file.
 */
public class XMLParser {
    /**
     * Reads the data in an Employee section and uses it to create and return a new Employee object.
     * @param scanner Scanner object positioned at the start of a nested employee element
     * @return New Employee object created from the information in a nested employee element
     * @throws IllegalArgumentException if any of the tags needed to create an Employee are missing
     * @throws NumberFormatException if number values can not be parsed correctly.
     */
    public static Employee parseEmployee(Scanner scanner) throws IllegalArgumentException,
            NumberFormatException {

        int id = 0;
        String name = null;
        String address = null;
        String type = null;
        double pay = 0;
        boolean employee = true;
        List<Deduction> deductions = new ArrayList<>();

        Employee e1 = null;
        while (employee) {
            String line = scanner.nextLine();
            String token = null;
            for (int i = 0; i < line.length() && token == null; i++) {
                if (line.charAt(i) == '>') {
                    token = line.substring(0, i+1);
                    token = token.replace(" ", "");
                }
            }
            if (token.equals("<id>")) {
                String data = getData(line, token);
                boolean containsNonDigits = false;
                for (int i = 0; i < data.length() && !containsNonDigits; i++) {
                    if (!Character.isDigit(data.charAt(i))) {
                        containsNonDigits = true;
                    }
                }
                if (!containsNonDigits) {
                    id = Integer.parseInt(data);
                } else {
                    throw new NumberFormatException();
                }
            } else if (token.equals("<name>")) {
                name = getData(line, token);
            } else if (token.equals("<address>")) {
                address = getData(line, token);
            } else if (token.equals("<type>")) {
                type = getData(line, token);
            } else if (token.equals("<salary>") || token.equals("<rate>")) {
                String data = getData(line, token);
                boolean containsNonDigits = false;
                for (int i = 0; i < data.length() && !containsNonDigits; i++) {
                    if (!Character.isDigit(data.charAt(i))) {
                        if (data.charAt(i) != '.') {
                            containsNonDigits = true;
                        }
                    }
                }
                if (!containsNonDigits) {
                    pay = Double.parseDouble(data);
                }
            } else if (token.equals("<deductions>")) {
                boolean deduction = true;
                String deductionType = null;
                double deductionAmount = 0;
                while (deduction) {
                    line = scanner.nextLine();
                    token = null;
                    for (int i = 0; i < line.length() && token == null; i++) {
                        if (line.charAt(i) == '>') {
                            token = line.substring(0, i+1);
                            token = token.replace(" ", "");
                        }
                    }
                    if (token.equals("<type>")) {
                        deductionType = getData(line, token);
                    } else if (token.equals("<amount>")) {
                        String data = getData(line, token);
                        boolean containsNonDigits = false;
                        for (int i = 0; i < data.length() && !containsNonDigits; i++) {
                            if (!Character.isDigit(data.charAt(i))) {
                                if (data.charAt(i) != '.') {
                                    containsNonDigits = true;
                                }
                            }
                        }
                        if (!containsNonDigits) {
                            deductionAmount = Double.parseDouble(data);
                        }
                    } else if (token.equals("</deduction>")) {
                        deductions.add(new Deduction(deductionType, deductionAmount));
                    } else if (token.equals("</deductions>")) {
                        deduction = false;
                    }

                }
            } else if (token.equals("</employee>")) {
                employee = false;
                if (type != null && type.equals("hourly")) {
                    e1 = new Hourly(id, name, address, pay, deductions);
                } else if (type != null && type.equals("fulltime")) {
                    e1 = new FullTime(id, name, address, pay, deductions);
                } else {
                    throw new IllegalArgumentException();
                }
                id = 0;
                name = null;
                address = null;
                type = null;
                pay = 0;
            }
        }
        return e1;
    }

    private static String getData(String line, String token) {
        line = line.replace(token, "");
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '<') {
                line = line.substring(0, i);
            }
        }
        boolean charFound = false;
        for (int i = 0; i < line.length() && !charFound; i++) {
            if (Character.isLetterOrDigit(line.charAt(i))) {
                line = line.substring(i);
                charFound = true;
            }
        }

        return line;
    }

}

