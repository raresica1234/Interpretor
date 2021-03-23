package model.adt;

public interface IStack<T> {

    void push(T object);
    T pop();

    boolean isEmpty();

    IStack<T> deepCopy();
}
