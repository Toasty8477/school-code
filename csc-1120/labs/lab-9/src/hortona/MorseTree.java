/*
 * Course: CSC1020
 * Morse Code Decoder
 * Name: Alexander Horton
 * Last Updated: 03/28/2025
 */

package hortona;

import java.util.Optional;

/**
 * Binary tree for decoding morse code messages
 * @param <E> type of the symbol
 */
public class MorseTree<E> {

    private static class Node<E> {
        private Node<E> dot;
        private Node<E> dash;
        private E symbol;

        private Node(E data) {
            this.symbol = data;
            this.dot = null;
            this.dash = null;
        }
        private Node(E data, Node<E> dot, Node<E> dash) {
            this.symbol = data;
            this.dot = dot;
            this.dash = dash;
        }

        @Override
        public String toString() {
            return this.symbol.toString();
        }
    }

    private Node<E> root;

    /**
     * No parameter constructor for MorseTree
     */
    public MorseTree() {
        this.root = new Node<>(null);
    }

    /**
     * add a symbol to the tree
     * @param symbol letter/digit/punctuation to add to the tree
     * @param code Morse code associated with symbol
     * @throws IllegalArgumentException if the code is anything other than dots or dashes (. or -)
     */
    public void add(E symbol, String code) throws IllegalArgumentException {
        checkValid(code);
        add(symbol, code, root);
    }
    private void add(E symbol, String code, Node<E> node) {
        if (code.length() == 1) {
            if (code.charAt(0) == '.') {
                if (node.dot != null) {
                    node.dot = new Node<>(symbol, node.dot.dot, node.dot.dash);
                } else {
                    node.dot = new Node<>(symbol);
                }
            } else {
                if (node.dash != null) {
                    node.dash = new Node<>(symbol, node.dash.dot, node.dash.dash);
                } else {
                    node.dash = new Node<>(symbol);
                }
            }
        } else {
            if (code.charAt(0) == '.') {
                if (node.dot != null) {
                    node.dot = new Node<>(node.dot.symbol, node.dot.dot, node.dot.dash);
                } else {
                    node.dot = new Node<>(null);
                }
                add(symbol, code.substring(1), node.dot);
            } else {
                if (node.dash != null) {
                    node.dash = new Node<>(node.dash.symbol, node.dash.dot, node.dash.dash);
                } else {
                    node.dash = new Node<>(null);
                }
                add(symbol, code.substring(1), node.dash);
            }
        }
    }

    /**
     * Decodes one Morse Code character
     * @param code Morse code to decode
     * @return Optional containing the decoded symbol or an empty optional if not found
     * @throws IllegalArgumentException if the code is anything other than dots or dashes (. or -)
     */
    public Optional<E> decode(String code) throws IllegalArgumentException {
        checkValid(code);
        return decode(code, root);
    }
    private Optional<E> decode(String code, Node<E> node) {
        if (node == null || code.isEmpty() && node.symbol == null) {
            return Optional.empty();
        }
        if (code.isEmpty()) {
            return Optional.of(node.symbol);
        }
        if (code.charAt(0) == '.') {
            return decode(code.substring(1), node.dot);
        } else {
            return decode(code.substring(1), node.dash);
        }
    }

    @Override
    public String toString() {
        return preorder(root);
    }

    private String preorder(Node<E> node) {
        if (node == null) {
            return "";
        }
        if (node.symbol != null) {
            return String.format("%-4s\n", node.symbol) + preorder(node.dot) + preorder(node.dash);
        } else {
            return preorder(node.dot) + preorder(node.dash);
        }
    }

    private void checkValid(String code) throws IllegalArgumentException {
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) != '.' && code.charAt(i) != '-') {
                throw new IllegalArgumentException("Warning! Skipping: " + code);
            }
        }
    }
}
