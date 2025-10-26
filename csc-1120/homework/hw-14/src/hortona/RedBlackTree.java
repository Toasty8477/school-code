/*
 * Course: CSC-1120
 * Homework 14: Self-Balancing Trees
 * Name: Alexander Horton
 */
package hortona;

import java.util.Objects;

/**
 * A Red-Black Tree implementation
 *
 * @param <E> the element type stored in the tree
 */
public class RedBlackTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
    protected static class RedBlackNode<E> extends Node<E> {
        private boolean isRed;

        public RedBlackNode(E item) {
            super(item);
            isRed = true;
        }

        @Override
        public String toString() {
            return (isRed ? "Red  : " : "Black: ") + super.toString();
        }
    }

    private boolean blackReduced;

    @Override
    public boolean add(E item) {
        if (root == null) {
            root = new RedBlackNode<>(item);
            ((RedBlackNode<E>) root).isRed = false;
            addReturn = true;
        } else {
            root = add((RedBlackNode<E>) root, item);
            ((RedBlackNode<E>) Objects.requireNonNull(root)).isRed = false;
        }
        return addReturn;
    }

    private Node<E> add(RedBlackNode<E> localRoot, E item) {
        if (item.compareTo(localRoot.data) == 0) {
            // item already in the tree.
            addReturn = false;
        } else if (item.compareTo(localRoot.data) < 0) {
            // item < localRoot.data.
            if (localRoot.left == null) {
                // Create new left child.
                localRoot.left = new RedBlackNode<>(item);
                addReturn = true;
            } else { // Need to search.
                // Check for two red children, swap colors if found.
                moveBlackDown(localRoot);
                // Recursively add on the left.
                localRoot.left = add((RedBlackNode<E>) localRoot.left, item);

                // See whether the left child is now red
                if (((RedBlackNode<E>) localRoot.left).isRed) {

                    if (localRoot.left.left != null &&
                            ((RedBlackNode<E>) localRoot.left.left).isRed) {
                        // Left-left grandchild is also red.

                        // Single rotation is necessary.
                        ((RedBlackNode<E>) localRoot.left).isRed = false;
                        localRoot.isRed = true;
                        localRoot = (RedBlackNode<E>) rotateRight(localRoot);
                    } else if (localRoot.left.right != null &&
                            ((RedBlackNode<E>) localRoot.left.right).isRed) {
                        // Left-right grandchild is also red.
                        // Double rotation is necessary.
                        localRoot.left = rotateLeft(localRoot.left);
                        ((RedBlackNode<E>) localRoot.left).isRed = false;
                        localRoot.isRed = true;
                        localRoot = (RedBlackNode<E>) rotateRight(localRoot);
                    }
                }
            }
        } else { // item > localRoot.data
            if (localRoot.right == null) {
                // Create new right child.
                localRoot.right = new RedBlackNode<>(item);
                addReturn = true;
            } else { // Need to search.
                // Check for two red children, swap colors if found.
                moveBlackDown(localRoot);
                // Recursively add on the right.
                localRoot.right = add((RedBlackNode<E>) localRoot.right, item);

                // See whether the right child is now red
                if (((RedBlackNode<E>) localRoot.right).isRed) {

                    if (localRoot.right.right != null &&
                            ((RedBlackNode<E>) localRoot.right.right).isRed) {
                        // right-right grandchild is also red.

                        // Single rotation is necessary.
                        ((RedBlackNode<E>) localRoot.right).isRed = false;
                        localRoot.isRed = true;
                        localRoot = (RedBlackNode<E>) rotateLeft(localRoot);
                    } else if (localRoot.right.right != null &&
                            ((RedBlackNode<E>) localRoot.right.left).isRed) {
                        // right-left grandchild is also red.
                        // Double rotation is necessary.
                        localRoot.right = rotateLeft(localRoot.right);
                        ((RedBlackNode<E>) localRoot.right).isRed = false;
                        localRoot.isRed = true;
                        localRoot = (RedBlackNode<E>) rotateLeft(localRoot);
                    }
                }
            }
        }
        return localRoot;
    }

    /**
     * Method to make the two children of a subtree black and the localRoot red
     * if the localRoot has two children and if they are both red, otherwise
     * do nothing
     *
     * @param localRoot The root of the subtree
     */
    private void moveBlackDown(RedBlackNode<E> localRoot) {
        if (localRoot.left != null
                && localRoot.right != null
                && ((RedBlackNode<E>) localRoot.left).isRed
                && ((RedBlackNode<E>) localRoot.right).isRed) {
            //make them black and myself red
            ((RedBlackNode<E>) localRoot.left).isRed = false;
            ((RedBlackNode<E>) localRoot.right).isRed = false;
            localRoot.isRed = true;
        }
    }

    @Override
    public E delete(E item) {
        E oldValue;
        blackReduced = false;
        if (root == null) {
            return null;
        } else {
            int compareReturn = item.compareTo(root.data);
            if (compareReturn == 0) {
                oldValue = root.data;
                root = findReplacement(root);
                if (blackReduced) {
                    assert root != null;
                    root = fixupLeft(root);
                }
            } else if (compareReturn < 0) {
                if (root.left == null) {
                    return null;
                } else {
                    oldValue = removeFromLeft(root, item);
                    if (blackReduced) {
                        root = fixupLeft(root);
                    }
                }
            } else {
                if (root.right == null) {
                    return null;
                } else {
                    oldValue = removeFromRight(root, item);
                    if (blackReduced) {
                        root = fixupRight(root);
                    }

                }
            }
        }
        return oldValue;
    }

    private E removeFromLeft(Node<E> parent, E item) {
        E oldValue;
        if (item.compareTo(parent.left.data) < 0) {
            if (parent.left.left == null) {
                return null;
            } else {
                oldValue = removeFromLeft(parent.left, item);
                if (blackReduced) {
                    parent.left = fixupLeft(parent.left);
                }
            }
        } else if (item.compareTo(parent.left.data) > 0) {
            if (parent.left.right == null) {
                return null;
            } else {
                oldValue = removeFromRight(parent.left, item);
                if (blackReduced) {
                    parent.left = fixupRight(parent.left);
                }
            }
        } else {
            oldValue = parent.left.data;
            parent.left = findReplacement(parent.left);
        }
        return oldValue;
    }

    private E removeFromRight(Node<E> parent, E item) {
        E oldValue;
        if (item.compareTo(parent.right.data) < 0) {
            if (parent.right.left == null) {
                return null;
            } else {
                oldValue = removeFromLeft(parent.right, item);
                if (blackReduced) {
                    parent.right = fixupLeft(parent.right);
                }
            }
        } else if (item.compareTo(parent.right.data) > 0) {
            if (parent.right.right == null) {
                return null;
            } else {
                oldValue = removeFromRight(parent.right, item);
                if (blackReduced) {
                    parent.right = fixupRight(parent.right);
                }
            }
        } else {
            oldValue = parent.right.data;
            parent.right = findReplacement(parent.right);
        }
        return oldValue;
    }

    private Node<E> findReplacement(Node<E> node) {
        Node<E> result = null;
        if (node.left == null) {
            if (((RedBlackNode<E>) node).isRed) {
                result = node.right;
            } else if (node.right == null) {
                blackReduced = true;
            } else if (((RedBlackNode<E>) node.right).isRed) {
                ((RedBlackNode<E>) node.right).isRed = false;
                result = node.right;
            } else {
                throw new RuntimeException("Invalid Red-Black "
                        + "Tree Structure");
            }
        } else if (node.right == null) {
            if (((RedBlackNode<E>) node).isRed) {
                result = node.left;
            } else if (((RedBlackNode<E>) node.left).isRed) {
                ((RedBlackNode<E>) node.left).isRed = false;
                result = node.left;
            } else {
                throw new RuntimeException("Invalid Red-Black "
                        + "Tree structure");
            }
        } else {
            if (node.left.right == null) {
                node.data = node.left.data;
                if (((RedBlackNode<E>) node.left).isRed) {
                    node.left = node.left.left;
                } else if (node.left.left == null) {
                    blackReduced = true;
                    node.left = null;
                } else if (((RedBlackNode<E>) node.left.left).isRed) {
                    ((RedBlackNode<E>) node.left.left).isRed = false;
                    node.left = node.left.left;
                } else {
                    throw new RuntimeException("Invalid Red-Black "
                            + "Tree structure");
                }
                result = node;
            } else {
                node.data = findLargestChild(node.left);
                if (blackReduced) {
                    node.left = fixupRight(node.left);
                }
                if (blackReduced) {
                    result = fixupLeft(node);
                } else {
                    result = node;
                }
            }
        }
        return result;
    }

    private E findLargestChild(Node<E> parent) {
        E returnValue;
        if (parent.right.right == null) {
            returnValue = parent.right.data;
            if (((RedBlackNode<E>) parent.right).isRed) {
                parent.right = parent.right.left;
            } else if (parent.right.left == null) {
                blackReduced = true;
                parent.right = null;
            } else if (((RedBlackNode<E>) parent.right.left).isRed) {
                ((RedBlackNode<E>) parent.right.left).isRed = false;
                parent.right = parent.right.left;
            } else {
                throw new RuntimeException("Invalid Red-Black "
                        + "Tree structure");
            }
        } else {
            returnValue = findLargestChild(parent.right);
            if (blackReduced) {
                parent.right = fixupRight(parent.right);
            }
        }
        return returnValue;
    }

    private Node<E> fixupRight(Node<E> localRoot) {
        Node<E> result;
        if (localRoot.right != null
                && ((RedBlackNode<E>) localRoot.right).isRed) {
            ((RedBlackNode<E>) localRoot.right).isRed = false;
            blackReduced = false;
            return localRoot;
        }
        RedBlackNode<E> s = (RedBlackNode<E>) localRoot.left;
        if (s.isRed) {
            s.isRed = false;
            ((RedBlackNode<E>) localRoot).isRed = true;
            Node<E> returnValue = rotateRight(localRoot);
            returnValue.right = fixupRight(returnValue.right);
            if (blackReduced) {
                result = fixupRight(returnValue);
            } else {
                result = returnValue;
            }
        } else {
            if (s.left == null && s.right == null ||
                    s.left != null && !((RedBlackNode<E>) s.left).isRed
                            && s.right != null && !((RedBlackNode<E>) s.right).isRed) {
                s.isRed = true;
                result = localRoot;
            } else {
                if (s.right != null && ((RedBlackNode<E>) s.right).isRed) {
                    s.isRed = true;
                    ((RedBlackNode<E>) s.right).isRed = false;
                    localRoot.left = rotateLeft(s);
                    s = (RedBlackNode<E>) localRoot.left;
                }
                s.isRed = ((RedBlackNode<E>) localRoot).isRed;
                assert s.left != null;
                ((RedBlackNode<E>) s.left).isRed = false;
                ((RedBlackNode<E>) localRoot).isRed = false;
                blackReduced = false;
                result = rotateRight(localRoot);
            }
        }
        return result;
    }

    private Node<E> fixupLeft(Node<E> localRoot) {
        Node<E> result;
        if (localRoot.left != null
                && ((RedBlackNode<E>) localRoot.left).isRed) {
            ((RedBlackNode<E>) localRoot.left).isRed = false;
            blackReduced = false;
            return localRoot;
        }
        RedBlackNode<E> s = (RedBlackNode<E>) localRoot.right;
        if (s.isRed) {
            s.isRed = false;
            ((RedBlackNode<E>) localRoot).isRed = true;
            Node<E> returnValue = rotateLeft(localRoot);
            returnValue.left = fixupLeft(returnValue.left);
            if (blackReduced) {
                result = fixupLeft(returnValue);
            } else {
                result = returnValue;
            }
        } else {
            if (s.right == null && s.left == null ||
                    s.right != null && !((RedBlackNode<E>) s.right).isRed
                            && s.left != null && !((RedBlackNode<E>) s.left).isRed) {
                s.isRed = true;
                result = localRoot;
            } else {
                if (s.left != null && ((RedBlackNode<E>) s.left).isRed) {
                    s.isRed = true;
                    ((RedBlackNode<E>) s.left).isRed = false;
                    localRoot.right = rotateRight(s);
                    s = (RedBlackNode<E>) localRoot.right;
                }
                s.isRed = ((RedBlackNode<E>) localRoot).isRed;
                assert s.right != null;
                ((RedBlackNode<E>) s.right).isRed = false;
                ((RedBlackNode<E>) localRoot).isRed = false;
                blackReduced = false;
                result = rotateLeft(localRoot);
            }
        }
        return result;
    }
}
