/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Lab 10 - StarBlind
 * Name: Alexander Horton
 * Created 11/08/2024
 */
package hortona;

import java.util.ArrayList;

/**
 * Pretty colored stars
 */
public class Stars {
    private static final char STAR = '\u2605';
    private final String bold = "\u001B[1m";
    private final int maxColors = 4;
    private ArrayList<Color> colors;

    /**
     * creates a list of 4 random stars
     */
    public Stars() {
        colors = new ArrayList<>();
        for (int i = 0; i < maxColors; i++) {
            colors.add(Color.getColor());
        }
    }

    /**
     * creates a list of stars based on an inputted pattern
     * @param pattern in the form rggb
     */
    public Stars(String pattern) {
        colors = new ArrayList<>();
        for (int i = 0; i < maxColors; i++) {
            colors.add(Color.getColor(pattern.charAt(i)));
        }
    }

    /**
     * Creates four stars of the specified colors
     * @param a color one
     * @param b color two
     * @param c color three
     * @param d color four
     */
    public Stars(Color a, Color b, Color c, Color d) {
        colors = new ArrayList<>();
        colors.add(a);
        colors.add(b);
        colors.add(c);
        colors.add(d);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(bold);
        for (Color color: colors) {
            sb.append(Color.colorString(color, String.valueOf(STAR)));
        }
        sb.append(Color.RESET);
        return sb.toString();
    }

    /**
     * checks for similarity between this stars instance and another
     * @param that another stars instance
     * @return returns green and yellow pipes based on similarity
     */
    public String getSimilarity(Stars that) {
        ArrayList<Color> guessCopy = new ArrayList<>();
        ArrayList<Color> answerCopy = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            guessCopy.add(that.colors.get(i));
            answerCopy.add(this.colors.get(i));
        }
        StringBuilder sb = new StringBuilder(bold);
        int i = 0;
        sb.append(Color.GREEN);
        while (i < answerCopy.size() || i == 0) {
            if (answerCopy.get(i) == guessCopy.get(i)) {
                sb.append("|");
                answerCopy.remove(i);
                guessCopy.remove(i);
            } else {
                i++;
            }
            if (answerCopy.isEmpty()) {
                i++;
            }
        }
        sb.append(Color.YELLOW);
        for (i = 0; i < guessCopy.size(); i++) {
            if (answerCopy.contains(guessCopy.get(i))) {
                sb.append("|");
                answerCopy.remove(guessCopy.get(i));
            }
        }
        sb.append(Color.RESET);
        return sb.toString();
    }
}