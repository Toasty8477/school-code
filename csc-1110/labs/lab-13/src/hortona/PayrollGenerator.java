/*
 * Course: CS1110 - 111
 * Fall 2024
 * Lab 13 - Payroll Processing
 * Name: Alexander Horton
 * Last Updated: 12/13/2024
 */

package hortona;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Generates the payroll for a company.
 */
public class PayrollGenerator {
    /**
     The length of the pay period in days.
     */
    public static final int PAY_PERIOD_LENGTH = 15;

    /**
     * Gets the number of hours in the timesheet file
     * between the start (inclusive) and end dates (exclusive).
     * @param filename Filename for the timesheet.
     * @param periodStart Start date.
     * @param periodEnd End date.
     * @return Hours worked between the start and end dates.
     * @throws FileNotFoundException If the timesheet file can not be opened.
     * @throws IllegalArgumentException If the extension is not .csv.
     */
    public static double getHours(String filename, LocalDate periodStart, LocalDate periodEnd)
            throws FileNotFoundException, IllegalArgumentException {
        double hours = 0;
        if (filename.contains(".csv")) {
            try {
                FileInputStream csv = new FileInputStream(filename);
                Scanner scanner = new Scanner(csv);

                scanner.nextLine();
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String[] dateTime = line.split(",");
                    final double minutesPerHour = 60;

                    if (dateTime.length == 3) {
                        try {
                            LocalDate day = LocalDate.parse(dateTime[0]);
                            if (day.equals(periodStart) ||
                                    day.isAfter(periodStart) && day.isBefore(periodEnd)) {
                                LocalTime startTime = LocalTime.parse(dateTime[1]);
                                LocalTime endTime = LocalTime.parse(dateTime[2]);
                                hours += startTime.until(endTime, ChronoUnit.MINUTES)
                                        / minutesPerHour;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Could not parse date");
                        }
                    } else {
                        System.out.println("Could not parse date");
                    }
                }
                System.out.println(hours);

            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return hours;
    }

    /**
     * Creates a List of Employees from the provided staff list.
     * @param filename .xml file for the staff list
     * @return List of Employees generated from the staff list.
     * @throws FileNotFoundException If the file can not be opened.
     * @throws IllegalArgumentException If the extension is not .xml.
     */
    public static List<Employee> readStaffList(String filename)
            throws FileNotFoundException, IllegalArgumentException {
        List<Employee> ret = new ArrayList<>();
        if (filename.contains(".xml")) {
            try {
                FileInputStream fileInputStream = new FileInputStream(filename);
                Scanner scanner = new Scanner(fileInputStream);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.equals("    <employee>")) {
                        try {
                            ret.add(XMLParser.parseEmployee(scanner));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Could not parse employee");
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException();
            }
        } else {
            throw new IllegalArgumentException();
        }

        return ret;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        List<Employee> employees;
        System.out.println("Enter filename for staff list:");
        String filename = scanner.next();

        try {
            employees = readStaffList(filename);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("The provided file was not and XML file");
        }

        System.out.println("Input the period start date int the format \"YYYY-MM-DD\":");
        LocalDate startDate;
        try {
            startDate = LocalDate.parse(scanner.next());
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Period start was not a valid date");
        }
        for (Employee e : employees) {
            if (e instanceof Hourly) {
                try {
                    ((Hourly) e).setHours(
                            getHours("timesheets/timesheet_staff" + e.getId() + ".csv",
                            startDate, startDate.plusDays(PAY_PERIOD_LENGTH)));
                } catch (IllegalArgumentException ex) {
                    throw new RuntimeException("The timesheet was not a csv");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException("Could not find timesheet for employee");
                }
                System.out.println(e.generatePayStub());
                File file = new File("payStubs");
                if (!file.exists()) {
                    file.mkdirs();
                }
                try {
                    file.createNewFile();
                } catch (IOException exc) {
                    System.out.println("File could not be created");
                }
                try (FileOutputStream fileOutputStream =
                             new FileOutputStream("payStubs/employeeID" + e.getId() + ".txt");
                        PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
                    printWriter.println(e.generatePayStub());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
