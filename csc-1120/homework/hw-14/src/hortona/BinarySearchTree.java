/*
 * Course: CSC-1120
 * Homework 14: Self-Balancing Trees
 * Name: Alexander Horton
 */
package hortona;

import java.util.ArrayList;
import java.util.List;

/**
 * A Binary Search Tree tht does not allow duplicate values
 * @param <E> the element type stored in the tree
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E>
        implements SearchTree<E> {
    protected boolean addReturn;
    protected E deleteReturn;

    /**
     * No-parameter constructor
     */
    public BinarySearchTree() {
        super();
        addReturn = false;
        deleteReturn = null;
    }

    @Override
    public boolean add(E item) {
        root = add(root, item);
        return addReturn;
    }

    private Node<E> add(Node<E> localRoot, E item) {
        if(localRoot == null) {
            addReturn = true;
            return new Node<>(item);
        } else if(item.compareTo(localRoot.data) == 0) {
            addReturn = false;
            return localRoot;
        } else if(item.compareTo(localRoot.data) < 0) {
            localRoot.left = add(localRoot.left, item);
            return localRoot;
        } else {
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }

    @Override
    public boolean contains(E target) {
        return contains(target, root);
    }

    private boolean contains(E target, Node<E> localRoot) {
        if(localRoot == null) {
            return false;
        }
        int compareResult = target.compareTo(localRoot.data);
        if(compareResult == 0) {
            return true;
        } else if(compareResult < 0) {
            return contains(target, localRoot.left);
        } else {
            return contains(target, localRoot.right);
        }
    }

    @Override
    public E find(E target) {
        return find(root, target);
    }

    private E find(Node<E> localRoot, E target) {
        if(localRoot == null) {
            return null;
        }
        int compareResult = target.compareTo(localRoot.data);
        if(compareResult == 0) {
            return localRoot.data;
        } else if(compareResult < 0) {
            return find(localRoot.left, target);
        } else {
            return find(localRoot.right, target);
        }
    }

    @Override
    public E delete(E target) {
        root = delete(root, target);
        return deleteReturn;
    }

    private Node<E> delete(Node<E> localRoot, E target) {
        if(localRoot == null) {
            deleteReturn = null;
            return null;
        }
        int compareResult = target.compareTo(localRoot.data);
        if(compareResult == 0) {
            deleteReturn = localRoot.data;
            if(localRoot.left == null) {
                return localRoot.right;
            } else if(localRoot.right == null) {
                return localRoot.left;
            } else {
                if(localRoot.left.right == null) {
                    localRoot.data = localRoot.left.data;
                    localRoot.left = localRoot.left.left;
                } else {
                    localRoot.data = findLargestChild(localRoot.left);
                }
            }
        } else if(compareResult < 0) {
            localRoot.left = delete(localRoot.left, target);
        } else {
            localRoot.right = delete(localRoot.right, target);
        }
        return localRoot;
    }

    private E findLargestChild(Node<E> parent) {
        if(parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        } else {
            return findLargestChild(parent.right);
        }
    }

    @Override
    public boolean remove(E target) {
        deleteReturn = null;
        root = delete(root, target);
        return deleteReturn == null;
    }

    @Override
    public List<E> toList() {
        List<E> list = new ArrayList<>();
        toList(list, root);
        return list;
    }

    private void toList(List<E> list, Node<E> node) {
        if(node != null) {
            toList(list, node.left);
            list.add(node.data);
            toList(list, node.right);
        }
    }

    @Override
    public void clear() {
        root = null;
    }
}
