package model.adt;

import java.util.Stack;

public class StackImplementation<T> implements IStack<T> {
    private Stack<T> stack;

    public StackImplementation() {
        stack = new Stack<>();
    }

    @Override
    public void push(T object) {
        stack.push(object);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public IStack<T> deepCopy() {
        StackImplementation<T> copy = new StackImplementation<>();
        stack.forEach(e -> copy.stack.addElement(e));
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Stack<T> copy = new Stack<>();
        for (T t : stack)
            copy.push(t);

        while (!copy.isEmpty())
            result.append(copy.pop().toString()).append("\n");

        return result.toString();
    }
}
