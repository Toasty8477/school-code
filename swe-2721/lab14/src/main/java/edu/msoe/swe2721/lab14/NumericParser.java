package edu.msoe.swe2721.lab14;

/**
 * This class is responsible for parsing strings to identify a number within the
 * text.
 */
public class NumericParser {

    /**
     * This method will convert the English textual representations of the numbers
     * zero through ten into integer values.
     * 
     * @param s This is the parameter. It can be in any case, but must match the
     *          English spellings for the numbers zero through ten. Whitespace can
     *          be present before and after the text.
     * @return The return will be a number with an integer value of between 0 and
     *         10.
     * @throws NumericParseException This exception will be thrown if the value to
     *                               be parsed is not a valid string representation
     *                               of an English number between 0 and 10. Also
     *                               will be thrown if a null parameter is passed
     *                               in.
     */
    public static int parseString(String s) throws NumericParseException {
        // null is not a number between zero and ten
        if (s == null) {
            throw new NumericParseException("Input cannot be null");
        }
        // make everything lowercase
        s = s.toLowerCase();
        // get rid of leading and trailing whitespace
        s = s.trim();
        
        // oh boy, here we go...
        int ret = -1;
        switch (s) {
            case "zero" -> ret = 0;
            case "one" -> ret = 1;
            case "two" -> ret = 2;
            case "three" -> ret = 3;
            case "four" -> ret = 4;
            case "five" -> ret = 5;
            case "six" -> ret = 6;
            case "seven" -> ret = 7;
            case "eight" -> ret = 8;
            case "nine" -> ret = 9;
            case "ten" -> ret = 10;
            default -> throw new NumericParseException("Number must be zero through ten");
        };

        return ret;
    }
}
