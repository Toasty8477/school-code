/*
 * Course: CSC-1120A
 * Lab 11: Morse Encoder
 * Name: Alexander Horton
 * Last Updated: 04/15/2025
 */

package hortona;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Collection;

/**
 * Hash map using chained addressing
 * @param <K> key
 * @param <V> value
 */
public class HashMap<K, V> implements Map<K, V> {

    private static class HashMapEntry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private V value;

        private HashMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public String toString() {
            return key + "=>" + value;
        }
    }

    private final int capacity = 1024;
    private List<Map.Entry<K, V>>[] entries;
    private int numKeys;

    /**
     * Makes a hash map with the default capacity
     */
    public HashMap() {
        entries = (List<Map.Entry<K, V>>[]) new LinkedList[capacity];
        numKeys = 0;
    }

    @Override
    public V get(Object key) {
        V result = null;
        int index = getIndex(key);
        if (entries[index] != null) {
            for (int i = 0; i < entries[index].size(); i++) {
                if(entries[index].get(i).getKey().equals(key)) {
                    result = entries[index].get(i).getValue();
                }
            }
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        int index = getIndex(key);
        if (entries[index] == null) {
            entries[index] = new LinkedList<>();
        }
        entries[index].addFirst(new HashMapEntry<>(key, value));
        numKeys++;
        return value;
    }

    @Override
    public V remove(Object key) {
        V removed = null;
        int index = getIndex(key);
        if (entries[index] != null) {
            for (int i = 0; i < entries[index].size(); i++) {
                if(entries[index].get(i).getKey().equals(key)) {
                    removed = entries[index].get(i).getValue();
                    entries[index].remove(i);
                    numKeys--;
                }
            }
        }
        if (entries[index].isEmpty()) {
            entries[index] = null;
        }
        return removed;
    }

    @Override
    public void clear() {
        entries = (List<Map.Entry<K, V>>[]) new LinkedList[capacity];
    }

    @Override
    public int size() {
        return numKeys;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = getIndex(key);
        if (entries[index] != null) {
            for (int i = 0; i < entries[index].size(); i++) {
                if (entries[index].get(i).getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }

    private int getIndex(Object key) {
        int index = key.hashCode() % this.entries.length;
        if (index < 0) {
            index += entries.length;
        }
        return index;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
