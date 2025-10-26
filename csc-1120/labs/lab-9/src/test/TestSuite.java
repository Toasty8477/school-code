/*
 * Course: CSC1020
 * Morse Code Decoder
 * Name: Alexander Horton
 * Last Updated: 03/28/2025
 */

package test;

import hortona.MorseDecoder;
import hortona.MorseTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

/**
 * Test suite for Morse Tree and Decoder
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSuite {
    private MorseTree<Character> tree;
    private Field root;
    private Field symbol;
    private Field dot;
    private Field dash;

    @BeforeEach
    void setup() throws NoSuchFieldException {
        tree = new MorseTree<>();
        root = tree.getClass().getDeclaredField("root");
        root.setAccessible(true);
        Class<?> node = MorseTree.class.getDeclaredClasses()[0];
        symbol = node.getDeclaredField("symbol");
        symbol.setAccessible(true);
        dot = node.getDeclaredField("dot");
        dot.setAccessible(true);
        dash = node.getDeclaredField("dash");
        dash.setAccessible(true);
    }

    @Order(1)
    @Test
    @DisplayName("Testing Constructor")
    void treeConstructor() throws IllegalAccessException {
        Object rootNode = root.get(tree);
        Assertions.assertNotNull(rootNode);
        Assertions.assertNull(symbol.get(rootNode));
        Assertions.assertNull(dot.get(rootNode));
        Assertions.assertNull(dash.get(rootNode));
    }

    @Order(2)
    @Test
    @DisplayName("Testing Add")
    void add() throws IllegalAccessException {
        // add A
        tree.add('A', ".-");
        // the left child should be created with a null symbol
        Assertions.assertNotNull(dot.get(root.get(tree)));
        Assertions.assertNull(dash.get(root.get(tree)));
        Assertions.assertNull(symbol.get(dot.get(root.get(tree))));
        // the left child's right child should be created with the symbol 'A'
        Assertions.assertNotNull(dash.get(dot.get(root.get(tree))));
        Assertions.assertNotNull(symbol.get(dash.get(dot.get(root.get(tree)))));
        Assertions.assertEquals('A', symbol.get(dash.get(dot.get(root.get(tree)))));
        // add E
        tree.add('E', ".");
        // the left child should now contain the symbol E
        Assertions.assertNotNull(symbol.get(dot.get(root.get(tree))));
        Assertions.assertEquals('E', symbol.get(dot.get(root.get(tree))));
        // the left child's right child should still be there and still contain 'A'
        Assertions.assertNotNull(dash.get(dot.get(root.get(tree))));
        Assertions.assertNotNull(symbol.get(dash.get(dot.get(root.get(tree)))));
        Assertions.assertEquals('A', symbol.get(dash.get(dot.get(root.get(tree)))));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.add('*', ".$"));
    }

    @Order(3)
    @Test
    @DisplayName("Testing Decode")
    void decode() throws IOException {
        loadDictionary();
        validateTree(tree);
        Assertions.assertEquals(Optional.empty(), tree.decode("..--."));
        Assertions.assertEquals(Optional.empty(), tree.decode("......."));
    }

    @Order(4)
    @Test
    @DisplayName("Test LoadDecoder")
    void loadDecoder() throws IOException {
        MorseTree<Character> actual = MorseDecoder.loadDecoder(Paths.get("data", "morseCode.txt"));
        validateTree(actual);
        Assertions.assertThrows(IOException.class,
                () -> MorseDecoder.loadDecoder(Paths.get("data", "morseCod.txt")));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> MorseDecoder.loadDecoder(Paths.get("data", "badCode.txt")));
    }

    @Order(5)
    @Test
    @DisplayName("Test DecodeMessage")
    void decodeMessage() throws IOException {
        loadDictionary();
        String message = MorseDecoder.decodeMessage(Paths.get("data", "counting.txt")
                .toFile(), tree).getFirst();
        Assertions.assertEquals("1 2 3 4 5 6 7 8 9 10 11 12", message);
        message = MorseDecoder.decodeMessage(Paths.get("data", "encoded.txt")
                .toFile(), tree).getFirst();
        Assertions.assertEquals("""
                A SPACE SHOULD BE PLACED BETWEEN EACH ENCODED CHARACTER.

                A  SHOULD BE PLACED BETWEEN EACH WORD.

                LINE BREAKS IN THE INPUT FILE SHOULD BE REPLICATED IN THE ENCODED FILE.
                """, message);
        message = MorseDecoder.decodeMessage(Paths.get("data", "secret.txt")
                .toFile(), tree).getFirst();
        Assertions.assertEquals("""
                THIS IS A SECRET MESSAGE
                DO NOT TELL ANYONE.""", message);

    }

    private void loadDictionary() throws IOException {
        try(Scanner in = new Scanner(Paths.get("data", "morseCode.txt"))) {
            while(in.hasNextLine()) {
                char symbol;
                String code;
                String line = in.nextLine();
                if (line.charAt(0) == '\\') {
                    symbol = '\n';
                    code = line.substring(2);
                } else {
                    symbol = line.charAt(0);
                    code = line.substring(1);
                }
                tree.add(symbol, code);
            }
        }
    }

    private void validateTree(MorseTree<Character> tree) {
        Assertions.assertEquals('A', tree.decode(".-").get());
        Assertions.assertEquals('B', tree.decode("-...").get());
        Assertions.assertEquals('C', tree.decode("-.-.").get());
        Assertions.assertEquals('D', tree.decode("-..").get());
        Assertions.assertEquals('E', tree.decode(".").get());
        Assertions.assertEquals('F', tree.decode("..-.").get());
        Assertions.assertEquals('G', tree.decode("--.").get());
        Assertions.assertEquals('H', tree.decode("....").get());
        Assertions.assertEquals('I', tree.decode("..").get());
        Assertions.assertEquals('J', tree.decode(".---").get());
        Assertions.assertEquals('K', tree.decode("-.-").get());
        Assertions.assertEquals('L', tree.decode(".-..").get());
        Assertions.assertEquals('M', tree.decode("--").get());
        Assertions.assertEquals('N', tree.decode("-.").get());
        Assertions.assertEquals('O', tree.decode("---").get());
        Assertions.assertEquals('P', tree.decode(".--.").get());
        Assertions.assertEquals('Q', tree.decode("--.-").get());
        Assertions.assertEquals('R', tree.decode(".-.").get());
        Assertions.assertEquals('S', tree.decode("...").get());
        Assertions.assertEquals('T', tree.decode("-").get());
        Assertions.assertEquals('U', tree.decode("..-").get());
        Assertions.assertEquals('V', tree.decode("...-").get());
        Assertions.assertEquals('W', tree.decode(".--").get());
        Assertions.assertEquals('X', tree.decode("-..-").get());
        Assertions.assertEquals('Y', tree.decode("-.--").get());
        Assertions.assertEquals('Z', tree.decode("--..").get());
        Assertions.assertEquals('0', tree.decode("-----").get());
        Assertions.assertEquals('1', tree.decode(".----").get());
        Assertions.assertEquals('2', tree.decode("..---").get());
        Assertions.assertEquals('3', tree.decode("...--").get());
        Assertions.assertEquals('4', tree.decode("....-").get());
        Assertions.assertEquals('5', tree.decode(".....").get());
        Assertions.assertEquals('6', tree.decode("-....").get());
        Assertions.assertEquals('7', tree.decode("--...").get());
        Assertions.assertEquals('8', tree.decode("---..").get());
        Assertions.assertEquals('9', tree.decode("----.").get());
        Assertions.assertEquals('.', tree.decode(".-.-.-").get());
        Assertions.assertEquals(',', tree.decode("--..--").get());
        Assertions.assertEquals('/', tree.decode("-..-.").get());
        Assertions.assertEquals('?', tree.decode("..--..").get());
        Assertions.assertEquals(' ', tree.decode(".-...").get());
        Assertions.assertEquals('\n', tree.decode(".-.-").get());
        Assertions.assertFalse(tree.decode("---.").isPresent());
        Assertions.assertFalse(tree.decode("------").isPresent());
    }
}
