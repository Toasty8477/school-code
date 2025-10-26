/*
 * Course: CSC-1120
 * Homework 14: Self-Balancing Trees
 * Name: Alexander Horton
 */
package hortona;

/**
 * A Binary Search Tree that uses rotations to self-balance
 * @param <E> the element type stored in the tree
 */
public class BinarySearchTreeWithRotate<E extends Comparable<E>>
        extends BinarySearchTree<E> {
    protected Node<E> rotateLeft(Node<E> node) {
        Node<E> temp = node.right;
        node.right = temp.left;
        temp.left = node;
        return temp;
    }

    protected Node<E> rotateRight(Node<E> node) {
        Node<E> temp = node.left;
        node.left = temp.right;
        temp.right = node;
        return temp;
    }
}