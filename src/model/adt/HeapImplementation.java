package model.adt;


import java.util.HashMap;
import java.util.Map;

public class HeapImplementation<V> implements IHeap<V> {
    private Map<Integer, V> heap;
    private int freeAddress = 1;

    public HeapImplementation() {
        heap = new HashMap<>();
    }

    @Override
    public boolean isDefined(int address) {
        return heap.containsKey(address);
    }

    @Override
    public V lookup(int address) {
        return heap.get(address);
    }

    @Override
    public int getFreeAddress() {
        return freeAddress;
    }

    @Override
    public int allocate(V val) {
        int currentAddr = freeAddress++;
        heap.put(currentAddr, val);
        return currentAddr;
    }

    @Override
    public void update(int address, V value) {
        heap.replace(address, value);
    }

    @Override
    public Map<Integer, V> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, V> content) {
        heap = content;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int address : heap.keySet()) {
            result.append(address).append(" -> ").append(heap.get(address).toString()).append("\n");
        }
        if (result.length() != 0)
            result.deleteCharAt(result.length() - 1);

        return result.toString();
    }
}
