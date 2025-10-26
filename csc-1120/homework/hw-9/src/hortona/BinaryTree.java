/*
 * Course: CSC1020
 * Homework 9 - Binary Trees
 * Name: Alexander Horton
 * Last Updated: 03/28/2025
 */
package hortona;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * A simple Binary Tree implementation based on Koffman & Wolfgang's
 * Data Structures: Abstraction and Design Using Java - Chapter 6
 *
 * @param <E> - the type being stored in the Tree
 */
public class BinaryTree<E> implements Serializable {
    /**
     * Inner Node class to be used with a Binary Tree
     *
     * @param <E> - The type matching the Binary Tree class
     */
    public static class Node<E> implements Serializable {
        private static final long serialVersionUID = 0;
        protected final E data;
        protected Node<E> left;
        protected Node<E> right;

        /**
         * Single parameter constructor. Both subtrees assumed to be null
         *
         * @param data - Data to be stored in the root of the tree
         */
        public Node(E data) {
            this(data, null, null);
        }

        /**
         * Constructor that accepts the data and two subtrees
         *
         * @param data - Data to be stored in the root of the tree
         * @param left - the left child of this node
         * @param right - the right child of this node
         */
        public Node(E data, Node<E> left, Node<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        /**
         * Checks if the node is a leaf node. A leaf node is defined as
         * either having two null children or being itself null
         * @return - true if this is a leaf node, false otherwise
         */
        protected boolean isLeaf() {
            return left == null & right == null;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
    private static final long serialVersionUID = 1;
    protected final Node<E> root;

    /**
     * No-parameter constructor. Sets the root to null.
     */
    public BinaryTree(){
        this(null);
    }

    /**
     * One-parameter constructor that takes a root node as its parameter
     * @param root - the root of the tree
     */
    public BinaryTree(Node<E> root) {
        this.root = root;
    }

    /**
     * Constructor that assigns data to the root of the tree and a left and right subtree
     * @param data - data to be stored in the root of the tree
     * @param left - the left subtree of this tree
     * @param right - the right subtree of this tree
     */
    public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) {
        root = new Node<>(data);
        root.left = left != null ? left.root : null;
        root.right = right != null ? right.root : null;
    }

    /**
     * Returns the left subtree of this tree
     * @return - the left subtree
     */
    public BinaryTree<E> getLeftSubtree() {
        if(root != null && root.left != null) {
            return new BinaryTree<>(root.left);
        }
        return null;
    }

    /**
     * Returns the right subtree of this tree
     * @return - the right subtree
     */
    public BinaryTree<E> getRightSubtree() {
        if(root != null && root.right != null) {
            return new BinaryTree<>(root.right);
        }
        return null;
    }

    /**
     * Returns the data stored in the root of this tree
     * @return - data stored in the root of the tree
     */
    public E getData() {
        return root == null ? null : root.data;
    }

    /**
     * Checks if the root of the tree is a leaf node
     * @return - true if the root is a leaf node, false otherwise
     */
    public boolean isLeaf() {
        return root == null || root.left == null && root.right == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, 1, sb);
        return sb.toString();
    }

    private void toString(Node<E> node, int height, StringBuilder sb) {
        for(int i = 1; i < height; ++i) {
            sb.append(" ");
        }
        if(node == null) {
            sb.append("null\n");
        } else {
            sb.append(node).append("\n");
            toString(node.left, height + 1, sb);
            toString(node.right, height + 1, sb);
        }
    }

    /**
     * Performs an inorder traversal of the tree
     *
     * @param consumer - defines the action performed while traversing
     */
    public void inOrder(BiConsumer<E, Integer> consumer) {
        inOrder(root, 1, consumer);
    }

    private void inOrder(Node<E> node, int height, BiConsumer<E, Integer> consumer) {
        if(node == null) {
            consumer.accept(null, height);
        } else {
            inOrder(node.left, height + 1, consumer);
            consumer.accept(node.data, height);
            inOrder(node.right, height + 1, consumer);
        }
    }

    /*
     * Homework
     */

    /**
     * Performs a preorder traversal of the tree
     *
     * @param consumer - defines the action performed while traversing
     */
    public void preOrder(BiConsumer<E, Integer> consumer) {
        preOrder(root, 1, consumer);
    }

    private void preOrder(Node<E> node, int height, BiConsumer<E, Integer> consumer) {
        if(node == null) {
            consumer.accept(null, height);
        } else {
            consumer.accept(node.data, height);
            preOrder(node.left, height + 1, consumer);
            preOrder(node.right, height + 1, consumer);
        }
    }

    /**
     * Performs a postorder traversal of the tree
     *
     * @param consumer - defines the action performed while traversing
     */
    public void postOrder(BiConsumer<E, Integer> consumer) {
        postOrder(root, 1, consumer);

    }
    private void postOrder(Node<E> node, int height, BiConsumer<E, Integer> consumer) {
        if(node == null) {
            consumer.accept(null, height);
        } else {
            postOrder(node.left, height + 1, consumer);
            postOrder(node.right, height + 1, consumer);
            consumer.accept(node.data, height);
        }
    }

    /**
     * Returns the height of the tree
     * @return - the height of the tree
     */
    public int height() {
        if (root == null) {
            return 0;
        }
        return height(root, 1);
    }
    private int height(Node<E> node, int height) {
        if (node.isLeaf()) {
            return height;
        }
        return Math.max(height(node.left, height + 1), height(node.right, height + 1));
    }



    /**
     * Performs a preorder traversal of the tree and returns a String with
     * the node values in the order they were visited separated by spaces
     *
     * @return - The values of the nodes in the visited order
     */
    public String preOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder((e, _) -> {
            if (e != null) {
                sb.append(e).append(" ");
            }
        });
        return sb.toString().substring(0, sb.length() - 1);
    }

    /**
     * Performs a postorder traversal of the tree and returns a String with
     * the node values in the order they were visited separated by spaces
     *
     * @return - The values of the nodes in the visited order
     */
    public String postOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder((e, _) -> {
            if (e != null) {
                sb.append(e).append(" ");
            }
        });
        return sb.toString().substring(0, sb.length() - 1);
    }

    /**
     * Performs an inorder traversal and returns the contents of an expression
     * tree with parentheses surrounding each subtree
     * @return - The values of the nodes in the visited order
     */
    public String inOrder() {
        return inorder(root);
    }
    private String inorder(Node<E> node) {
        if (node.isLeaf()) {
            if (node.data.equals("+") || node.data.equals("-") ||
                    node.data.equals("*") || node.data.equals("/")) {
                return " " + node.data + " ";
            } else {
                return "(" + node.data + ")";
            }
        }
        return "(" + inorder(node.left) + " " + node.data + " " + inorder(node.right) + ")";
    }
}
