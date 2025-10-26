/*
 * Course: CSC1110 - FIXME
 * Fall 2024
 * Lab 13 - Payroll Processing
 * Name: FIXME
 * Created: FIXME
 */
package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import hortona.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Test Suite for the PayrollGenerator and XMLParser
 */
public class Lab13UnitTests {
    @Test
    @DisplayName("Testing Parsing Employee1")
    void testParsingEmployee1() {
        Scanner scanner = new Scanner("""
            <employee>
                <id>2</id>
                <name>Talia</name>
                <address>123 Muddy Road, Denmarsh</address>
                <type>hourly</type>
                <salary>25.50</salary>
            </employee>""");
        scanner.nextLine(); //Burn the first line
        Employee e1 = XMLParser.parseEmployee(scanner);
        Assertions.assertEquals(2, e1.getId());
        Assertions.assertEquals("Talia", e1.getName());
        Assertions.assertEquals("123 Muddy Road, Denmarsh", e1.getAddress());
        Assertions.assertTrue(e1 instanceof Hourly);
        ((Hourly) e1).setHours(40);
        Assertions.assertEquals(25.50, ((Hourly)e1).getRate());
        String str1 = """
            Employee ID: 2
            \tName: Talia
            \tAddress: 123 Muddy Road, Denmarsh
            \tType: Hourly
            \tRate: 25.50
                """;
        Assertions.assertEquals(str1, e1.toString());
        String str2 = """
            Employee ID: 2
            \tName: Talia
            \tAddress: 123 Muddy Road, Denmarsh
            \tType: Hourly
            \tRate: 25.50
            Payment Details:
            \tHours:                 40.00
            \tRate:                  25.50
            \tGross Pay:           1020.00
            \tNet Pay:             1020.00
                """;
        Assertions.assertEquals(str2, e1.generatePayStub());
    }
    @Test
    @DisplayName("Testing Parsing Employee2")
    void testParsingEmployee2() {
        Scanner scanner = new Scanner("""
            <employee>
                <id>3</id>
                <name>William Goodfare</name>
                <nickname>Billy</nickname>
                <age>12</age>
                <address>Goodfare Market, Denmarsh</address>
                <email>Billy@pDenmarshVillage.com</email>
                <type>fulltime</type>
                <salary>12000</salary>
                <title>Mischief Maker</title>
                <deductions>
                    <deduction>
                        <type>Berry snacks</type>
                        <amount>75.50</amount>
                    </deduction>
                    <deduction>
                            <type>Sick day fund</type>
                            <amount>50</amount>
                    </deduction>
                    <deduction>
                            <type>Missing cart tax</type>
                            <amount>100</amount>
                    </deduction>
                </deductions>
            </employee>""");
        scanner.nextLine(); //Burn the first line
        Employee e1 = XMLParser.parseEmployee(scanner);
        Assertions.assertEquals(3, e1.getId());
        Assertions.assertEquals("William Goodfare", e1.getName());
        Assertions.assertEquals("Goodfare Market, Denmarsh", e1.getAddress());
        Assertions.assertTrue(e1 instanceof FullTime);
        Assertions.assertEquals(12000, ((FullTime)e1).getSalary());
        String str1 = """
            Employee ID: 3
            \tName: William Goodfare
            \tAddress: Goodfare Market, Denmarsh
            \tType: FullTime
            \tSalary: 12000.00
                """;
        Assertions.assertEquals(str1, e1.toString());
        String str2 = """
            Employee ID: 3
            \tName: William Goodfare
            \tAddress: Goodfare Market, Denmarsh
            \tType: FullTime
            \tSalary: 12000.00
            Deductions:
            \tMissing cart tax      100.00
            \tBerry snacks           75.50
            \tSick day fund          50.00
            \tTotal                 225.50
            Payment Details:
            \tSalary:             12000.00
            \tGross Pay:            500.00
            \tNet Pay:              274.50
                """;
        Assertions.assertEquals(str2, e1.generatePayStub());
    }
    @Test
    @DisplayName("Testing Reading Hours")
    void testSortingDeduction() throws FileNotFoundException {
        String startDate = "2024-01-01";
        LocalDate date1 = LocalDate.parse(startDate);
        LocalDate date2 = date1.plusDays(PayrollGenerator.PAY_PERIOD_LENGTH);
        LocalDate date3 = date2.plusDays(PayrollGenerator.PAY_PERIOD_LENGTH);
        LocalDate date4 = date3.plusDays(PayrollGenerator.PAY_PERIOD_LENGTH);
        LocalDate date5 = date4.plusDays(PayrollGenerator.PAY_PERIOD_LENGTH);

        Assertions.assertEquals(16.5,
                PayrollGenerator.getHours("timesheets/timesheet_staff1.csv", date1, date2));
        Assertions.assertEquals(31.5,
                PayrollGenerator.getHours("timesheets/timesheet_staff1.csv", date2, date3));
        Assertions.assertEquals(31,
                PayrollGenerator.getHours("timesheets/timesheet_staff1.csv", date3, date4));
        Assertions.assertEquals(16,
                PayrollGenerator.getHours("timesheets/timesheet_staff1.csv", date4, date5));

        Assertions.assertThrows(FileNotFoundException.class,
                () -> PayrollGenerator.getHours("timesheets/bad_file.csv", date4, date5));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> PayrollGenerator.getHours("timesheets/timesheet_staff1.txt", date4, date5));
    }
    @Test
    @DisplayName("Testing Reading Hours")
    void testParseEmployee() {
        Scanner badID = new Scanner("""
            <employee>
                <id>three</id>
                <name>William Goodfare</name>
                <age>12</age>
                <address>Goodfare Market, Denmarsh</address>
                <email>Billy@pDenmarshVillage.com</email>
                <type>fulltime</type>
                <salary>12000</salary>
                <deductions>
                    <deduction>
                        <type>Berry snacks</type>
                        <amount>75.50</amount>
                    </deduction>
                    <deduction>
                            <type>Sick day fund</type>
                            <amount>50</amount>
                    </deduction>
                    <deduction>
                            <type>Missing cart tax</type>
                            <amount>100</amount>
                    </deduction>
                </deductions>
            </employee>""");
        badID.nextLine(); //Burn the first line
        Assertions.assertThrows(NumberFormatException.class, () -> XMLParser.parseEmployee(badID));

        Scanner noName = new Scanner("""
            <employee>
                <id>3</id>
                <age>12</age>
                <address>Goodfare Market, Denmarsh</address>
                <email>Billy@pDenmarshVillage.com</email>
                <type>fulltime</type>
                <salary>12000</salary>
                <deductions>
                    <deduction>
                        <type>Berry snacks</type>
                        <amount>75.50</amount>
                    </deduction>
                    <deduction>
                            <type>Sick day fund</type>
                            <amount>50</amount>
                    </deduction>
                    <deduction>
                            <type>Missing cart tax</type>
                            <amount>100</amount>
                    </deduction>
                </deductions>
            </employee>""");
        noName.nextLine(); //Burn the first line
        Assertions.assertThrows(IllegalArgumentException.class, () -> XMLParser.parseEmployee(noName));

        Scanner missingType = new Scanner("""
            <employee>
                <id>3</id>
                <name>William Goodfare</name>
                <age>12</age>
                <address>Goodfare Market, Denmarsh</address>
                <email>Billy@pDenmarshVillage.com</email>
                <salary>12000</salary>
                <deductions>
                    <deduction>
                        <type>Berry snacks</type>
                        <amount>75.50</amount>
                    </deduction>
                    <deduction>
                            <type>Sick day fund</type>
                            <amount>50</amount>
                    </deduction>
                    <deduction>
                            <type>Missing cart tax</type>
                            <amount>100</amount>
                    </deduction>
                </deductions>
            </employee>""");
        missingType.nextLine(); //Burn the first line
        Assertions.assertThrows(IllegalArgumentException.class, () -> XMLParser.parseEmployee(missingType));
    }
}

