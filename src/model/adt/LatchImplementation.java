package model.adt;

import java.util.HashMap;
import java.util.Map;

public class LatchImplementation implements ILatchTable {
    private Map<Integer, Integer> hashMap;
    private int freeAddress = 1;

    public LatchImplementation() {
        this.hashMap = new HashMap<>();
    }

    @Override
    public int lookup(int id) {
        return hashMap.get(id);
    }

    @Override
    public void update(int id, int v) {
        hashMap.replace(id, v);
    }

    @Override
    public int allocate(int val) {
        int currentAddress = freeAddress++;
        hashMap.put(currentAddress, val);
        return currentAddress;
    }

    @Override
    public boolean isDefined(int id) {
        return hashMap.containsKey(id);
    }

    @Override
    public Map<Integer, Integer> getContent() {
        return hashMap;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int address : hashMap.keySet()) {
            result.append(address).append(" -> ").append(hashMap.get(address).toString()).append("\n");
        }
        if (result.length() != 0)
            result.deleteCharAt(result.length() - 1);

        return result.toString();
    }
}
