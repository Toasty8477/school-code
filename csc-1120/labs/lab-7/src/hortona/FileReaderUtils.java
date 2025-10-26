/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 7 - Call Stack
 * Name: Alexander Horton
 * Updated: 3/11/2025
 */

package hortona;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * Utility class for reading files
 */
public class FileReaderUtils {

    /**
     * returns false unless the passed argument only contains returns
     * @param aReturn a return
     * @return true if return is void
     */
    public static boolean isVoidReturn(String aReturn) {
        String noWhitespace = aReturn.strip();
        return noWhitespace.equals("return");
    }

    /**
     * Returns an OptionalInt for a non-void return's value
     * @param s a return
     * @return an OptionalInt containing the return value. Empty if no value
     */
    public static OptionalInt parseReturnValue(String s) {
        String noWhitespace = s.strip();
        if (s.contains("return")) {
            noWhitespace = noWhitespace.replace("return", "");
            noWhitespace = noWhitespace.strip();
            if (!noWhitespace.isEmpty()) {
                return OptionalInt.of(Integer.parseInt(noWhitespace));
            }
        }
        return OptionalInt.empty();
    }

    /**
     * Returns the name of a method from the passed string
     * @param s a method
     * @return the method name
     */
    public static Optional<String> parseMethodName(String s) {
        String noWhitespace = s.strip();
        String methodName;
        for (int i = 0; i < noWhitespace.length(); i++) {
            if(noWhitespace.charAt(i) =='(') {
                methodName = noWhitespace.substring(0, i);
                methodName = methodName.replace("int", "");
                methodName = methodName.replace("void", "");
                methodName = methodName.strip();
                return Optional.of(methodName);
            }
        }
        return Optional.empty();
    }

    /**
     * Gives an array of integers representing the arguments of a method
     * @param s a method
     * @return null if not a method, an empty array if there are no arguments,
     * or an array of arguments passed.
     * @throws IllegalArgumentException if arguments are not comma seperated ints
     */
    public static int[] parseArguments(String s)
            throws IllegalArgumentException {
        String noWhitespace = s.strip();
        String arguments;
        int[] args = null;
        String[] temp = null;
        for (int i = 0; i < noWhitespace.length(); i++) {
            if (noWhitespace.charAt(i) == '(') {
                arguments = noWhitespace.substring(i+1, noWhitespace.length()-1).strip();
                if (!arguments.isEmpty()) {
                    temp = arguments.split(",");
                } else {
                    args = new int[0];
                }
            }
        }
        if (temp != null) {
            args = new int[temp.length];
            int i = 0;
            for (String string : temp) {
                if (isInt(string.strip())) {
                    args[i] = Integer.parseInt(string.strip());
                }
                i++;
            }
        }
        return args;
    }

    private static boolean isInt(String string) {
        boolean allDigits = true;
        for (int i = 0; i < string.length() && allDigits; i++) {
            if(!(Character.isDigit(string.charAt(i)) || string.charAt(i) == '-')) {
                throw new IllegalArgumentException();
            }
        }
        return allDigits;
    }
}
