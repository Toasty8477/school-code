package msoe.swe2721.lab3;

import java.security.InvalidParameterException;

/**
 * This class is a class that will convert numbers representations using digits into textual strings.  It will also allow the reverse traslation of words into digits.
 */
public class NumericStringConverterPartB {
  
    private int digitCount = 0;
    private String[] numbersText = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "punto"};
    private String[] numbersDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."};

    public String convertTextToNumbers(String text) throws InvalidParameterException {

        if (text == null) {
            throw new InvalidParameterException("Cannot convert null");
        }

        String[] origArray = text.split(" ");
        String[] newArray = new String[origArray.length];

        for (int i = 0; i < origArray.length; i++) {
            newArray[i] = convertTextToNumbers(origArray[i], 1);
        }
        return String.join(" ", newArray);

    }

    private String convertTextToNumbers(String text, int subTo) {
        if (text == null) {
            throw new InvalidParameterException();
        }
        if (subTo > text.length()) {
            return text;
        }
        for (int i = 0; i < numbersText.length; i++) {
            if (text.substring(0, subTo).equals(numbersText[i])) {
                if (i != 1) {
                    digitCount--;
                }
                return numbersDigits[i] + convertTextToNumbers(text.substring(subTo), 1);
            }
        }
        return convertTextToNumbers(text, subTo + 1);
    }

    public int getDigitCount() {
        return digitCount;
    }
}
