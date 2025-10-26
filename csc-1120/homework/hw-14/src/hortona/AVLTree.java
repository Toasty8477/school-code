/*
 * Course: CSC-1120
 * Homework 14: Self-Balancing Trees
 * Name: Alexander Horton
 */
package hortona;

/**
 * An AVL Tree
 * @param <E> the element type stored in the tree
 */
public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
    private static class AVLNode<E> extends Node<E> {
        /**
         * Left Heavy
         */
        public static final int LEFT_HEAVY = -1;
        /**
         * Balanced
         */
        public static final int BALANCED = 0;
        /**
         * Right Heavy
         */
        public static final int RIGHT_HEAVY = 1;
        private int balance;

        private AVLNode(E item) {
            super(item);
            balance = BALANCED;
        }

        @Override
        public String toString() {
            return balance + ": " + super.toString();
        }
    }

    private boolean increase;
    private boolean decrease;

    @Override
    public boolean add(E item) {
        increase = false;
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    private AVLNode<E> add(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            return new AVLNode<>(item);
        }

        if (item.compareTo(localRoot.data) == 0) {
            increase = false;
            addReturn = false;
        } else if (item.compareTo(localRoot.data) < 0) {
            localRoot.left = add((AVLNode<E>) localRoot.left, item);
            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
        } else {
            localRoot.right = add((AVLNode<E>) localRoot.right, item);
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    increase = false;
                    return rebalanceRight(localRoot);
                }
            }
        }
        return localRoot;
    }

    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        if (leftChild.balance > AVLNode.BALANCED) {
            updateLeftBalances(localRoot, leftChild);
            localRoot.left = rotateLeft(leftChild);
        } else {
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        return (AVLNode<E>) rotateRight(localRoot);
    }

    private void updateLeftBalances(AVLNode<E> localRoot, AVLNode<E> leftChild) {
        AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
        if (leftRightChild.balance < AVLNode.BALANCED) {
            leftChild.balance = AVLNode.LEFT_HEAVY;
            leftRightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        } else if (leftRightChild.balance > AVLNode.BALANCED) {
            leftChild.balance = AVLNode.BALANCED;
            leftRightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.RIGHT_HEAVY;
        } else {
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
    }

    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        if (rightChild.balance < AVLNode.BALANCED) {
            updateRightBalances(localRoot, rightChild);
            localRoot.right = rotateRight(rightChild);
        } else {
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        return (AVLNode<E>) rotateLeft(localRoot);
    }

    private void updateRightBalances(AVLNode<E> localRoot, AVLNode<E> rightChild) {
        AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
        if (rightLeftChild.balance > AVLNode.BALANCED) {
            rightChild.balance = AVLNode.RIGHT_HEAVY;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        } else if (rightLeftChild.balance < AVLNode.BALANCED) {
            rightChild.balance = AVLNode.BALANCED;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.LEFT_HEAVY;
        } else {
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
    }

    private void decrementBalance(AVLNode<E> node) {
        node.balance--;
        if (node.balance == AVLNode.BALANCED) {
            increase = false;
        }
    }

    private void incrementBalance(AVLNode<E> node) {
        node.balance++;
        if (node.balance == AVLNode.BALANCED) {
            increase = false;
        }
    }

    @Override
    public E delete(E item) {
        decrease = false;
        root = delete((AVLNode<E>) root, item);
        return deleteReturn;
    }

    private AVLNode<E> delete(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            deleteReturn = null;
        }
        if (item.compareTo(localRoot.data) == 0) {
            deleteReturn = localRoot.data;
            return findReplacementNode(localRoot);
        } else if (item.compareTo(localRoot.data) < 0) {
            localRoot.left = delete((AVLNode<E>) localRoot.left, item);
            if (decrease) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    return rebalanceRightL(localRoot);
                }
            }
        } else {
            localRoot.right = delete((AVLNode<E>) localRoot.right, item);
            if (decrease) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    return rebalanceLeftR(localRoot);
                }
            }
        }
        return localRoot;
    }

    private AVLNode<E> findReplacementNode(AVLNode<E> node) {
        if (node.left == null) {
            decrease = true;
            return (AVLNode<E>) node.right;
        } else if (node.right == null) {
            decrease = true;
            return (AVLNode<E>) node.left;
        } else {
            if (node.left.right == null) {
                node.data = node.left.data;
                node.left = node.left.left;
                incrementBalance(node);
            } else {
                node.data = findLargestChild((AVLNode<E>) node.left);
                if (((AVLNode<E>) node.left).balance < AVLNode.LEFT_HEAVY) {
                    node.left = rebalanceLeft((AVLNode<E>) node.left);
                }
                if (decrease) {
                    incrementBalance(node);
                }
            }
            return node;
        }
    }

    private E findLargestChild(AVLNode<E> parent) {
        E returnValue;
        if (parent.right.right == null) {
            returnValue = parent.right.data;
            parent.right = parent.right.left;
            decrementBalance(parent);
        } else {
            returnValue = findLargestChild((AVLNode<E>) parent.right);
            if (((AVLNode<E>) parent.right).balance < AVLNode.LEFT_HEAVY) {
                parent.right = rebalanceLeft((AVLNode<E>) parent.right);
            }
            if (decrease) {
                decrementBalance(parent);
            }
        }
        return returnValue;
    }

    private AVLNode<E> rebalanceLeftR(AVLNode<E> localRoot) {
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        if (leftChild.balance > AVLNode.BALANCED) {
            updateLeftBalances(localRoot, leftChild);
            increase = false;
            decrease = true;
            localRoot.left = rotateLeft(leftChild);
            return (AVLNode<E>) rotateRight(localRoot);
        }
        if (leftChild.balance < AVLNode.BALANCED) {
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            increase = false;
            decrease = true;
        } else {
            leftChild.balance = AVLNode.RIGHT_HEAVY;
            localRoot.balance = AVLNode.LEFT_HEAVY;
        }
        return (AVLNode<E>) rotateRight(localRoot);
    }

    private AVLNode<E> rebalanceRightL(AVLNode<E> localRoot) {
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        if (rightChild.balance < AVLNode.BALANCED) {
            updateRightBalances(localRoot, rightChild);
            increase = false;
            decrease = true;
            localRoot.right = rotateRight(rightChild);
            return (AVLNode<E>) rotateLeft(localRoot);
        }
        if (rightChild.balance > AVLNode.BALANCED) {
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            increase = false;
            decrease = true;
        } else {
            rightChild.balance = AVLNode.LEFT_HEAVY;
            localRoot.balance = AVLNode.RIGHT_HEAVY;
        }
        return (AVLNode<E>) rotateLeft(localRoot);
    }
}
