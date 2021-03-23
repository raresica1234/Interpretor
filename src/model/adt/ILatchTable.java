package model.adt;

import java.util.Map;

public interface ILatchTable {
    int lookup(int id);
    void update(int id, int v);
    int allocate(int val);
    boolean isDefined(int id);
    Map<Integer, Integer> getContent();

}
