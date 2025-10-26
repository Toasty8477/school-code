/*
 * Course: CSC-1120
 * Homework 14: Self-Balancing Trees
 */
package test;

import hortona.RedBlackTree;
import hortona.AVLTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * Basic tests for AVL and Red-Black Trees
 */
public class TestSuite {
    private AVLTree<String> avlTree;
    private RedBlackTree<String> rbTree;
    private TreeSet<String> words;

    @BeforeEach
    void initialize() {
        avlTree = new AVLTree<>();
        rbTree = new RedBlackTree<>();
        words = new TreeSet<>();
        loadDictionary();
    }

    @DisplayName("Testing Add method")
    @Test
    void add() {
        int size = 0;
        while (!words.isEmpty()) {
            String word = words.pollFirst();
            avlTree.add(word);
            rbTree.add(word);
            Assertions.assertEquals(++size, avlTree.size());
            Assertions.assertEquals(size, rbTree.size());
        }
        System.out.println(size + " " + avlTree.height());
        Assertions.assertTrue(avlTree.height() >= log2(size) && avlTree.height() <= log2(size) + 1);
        System.out.println(rbTree);
    }

    @DisplayName("Testing toString")
    @Test
    void toStringTest() {
        try (Scanner in = new Scanner(Paths.get("data", "2000WordsAVL.txt").toFile());
                Scanner in2 = new Scanner(Paths.get("data", "2000WordsRB.txt").toFile())) {
            fillTrees();
            String avl = avlTree.toString().trim();
            StringJoiner sj = new StringJoiner("\n");
            while (in.hasNextLine()) {
                sj.add(in.nextLine());
            }
            Assertions.assertEquals(sj.toString(), avl);

            String rb = rbTree.toString().trim();
            sj = new StringJoiner("\n");
            while (in2.hasNextLine()) {
                sj.add(in2.nextLine());
            }
            Assertions.assertEquals(sj.toString(), rb);
        } catch (FileNotFoundException e) {
            System.err.println("Can't find file");
        }
    }

    private void loadDictionary() {
        try (Scanner in = new Scanner(Paths.get("data", "2000words.txt").toFile())) {
            while (in.hasNext()) {
                words.add(in.next());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not load file. Exiting.");
            System.exit(1);
        }
    }

    private int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    private void fillTrees() {
        loadDictionary();
        while (!words.isEmpty()) {
            String word = words.pollFirst();
            avlTree.add(word);
            rbTree.add(word);
        }
    }
}
