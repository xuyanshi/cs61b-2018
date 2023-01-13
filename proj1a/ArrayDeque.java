public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int capacity;

    public ArrayDeque() {
        size = 0;
        capacity=8;
        items = (T[]) new Object[capacity];
    }

    public void addFirst(T item) {

    }

    public void addLast(T item) {

    }

    public boolean isEmpty() {
        return true;
    }

    public void printDeque() {

    }

    public int size() {
        return this.size;
    }

    public T removeFirst() {
        return null;
    }

    public T removeLast() {

        return null;
    }

    public T get(int index) {

        return null;
    }
}
