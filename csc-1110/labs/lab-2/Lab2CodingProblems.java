/*
 * Course: CS 1021
 * Winter 2021
 * Demo
 * Name: Roby Velez
 * Created: 7/30/2022
 */

/**
 * Lab 2: Simple coding problems. Add code to the methods below so that
 * all the tests in main() pass and produce green text.
 */
public class Lab2CodingProblems {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Returns the area of a triangle give its base and
     * height. Assumes area is one half base times height.
     * @param base of a triangle
     * @param height height of a triangle
     * @return area of the triangle
     */
    public static double triangleArea(int base, int height){
        return base * height / 2.0;
    }

    /**
     * Returns a String that is a sandwich of Strings
     * a and b. For example sandwich("foo","bar") => "foobarfoo"
     * or sandwich("bread","cheese") -> "breadcheesebread"
     * @param a String that will go on the outside of the sandwich
     * @param b String that will go on the inside of the sandwich
     * @return Final composite String
     */
    public static String sandwich(String a, String b){
        return a + b + a;
    }

    /**
     * Returns the slope of the line between two points (x1, y1)
     * and (x2, y2) using the slope formula. Don't worry
     * about division by 0.
     * @param x1 x location of one end of a line
     * @param y1 y location of one end of a line
     * @param x2 x location of the other end of a line
     * @param y2 y location of the other end of a line
     * @return Slope of the line
     */
    public static double slope(int x1, int y1, int x2, int y2){
        return (double)(y2 - y1) / (x2 - x1);
    }

    /**
     * Converts the floating point number into a percentage String
     * For example toPercentage(0.12) => "12%" or toPercentage(1)
     * => "100%" or toPercentage(.14968) => "14%"
     * @param percent double that represents a percent
     * @return String representation of the percent
     */
    public static String toPercentage(double percent){
        return (int)(percent * 100) + "%";
    }

    /**
     * Parses the passed in String and pulls out the floating point
     * number for the height in meters. Converts the height to inches
     * using the provided conversion. Returns the resulting inches as
     * a double. For example toInches("2.5m") => 98.5 or
     * toInches("0.001m") => 0.0394
     * @param height height of a person
     * @return height of a person in inches
     */
    public static double toInches(String height){
        final double conversion = 39.4;
	double extractedHeight = Double.parseDouble(height.substring(0, (height.length()-1)));
	double heightInches = extractedHeight * conversion;
        return heightInches;
    }

    /**
     * Returns a String that shows the equation for a line given
     * the passed in slope and intercepts. For example lineEquation(4, -2) ->
     * "y = 4.0x + -2.0" or lineEquation(1.3, 10) -> "y = 1.3x + 10.0"
     * @param slope of a line used in the slope line equation
     * @param intercept of a line used in the slope line equation
     * @return The equation for a line as a String
     */
    public static String lineEquation(double slope, double intercept){
        return "y = " + slope + "x + " + intercept;
    }

    /**
     * Returns the middle letter of a passed in String.
     * For example middleLetter("World") -> 'r' or
     * middleLetter("taco") -> 'c'
     * @param word Generic String that can be of any length
     * @return char that is the middle of the passed in word
     */
    public static char middleLetter(String word){
        return word.charAt(word.length()/2);
    }

    /**
     * Creates a message from the passed in first and last name.
     * makeMsg("Jane","Smith") -> "My name is Jane Smith and my
     * initials are J.S."
     * @param first name of a person
     * @param last name of a person
     * @return Message the uses the first and last name
     */
    public static String makeMsg(String first, String last){
        return "My name is " + first + " " + last + " and my initials are " + first.charAt(0) + "." + last.charAt(0) + ".";
    }

    /**
     * Converts the passed in number of hours, minutes, and second
     * into just seconds. For example toSeconds(0,0,1) -> 1 or
     * toSeconds(0,1,0) -> 60 or toSeconds(1,0,0) -> 360 or
     * toSeconds(1,1,1) -> 3661
     * @param hours number of hours as an int
     * @param minutes number of minutes as an int
     * @param seconds number of seconds as an int
     * @return total number of seconds of the hours, minutes, and seconds passed in
     */
    public static int toSeconds(int hours, int minutes, int seconds){
        return ((hours * 60) * 60) + (minutes * 60) + seconds;
    }

    /**
     * Breaks the passed in seconds into hours, minutes, and seconds
     * and returns a String that displays those values
     * For example timeBreakDown(120) -> "hours:0,minutes:2,seconds:0"
     * or timeBreakDown(3661) -> "hours:1,minutes:1,seconds:1"
     * @param seconds number of seconds as an int
     * @return String that formats the seconds into hours, minutes, and seconds
     */
    public static String timeBreakDown(int seconds){
	int minutes = (seconds % 3600)/60;
	int hours = seconds / 3600;
        return "hours:" + hours + ",minutes:" + minutes + ",seconds:" + ((seconds % 3600) % 60);
    }

    /**
     * Test the various methods above.
     * DO NOT EDIT ANY OF THE CODE IN THIS METHOD
     * @param args Command line arguments if any
     */
    public static void main(String[] args) {

        System.out.println("\nTesting triangleArea");
        testMethod(triangleArea(4, 3),6.0);
        testMethod(triangleArea(5, 5), 12.5);
        testMethod(triangleArea(12, 3),18.0);
        testMethod(triangleArea(2, 7), 7.0);
        testMethod(triangleArea(9, 5), 22.5);

        System.out.println("\nTesting sandwich");
        testMethod(sandwich("bread", "cheese"), "breadcheesebread");
        testMethod(sandwich("taco", "cat"), "tacocattaco");
        testMethod(sandwich("", "a"), "a");
        testMethod(sandwich("pop", " "), "pop pop");
        testMethod(sandwich("", ""), "");
        testMethod(sandwich("0", "_"), "0_0");

        System.out.println("\nTesting slope");
        testMethod(slope(1, 1, 4, 4), 1.0);
        testMethod(slope(1, 4, 4, 1), -1.0);
        testMethod(slope(-4, 4, 6, -8), -1.2);
        testMethod(slope(0, 0, -2, -10), 5.0);
        testMethod(slope(-1, -3, -3, 18), -10.5);
        testMethod(slope(-3, 5, 5, 5), 0.0);

        System.out.println("\nTesting toPercentage");
        testMethod(toPercentage(1.0), "100%");
        testMethod(toPercentage(0.1999), "19%");
        testMethod(toPercentage(1.23), "123%");
        testMethod(toPercentage(0.0), "0%");
        testMethod(toPercentage(0.50), "50%");
        testMethod(toPercentage(-0.43), "-43%");

        System.out.println("Testing to inches\n");
        testMethod(toInches("2.5m"), 98.5);
        testMethod(toInches("-0.23m"), -9.062);
        testMethod(toInches("32.3m"), 1272.62);
        testMethod(toInches("6.1234m"), 241.26196);
        testMethod(toInches("0.001m"), 0.0394);
        testMethod(toInches("1000m"), 39400.0);

        System.out.println("Testing lineEquation\n");
        testMethod(lineEquation(2, - 5), "y = 2.0x + -5.0");
        testMethod(lineEquation(0.25, 10), "y = 0.25x + 10.0");
        testMethod(lineEquation(14, 10.2), "y = 14.0x + 10.2");
        testMethod(lineEquation(-1, 2), "y = -1.0x + 2.0");
        testMethod(lineEquation(-4.11, -8), "y = -4.11x + -8.0");
        testMethod(lineEquation(0, 0), "y = 0.0x + 0.0");

        System.out.println("Testing middleLetter\n");
        testMethod(middleLetter("taco"), 'c');
        testMethod(middleLetter("cat"), 'a');
        testMethod(middleLetter("12345"), '3');
        testMethod(middleLetter("123456"), '4');
        testMethod(middleLetter("0_0"), '_');
        testMethod(middleLetter(" "), ' ');
        testMethod(middleLetter("hello world"), ' ');

        System.out.println("Testing makeMsg\n");
        testMethod(makeMsg("Ellen", "Ripley"), "My name is Ellen Ripley and my initials are E.R.");
        testMethod(makeMsg("Ben", "Sisko"), "My name is Ben Sisko and my initials are B.S.");
        testMethod(makeMsg("Emmet", "Brown"), "My name is Emmet Brown and my initials are E.B.");
        testMethod(makeMsg("Rick", "Deckard"), "My name is Rick Deckard and my initials are R.D.");
        testMethod(makeMsg("Alex", "Murphy"), "My name is Alex Murphy and my initials are A.M.");
        testMethod(makeMsg("Sarah", "Connor"), "My name is Sarah Connor and my initials are S.C.");
        testMethod(makeMsg(" ", " "), "My name is     and my initials are  . .");

        System.out.println("Testing toSeconds\n");
        testMethod(toSeconds(1, 1, 1), 3661);
        testMethod(toSeconds(0, 0, 1), 1);
        testMethod(toSeconds(1, 0, 0), 3600);
        testMethod(toSeconds(0, 1, 0), 60);
        testMethod(toSeconds(23, 59, 59), 86399);
        testMethod(toSeconds(0, 0, 0), 0);

        System.out.println("Testing timeBreakDown\n");
        testMethod(timeBreakDown(3661), "hours:1,minutes:1,seconds:1");
        testMethod(timeBreakDown(1), "hours:0,minutes:0,seconds:1");
        testMethod(timeBreakDown(3600), "hours:1,minutes:0,seconds:0");
        testMethod(timeBreakDown(60), "hours:0,minutes:1,seconds:0");
        testMethod(timeBreakDown(86399), "hours:23,minutes:59,seconds:59");
        testMethod(timeBreakDown(0), "hours:0,minutes:0,seconds:0");
    }

    /**
     * Helper method for testing the methods. Prints out red text if the resulting
     * output matches the expected output and green otherwise.
     */
    private static <T extends Comparable<T>> void testMethod(T result, T expected){
        if(result != null && result.compareTo(expected) == 0) {
            System.out.print(ANSI_GREEN);
        } else {
            System.out.print(ANSI_RED);
        }
        System.out.println("Output should be " + expected + ". Got " + result + ".");
        System.out.print(ANSI_RESET);
    }
}
