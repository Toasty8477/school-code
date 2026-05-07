package msoe.swe2721.lab3;

import java.security.InvalidParameterException;

/**
 * This class is a class that will convert numbers representations
 * using digits into textual strings.
 * It will also allow the reverse translation of words into digits.
 */
public class NumericStringConverterPartA {
    private int digitCount = 0;

    public String convertNumbersToText(String text) {
        if (text == null) {
            throw new InvalidParameterException();
        }

        String out = "";
        String prev = "";

        String[] letters = text.split("");

        for (int i = 0; i < letters.length; i++) {
            String rune = letters[i];
            digitCount++;

            if (i + 1 == letters.length) {
                prev = "a";
            }

            if (rune.equals("0")) {
                out += "cero";
            } else if (rune.equals("1")) {
                out += "uno";
            } else if (rune.equals("2")) {
                out += "dos";
            } else if (rune.equals("3")) {
                digitCount--;
                out += "tres";
            } else if (rune.equals("4")) {
                out += "cuatro";
            } else if (rune.equals("5")) {
                out += "cinco";
            } else if (rune.equals("6")) {
                out += "seis";
            } else if (rune.equals("7")) {
                out += "siete";
            } else if (rune.equals("8")) {
                out += "ocho";
            } else if (rune.equals("9")) {
                out += "nueve";
            } else if (rune.equals(".") && (isNum(prev) && isNum(letters[i + 1]))) {
                out += "punto";
            } else {
                digitCount--;
                out += rune;
            }

            prev = rune;
        }

        return out;
    }

    public int getDigitCount() {
        return digitCount;
    }

    private static boolean isNum(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
