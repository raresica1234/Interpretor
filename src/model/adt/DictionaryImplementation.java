package model.adt;

import java.util.HashMap;
import java.util.Map;

public class DictionaryImplementation<K, V> implements IDictionary<K, V> {
    private Map<K, V> hashMap;

    public DictionaryImplementation() {
        hashMap = new HashMap<>();
    }

    @Override
    public V lookup(K key) {
        return hashMap.get(key);
    }

    @Override
    public void update(K key, V newValue) {
        hashMap.replace(key, newValue);
    }

    @Override
    public void add(K key, V value) {
        hashMap.put(key, value);
    }

    @Override
    public boolean isDefined(K key) {
        return hashMap.containsKey(key);
    }

    @Override
    public void remove(K key) {
        hashMap.remove(key);
    }

    @Override
    public Map<K, V> getContent() {
        return hashMap;
    }

    @Override
    public IDictionary<K, V> deepCopy() {
        DictionaryImplementation<K, V> copy = new DictionaryImplementation<>();
        hashMap.forEach(((k, v) -> copy.hashMap.put(k, v)));
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (K k : hashMap.keySet()) {
            result.append(k.toString()).append(" = ").append(hashMap.get(k).toString()).append("\n");
        }
        if (result.length() != 0)
            result.deleteCharAt(result.length() - 1);

        return result.toString();
    }
}
