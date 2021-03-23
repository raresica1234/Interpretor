package model.adt;

import java.util.Map;

public interface IHeap<V> {
    boolean isDefined(int address);
    V lookup(int address);
    int getFreeAddress();
    int allocate(V val);
    void update(int address, V value);
    Map<Integer, V> getContent();
    void setContent(Map<Integer, V> content);
}
