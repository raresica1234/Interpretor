package model.adt;

public interface IList<T> {
    void pushBack(T value);
    T popFront();

    int getSize();
    T get(int i);
}
