/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 14 - Yet More Autocompleter
 * Name: Alexander Horton
 */

package hortona.model;

/**
 * AutoCompleter that will suggest possible word completion
 */
public interface AutoCompleter {
    /**
     * Adds a word to the auto-completer if it does not yet exist
     * @param word the word to add
     * @return true if the word is added, false otherwise
     * @throws IllegalArgumentException if word is null or empty
     */
    boolean add(String word);

    /**
     * Checks for an exact match in the AutoCompleter
     * @param target the word to check
     * @return true if there is an exact match, false if target is null, empty, or not in the
     * AutoCompleter
     */
    boolean exactMatch(String target);

    /**
     * The number of words in the AutoCompleter
     * @return the size of the AutoCompleter
     */
    int size();

    /**
     * Returns a String indicting the fully qualified name of the data structure used to store
     * the words for the AutoCompleter.
     * @return the FQN of the backing data structure
     */
    String getBackingClass();

    /**
     * Returns an array of all the String im the object that begin with prefix.
     * If prefix is empty, an array of all strings in the AutoCompleter is returned
     * @param prefix the beginning of the possible words
     * @return an array of possible completions
     */
    String[] allMatches(String prefix);

    /**
     * Formats the time taken into a human-friendly String
     * @param nanoseconds the time taken in nanoseconds
     * @return a formatted string
     * @throws IllegalArgumentException if time is negative
     */
    static String format(long nanoseconds) throws IllegalArgumentException {
        String result;
        String minutes = nanoseconds % Time.HOUR.duration / Time.MINUTE.duration != 1 ? "s " : "";
        String seconds = nanoseconds % Time.MINUTE.duration / Time.SECOND.duration != 1 ? "s" : "";

        if(nanoseconds < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
        switch ((Long) nanoseconds) {
            case Long l when l >= Time.DAY.duration -> {
                long days = nanoseconds / Time.DAY.duration;
                result = days + " day" + (nanoseconds / Time.DAY.duration != 1 ? "s " : " ");
                long hours = nanoseconds % Time.DAY.duration / Time.HOUR.duration;
                result += hours + " hour" +
                        (nanoseconds % Time.DAY.duration / Time.HOUR.duration != 1 ? "s " : " ");
                long mins = nanoseconds % Time.HOUR.duration / Time.MINUTE.duration;
                result += mins + " minute" + minutes;
            }
            case Long l when l >= Time.HOUR.duration -> {
                long hours = nanoseconds / Time.HOUR.duration;
                result = hours + " hour" + (nanoseconds / Time.HOUR.duration != 1 ? "s " : " ");
                long mins = nanoseconds % Time.HOUR.duration / Time.MINUTE.duration;
                result += mins + " minute" + minutes;
                long secs = (nanoseconds % Time.MINUTE.duration) / Time.SECOND.duration;
                result += secs + " second" + (secs != 1 ? "s" : "");
            }
            case Long l when l >= Time.MINUTE.duration -> {
                long mins = nanoseconds / Time.MINUTE.duration;
                result = mins + " minute" + (nanoseconds / Time.MINUTE.duration != 1 ? "s " : " ");
                double secs = (double) (nanoseconds % Time.MINUTE.duration) / Time.SECOND.duration;
                result += secs > 0 && secs % 1 == 0 ? (int) secs + " second"
                        : secs == 0 ? "0 seconds"
                        : String.format("%.1f second", secs) + seconds;
            }
            case Long l when l >= Time.SECOND.duration -> {
                double secs = (double) nanoseconds / Time.SECOND.duration;
                result = secs % 1 == 0 ? (int) secs + " second"
                        : String.format("%.1f second", secs) + seconds;
            }
            case Long l when l >= Time.MICRO.duration ->
                    result = nanoseconds / Time.MICRO.duration == 1 ? "1 microsecond" :
                    String.format("%.1f microseconds", (double) nanoseconds / Time.MICRO.duration);
            case Long l when l >= Time.MILLI.duration ->
                    result = nanoseconds / Time.MILLI.duration == 1 ? "1 millisecond"
                    : String.format("%.1f milliseconds",
                            (double) nanoseconds / Time.MILLI.duration);
            default -> result = nanoseconds + " nanosecond" + (nanoseconds > 1 ? "s" : "");
        }
        return result.trim();
    }

    /**
     * Generates a String of random lowercase characters
     * @param length the length of the String to generate
     * @return a String of size length of random characters
     */
    static String getString(int length) {
        final int a = 97;
        final int z = 122;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; ++i) {
            sb.append((char)((int)(Math.random() * (z - a + 1)) + a));
        }
        return sb.toString();
    }

    /**
     * Time values
     */
    enum Time {
        /**
         * Microsecond
         */
        MICRO(1_000),
        /**
         * Millisecond
         */
        MILLI(1_000_000L),
        /**
         * Second
         */
        SECOND(1_000_000_000L),
        /**
         * Minute
         */
        MINUTE(60_000_000_000L),
        /**
         * Hour
         */
        HOUR(60 * 60_000_000_000L),
        /**
         * Day
         */
        DAY(24 * 60 * 60_000_000_000L);
        private final long duration;

        Time(long duration) {
            this.duration = duration;
        }
    }
}

