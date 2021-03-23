package model.adt;


import java.util.Map;

public interface IDictionary<K, V> {
    V lookup(K key);
    void update(K key, V newValue);
    void add(K key, V value);
    boolean isDefined(K key);
    void remove(K key);
    Map<K, V> getContent();
    IDictionary<K, V> deepCopy();
}
