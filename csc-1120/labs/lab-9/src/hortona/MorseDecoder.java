/*
 * Course: CSC1020
 * Morse Code Decoder
 * Name: Alexander Horton
 * Last Updated: 03/28/2025
 */

package hortona;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for making a decoder and decoding messages
 */
public class MorseDecoder {

    /**
     * Creates a decoder tree from a file
     * @param path path of the decoder file
     * @return a MorseTree object corresponding to the decoder
     * @throws IOException when the file cannot be found
     * @throws IllegalArgumentException if provided Morse code is bad
     */
    public static MorseTree<Character> loadDecoder(Path path)
            throws IOException, IllegalArgumentException {

        MorseTree<Character> decoder = new MorseTree<>();

        try(Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char symbol;
                String code;
                if (line.charAt(0) == '\\') {
                    symbol = '\n';
                    code = line.substring(2);
                } else {
                    symbol = line.charAt(0);
                    code = line.substring(1);
                }
                decoder.add(symbol, code);
            }
        }
        return decoder;
    }

    /**
     * Decode a message using a decoder tree
     * @param input encoded file
     * @param tree decoder tree
     * @return decoded message
     * @throws FileNotFoundException if file could not be found
     */
    public static List<String> decodeMessage(File input, MorseTree<Character> tree)
            throws FileNotFoundException {
        List<String> decoded = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try(Scanner scanner = new Scanner(input)) {
            while (scanner.hasNext()) {
                String code = scanner.next();
                try {
                    char symbol = tree.decode(code).get();
                    sb.append(symbol);
                } catch (IllegalArgumentException e) {
                    decoded.addLast(e.getMessage());
                }
            }
            decoded.addFirst(sb.toString());
        }
        return decoded;
    }
}
