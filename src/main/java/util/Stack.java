package util;

import java.util.ArrayList;

public class Stack<T> {
    private ArrayList<T> stack;

    public Stack() {
        stack = new ArrayList<T>();
    }
    public void push(T e) {
        stack.add(e);
    }
    public T pop() {
        return stack.remove(stack.size()-1);
    }
    public int size() {
        return stack.size();
    }
    public boolean isEmpty() {
        return stack.size() == 0;
    }
}
