/*
 * Course: CSC-1120
 * Homework 14: Self-Balancing Trees
 * Name: Alexander Horton
 */
package hortona;

import java.util.function.BiConsumer;

/**
 * A Binary Tree
 * @param <E> the element type stored in the Binary Tree
 */
public class BinaryTree<E> {
    protected static class Node<E> {
        protected E data;
        protected Node<E> left;
        protected Node<E> right;

        protected Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        protected boolean isLeaf() {
            return left == null & right == null;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    protected Node<E> root;

    /**
     * No-parameter constructor
     */
    public BinaryTree() {
        this(null);
    }

    /**
     * Constructor that is given a root node
     * @param root the root Node of the tree
     */
    public BinaryTree(Node<E> root) {
        this.root = root;
    }

    /**
     * A constructor that creates a root node and attaches the left and right subtrees
     * @param data the data at the root of the tree
     * @param left the left subtree
     * @param right the right subtree
     */
    public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) {
        root = new Node<>(data);
        root.left = left != null ? left.root : null;
        root.right = right != null ? right.root : null;
    }

    /**
     * Returns the subtree rooted at the left child
     * @return the subtree rooted at the left child
     */
    public BinaryTree<E> getLeftSubtree() {
        if(root != null && root.left != null) {
            return new BinaryTree<>(root.left);
        }
        return null;
    }

    /**
     * Returns the subtree rooted at the right child
     * @return the subtree rooted at the right child
     */
    public BinaryTree<E> getRightSubtree() {
        if(root != null && root.right != null) {
            return new BinaryTree<>(root.right);
        }
        return null;
    }

    public E getData() {
        return root == null ? null : root.data;
    }

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
        sb.append(" ".repeat(Math.max(0, height - 1)));
        if(node != null) {
            sb.append(node).append("\n");
            toString(node.left, height + 1, sb);
            toString(node.right, height + 1, sb);
        }
    }

    /**
     * Performs an in-order traversal on the tree with the defined function
     * @param consumer the function to perform on each element
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

    /**
     * Performs a pre-order traversal on the tree with the defined function
     * @param consumer the function to perform on each element
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
     * Performs a post-order traversal on the tree with the defined function
     * @param consumer the function to perform on each element
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
     * Calculates the height of the tree, defined as the furthers distance of a
     * descendant node from the root
     * @return the height of the tree
     */
    public int height() {
        if(root == null) {
            return 0;
        }
        return height(root, 1);
    }

    private int height(Node<E> node, int height) {
        if(node == null) {
            return height - 1;
        }
        int left = height(node.left, height + 1);
        int right = height(node.right, height + 1);
        return Math.max(left, right);
    }

    /**
     * Returns the number of elements in the tree
     * @return the number of elements in the tree
     */
    public int size() {
        return size(root);
    }

    private int size(Node<E> localRoot) {
        if(localRoot == null) {
            return 0;
        }
        return 1 + size(localRoot.left) + size(localRoot.right);
    }
}
