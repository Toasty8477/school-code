[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/MU49IB8e)
# Homework 14 - Self-Balancing Rotating Trees

``AVL`` and ``RedBlack Trees`` are ``BinarySearchTrees`` that use rotation to keep themselves balanced at all times, thus enforcing the **O(logn)** time complexity for adding, accessing, and deleting elements. An ``AVLTree`` does this by keeping track of the relative depth of all the subtrees, using rotation to rebalance whenever a tree has one subtree whose height is 2 more than its other subtree. A ``RedBlackTree`` does this through a set of **invariants** (rules) that are checked after every modification to the tree to ensure all the rules are being followed.

For this assignment you will implement the following:
* In the ``SearchTreeWithRotation`` class, implement the ``rotateLeft(Node<E> node)`` and ``rotateRight(Node<E> node)`` methods that will rotate left or right a tree rooted at the node passed in.
* In the ``RedBlackTree`` class, complete the add method to handle adding an element to the right side of the root
    * You will also need to implement the ``moveBlackDown()`` helper method. See the javadocs for details
 

All of the code that we have written for ``BinaryTree`` and ``BinarySearchTree`` is provided, as well as most of the implementations of the ``AVLTree`` and ``RedBlackTree`` classes.
