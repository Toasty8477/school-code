/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 6 - Autocompleter
 * Name: Alexander Horton
 * Updated: 3/4/2025
 */

package hortona;

/**
 * Basis for autocompleting words as well as a nanosecond formatter
 */
public interface AutoCompleter {
    /**
     * Adds a word to the list of words that can be autocompleted
     * @param word any word
     * @return true if the word has been added. false if the word is a duplicate
     * @throws IllegalArgumentException If word is null or empty
     */
    boolean add(String word) throws IllegalArgumentException;

    /**
     * Returns whether there is an exact match wof the word in the autocompleter
     * @param target the word to check for
     * @return true if target is found, false if target is not found, empty, or null
     */
    boolean exactMatch(String target);

    /**
     * Number of words in the autocompleter
     * @return Number of words in the autocompleter
     */
    int size();

    /**
     * Returns the name of the backing items structure
     * @return fully qualified name of the backing structure e.x. "java.util.ArrayList"
     */
    String getBackingClass();

    /**
     * A list of any words in the autocompleter that start with a given prefix
     * @param prefix the start of a word
     * @return An array of all strings that begin with prefix.
     * Empty strings return all words, null returns no words
     */
    String[] allMatches(String prefix);

    /**
     * Returns a human-readable form for a given number of nanoseconds
     * @param nanoseconds a positive number of nanoseconds
     * @return nanoseconds in a human-readable format. e.x. "1 day 5 hours 32 minutes"
     * @throws IllegalArgumentException if nanoseconds is negative
     */
    static String format(long nanoseconds) throws IllegalArgumentException {

        final long nanosecondsPerDay = 86_400_000_000_000L;
        final long nanosecondsPerHour = 3_600_000_000_000L;
        final long nanosecondsPerMinute = 60_000_000_000L;
        final double nanosecondsPerSecond = 1_000_000_000;
        final double nanosecondsPerMillisecond = 1_000_000;
        final double nanosecondsPerMicrosecond = 1_000;

        long nanosecondsRemaining = nanoseconds;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        double seconds = 0;
        double milliseconds = 0;
        double microseconds = 0;

        days = (int) (nanosecondsRemaining / nanosecondsPerDay);
        nanosecondsRemaining = nanosecondsRemaining % nanosecondsPerDay;
        hours = (int) (nanosecondsRemaining / nanosecondsPerHour);
        nanosecondsRemaining = nanosecondsRemaining % nanosecondsPerHour;
        minutes = (int) (nanosecondsRemaining / nanosecondsPerMinute);
        nanosecondsRemaining = nanosecondsRemaining % nanosecondsPerMinute;

        seconds = nanosecondsRemaining / nanosecondsPerSecond;

        if (seconds < 1) {
            seconds = 0;
            milliseconds = nanosecondsRemaining / nanosecondsPerMillisecond;
        } else {
            nanosecondsRemaining = 0;
        }
        if (milliseconds < 1) {
            milliseconds = 0;
            microseconds = nanosecondsRemaining / nanosecondsPerMicrosecond;
        } else {
            nanosecondsRemaining = 0;
        }
        if (microseconds < 1) {
            microseconds = 0;
        } else {
            nanosecondsRemaining = 0;
        }

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days);
            sb.append(" Day");
            if (days > 1) {
                sb.append("s");
            }
            if (!sb.isEmpty()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        if (hours > 0) {
            sb.append(hours);
            sb.append(" Hour");
            if (hours > 1) {
                sb.append("s");
            }
            if (!sb.isEmpty()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        if (minutes > 0) {
            sb.append(minutes);
            sb.append(" Minute");
            if (minutes > 1) {
                sb.append("s");
            }
            if (!sb.isEmpty()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        if (seconds > 0 || milliseconds > 0 && !sb.isEmpty()) {
            if (seconds / (int) seconds == 1) {
                sb.append((int) seconds);
            } else {
                sb.append(seconds);
            }
            sb.append(" Second");
            if (seconds > 1 || seconds == 0) {
                sb.append("s");
            }
            if (!sb.isEmpty()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        if (milliseconds > 0 || microseconds > 0 && !sb.isEmpty()) {
            sb.append(milliseconds);
            sb.append(" Millisecond");
            if (milliseconds > 1 || milliseconds == 0) {
                sb.append("s");
            }
            if (!sb.isEmpty()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        if (microseconds > 0) {
            sb.append(microseconds);
            sb.append(" Microsecond");
            if (microseconds > 1 || nanoseconds == 0) {
                sb.append("s");
            }
            if (!sb.isEmpty()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        if (nanosecondsRemaining > 0) {
            sb.append(nanosecondsRemaining);
            sb.append(" Nanoseconds");
            if (!sb.isEmpty()) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        sb.setLength(sb.length()-2);
        return sb.toString();
    }
}