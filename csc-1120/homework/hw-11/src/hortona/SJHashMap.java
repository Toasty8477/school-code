/*
 * Course: CS-2852
 * HashMap Homework
 * Name: Alexander Horton
 * Last Updated: 04/11/2025
 */
package hortona;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Simplified implementation of a Hashmap
 * @param <K> Generic type for a Key object
 * @param <V> Generic type for a Value object
 */
public class SJHashMap<K, V> implements Map<K, V> {
    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return this.key == null ? null : key + "=" + value.toString();
        }
    }

    private static final int STARTING_CAPACITY = 10;
    private static final double LOAD_THRESHOLD = 0.75;
    private final Entry<K, V> deletedKey = new Entry<>(null, null);
    private Entry<K, V>[] entries;
    private int numKeys;
    private int numDeletes;
    private int timesCalled;
    private double probes;

    /**
     * Constructor that sets a given starting capacity
     *
     * @param capacity starting capacity of the table
     */
    public SJHashMap(int capacity) {
        this.numKeys = 0;
        this.numDeletes = 0;
        entries = new Entry[capacity];
    }

    /**
     * Constructor that uses the default starting capacity
     */
    public SJHashMap() {
        this(STARTING_CAPACITY);
    }

    @Override
    public int size() {
        return this.numKeys;
    }

    @Override
    public boolean isEmpty() {
        return this.numKeys == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = findKey(key);
        timesCalled++;
        K k = find(index, key);
        return key.equals(k);
    }
    private K find(int index, Object key) {
        while(entries[index] != null && !key.equals(entries[index].getKey())) {
            index++;
            if (index == entries.length) {
                index = 0;
            }
            probes++;
        }
        return entries[index] != null ? entries[index].getKey() : null;
    }

    @Override
    public boolean containsValue(Object value) {
        if(value != null) {
            for (Entry<K, V> e : entries) {
                if (e != null && value.equals(e.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        V result = null;
        int index = findKey(key);
        timesCalled++;
        while(entries[index] != null && entries[index] != deletedKey
                && !entries[index].getKey().equals(key)) {
            index++;
            index %= entries.length;
            probes++;
        }
        if (entries[index] != null && entries[index] != deletedKey
                && entries[index].getKey().equals(key)) {
            result = entries[index].getValue();
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        for(Entry<K, V> e : entries) {
            if (e != null && e.getKey().equals(key)) {
                return e.getValue();
            }
        }
        int index = findKey(key);
        timesCalled++;
        while(entries[index] != null) {
            index++;
            index %= entries.length;
            probes++;
        }

        entries[index] = new Entry<>(key, value);
        numKeys++;
        if((double)numKeys / entries.length > LOAD_THRESHOLD) {
            rehash();
        }
        return value;
    }

    @Override
    public V remove(Object key) {
        int index = findKey(key);
        timesCalled++;
        V old = null;
        while (entries[index] != null && !entries[index].getKey().equals(key)) {
            index++;
            index %= entries.length;
            probes++;
        }
        if (entries[index] != null) {
            old = entries[index].value;
            entries[index] = deletedKey;
            numDeletes++;
            numKeys--;
        }
        return old;
    }

    @Override
    public void clear() {
        numKeys = 0;
        numDeletes = 0;
        Arrays.fill(entries, null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for(Entry<K, V> e : entries) {
            if(e != null) {
                sb.append(e).append(", ");
            }
        }
        sb.replace(sb.length() - 2, sb.length(), "");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Calculates the ratio of the number of probes per times find is called
     * @return average number of probes per find
     */
    public double averageProbes() {
        return probes/timesCalled;
    }

    private int findKey(Object key) {
        int index = key.hashCode() % this.entries.length;
        if (index < 0) {
            index += entries.length;
        }
        return index;
    }

    private void rehash() {
        Entry<K, V>[] oldEntries = entries;
        entries = new Entry[entries.length * 2 + 1];
        numKeys = 0;
        numDeletes = 0;
        for (Entry<K, V> entry : oldEntries) {
            if (entry != null && entry != deletedKey) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Set<? extends K> set = m.keySet();
        for(K k : set) {
            put(k, m.get(k));
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for(Entry<K, V> e : entries) {
            if(e != null && e.key != null) {
                keys.add(e.key);
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> vals = new ArrayList<>();
        for(Entry<K, V> e : entries) {
            if(e != null && e.value != null) {
                vals.add(e.value);
            }
        }
        return vals;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for(Entry<K, V> e : entries) {
            if(e != null && e.key != null) {
                set.add(e);
            }
        }
        return set;
    }
}
