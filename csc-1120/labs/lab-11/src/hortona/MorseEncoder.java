/*
 * Course: CSC-1120A
 * Lab 11: Morse Encoder
 * Name: Alexander Horton
 * Last Updated: 04/15/2025
 */

package hortona;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility Class to play and encode messages
 */
public class MorseEncoder {

    private static final HashMap<Character, String> MAP = new HashMap<>();

    /**
     * Plays a single tone of a morse code message
     * @param dot true if the tone should be a dot, false if it should be a dash
     * @throws LineUnavailableException thrown if the stream is not available to play
     */
    public static void playTone(boolean dot) throws LineUnavailableException {
        final float frequency = 44100f;
        final int sampleSize = 8;
        final int dotLength = 50; // in milliseconds
        final int dashLength = 150; // in milliseconds
        final int millisecond = 1000;
        final int hertz = 880; // tone frequency. 440 = A
        final int volume = 50; // 0-100
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(frequency, sampleSize, 1, true, false);
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        final int mSecs = dot ? dotLength : dashLength;
        for (int i = 0; i < mSecs * frequency / millisecond; i++) {
            double angle = i / (frequency / hertz) * 2.0 * Math.PI;
            buf[0] = (byte) (Math.sin(angle) * volume);
            sdl.write(buf, 0, 1);
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
    }

    /**
     * Loads a morse encoder table
     * @param path path of encoder file
     * @throws IOException if the file cannot be loaded
     */
    public static void loadTable(Path path) throws IOException {
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                String morse = scanner.nextLine();
                char symbol = morse.charAt(0);
                if (symbol == '\\') {
                    MAP.put('\n', morse.substring(2));
                } else {
                    MAP.put(symbol, morse.substring(1));
                }
            }
        }
    }

    /**
     * Encodes a message to morse code
     * @param message unencoded message
     * @return encoded message
     */
    public static List<String> encodeMessage(String message) {
        StringBuilder sb = new StringBuilder();
        List<String> encoded = new ArrayList<>();
        message = message.toUpperCase();
        for (int i = 0; i < message.length(); i++) {
            String value = MAP.get(message.charAt(i));
            if (value != null) {
                if (value.equals(".-.-")) {
                    sb.append(value).append("\n");
                } else {
                    sb.append(value).append(" ");
                }
            } else {
                String error = "Warning: skipping: " + message.charAt(i);
                encoded.add(error);
            }
        }
        encoded.addFirst(sb.toString());
        return encoded;
    }
}
