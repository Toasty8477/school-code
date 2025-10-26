/*
 * Course: CSC-1020
 * Tree Tests
 * Name: Alexander Horton
 * Last Updated: 03/28/2025
 */
package test;

import hortona.BinaryTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Basic test suite for a Binary Tree
 */
public class TestSuite {
    private static final String TREE_TO_STRING = """
            +
             +
              x
               null
               null
              y
               null
               null
             /
              a
               null
               null
              b
               null
               null
            """;
    private static final String PREORDER = "++xnullnullynullnull/anullnullbnullnull";
    private static final String INORDER = "nullxnull+nullynull+nullanull/nullbnull";
    private static final String POSTORDER = "nullnullxnullnully+nullnullanullnullb/+";
    private static final String PREORDER2 = "+ + x y / a b";
    private static final String POSTORDER2 = "x y + a b / +";
    private static final String INORDER2 = "(((x) + (y)) + ((a) / (b)))";

    @Test
    @DisplayName("Test Constructors")
    void testConstructors() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        Assertions.assertNull(tree.getData());
        tree = new BinaryTree<>(new BinaryTree.Node<>(0));
        Assertions.assertEquals(0, tree.getData());
        BinaryTree<Integer> root = new BinaryTree<>(4, tree, null);
        Assertions.assertEquals(4, root.getData());
    }

    @Test
    @DisplayName("Test Getting Subtrees")
    void testGettingSubtrees() {
        BinaryTree<String> tree = buildExpressionTree();
        Assertions.assertEquals("+", tree.getLeftSubtree().getData());
        Assertions.assertEquals("/", tree.getRightSubtree().getData());
        Assertions.assertEquals("x", tree.getLeftSubtree().getLeftSubtree().getData());
        Assertions.assertEquals("y", tree.getLeftSubtree().getRightSubtree().getData());
        Assertions.assertEquals("a", tree.getRightSubtree().getLeftSubtree().getData());
        Assertions.assertEquals("b", tree.getRightSubtree().getRightSubtree().getData());
    }

    @Test
    @DisplayName("Test isLeaf")
    void isLeaf(){
        BinaryTree<Integer> tree = new BinaryTree<>();
        Assertions.assertTrue(tree.isLeaf());
        tree = new BinaryTree<>(new BinaryTree.Node<>(0));
        Assertions.assertTrue(tree.isLeaf());
        BinaryTree<Integer> root = new BinaryTree<>(4, tree, null);
        Assertions.assertFalse(root.isLeaf());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        BinaryTree<String> tree = buildExpressionTree();
        Assertions.assertEquals(TREE_TO_STRING, tree.toString());
    }

    @Test
    @DisplayName("Test Preorder Traversals")
    void preOrder() {
        BinaryTree<String> tree = buildExpressionTree();
        StringBuilder sb = new StringBuilder();
        tree.preOrder((d, _) -> sb.append(d));
        Assertions.assertEquals(PREORDER, sb.toString());
        Assertions.assertEquals(PREORDER2, tree.preOrder());
    }

    @Test
    @DisplayName("Test Inorder Traversal")
    void inOrder() {
        BinaryTree<String> tree = buildExpressionTree();
        StringBuilder sb = new StringBuilder();
        tree.inOrder((d, _) -> sb.append(d));
        Assertions.assertEquals(INORDER, sb.toString());
        Assertions.assertEquals(INORDER2, tree.inOrder());
    }

    @Test
    @DisplayName("Test Postorder Traversal")
    void postOrder() {
        BinaryTree<String> tree = buildExpressionTree();
        StringBuilder sb = new StringBuilder();
        tree.postOrder((d, _) -> sb.append(d));
        Assertions.assertEquals(POSTORDER, sb.toString());
        Assertions.assertEquals(POSTORDER2, tree.postOrder());
    }

    @Test
    @DisplayName("Test Height")
    void height() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        Assertions.assertEquals(0, tree.height());
        tree = new BinaryTree<>(new BinaryTree.Node<>(0));
        Assertions.assertEquals(1, tree.height());
        BinaryTree<String> root = buildExpressionTree();
        Assertions.assertEquals(3, root.height());
    }

    private BinaryTree<String> buildExpressionTree() {
        BinaryTree<String> x = new BinaryTree<>("x", null, null);
        BinaryTree<String> y = new BinaryTree<>("y", null, null);
        BinaryTree<String> a = new BinaryTree<>("a", null, null);
        BinaryTree<String> b = new BinaryTree<>("b", null, null);
        BinaryTree<String> plus = new BinaryTree<>("+", x, y);
        BinaryTree<String> divide = new BinaryTree<>("/", a, b);
        return new BinaryTree<>("+", plus, divide);
    }
}