/*
 * Course: CSC1120 - 131
 * Spring 2025
 * Lab 13 - More Autocompleter
 * Name: Alexander Horton
 * Updated: 4/24/2025
 */

package hortona.model;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 * Map using a List as a backing structure
 * @param <K> key
 * @param <V> value
 */
public class ListMap<K, V> implements Map<K, V> {

    private final List<Map.Entry<K, V>> entries;

    /**
     * No parameter constructor
     */
    public ListMap() {
        entries = new ArrayList<>();
    }

    @Override
    public V get(Object key) {
        if (containsKey(key)) {
            for (Entry e : entries) {
                if (e != null && e.getKey().equals(key)) {
                    return (V) (e.getValue());
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        V oldValue = get(key);
        if (containsKey(key)) {
            boolean set = false;
            for (int i = 0; i < entries.size() && !set; i++) {
                Entry e = entries.get(i);
                if (e != null && e.getKey().equals(key)) {
                    entries.set(i, Map.entry(key, value));
                    set = true;
                }
            }
        } else {
            entries.add(Map.entry(key, value));
        }
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        V oldValue = get(key);
        if (containsKey(key)) {
            boolean removed = false;
            for (int i = 0; i < entries.size() && !removed; i++) {
                Entry e = entries.get(i);
                if (e != null && e.getKey().equals(key)) {
                    entries.remove(i);
                    removed = true;
                }
            }
        }
        return oldValue;
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        for (Entry e : entries) {
            if (e != null && e.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>(entries);
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
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}
