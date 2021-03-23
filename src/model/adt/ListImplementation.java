package model.adt;

import java.util.LinkedList;

public class ListImplementation<T> implements IList<T> {
    private LinkedList<T> linkedList;

    public ListImplementation() {
        linkedList = new LinkedList<>();
    }

    @Override
    public void pushBack(T value) {
        linkedList.addLast(value);
    }

    @Override
    public T popFront() {
        return linkedList.removeFirst();
    }

    @Override
    public int getSize() {
        return linkedList.size();
    }

    @Override
    public T get(int i) {
        return linkedList.get(i);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if(!linkedList.isEmpty())
            for (int i = 0; i < linkedList.size(); i++) {
                result.append(linkedList.get(i).toString());
                if (i != linkedList.size() - 1)
                    result.append("\n");
            }
        return result.toString();
    }
}
