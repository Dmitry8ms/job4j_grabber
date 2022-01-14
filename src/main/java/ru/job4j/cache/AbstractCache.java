package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    protected final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        cache.put(key, new SoftReference<V>(value));
    }

    public V get(K key) {
        V result = null;
        result = cache.getOrDefault(key, new SoftReference<>(null)).get();
        if (result == null) {
            cache.put(key, new SoftReference<>(load(key)));
        }
        return result;
    }

    protected abstract V load(K key);

}
