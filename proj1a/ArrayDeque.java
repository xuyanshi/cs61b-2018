public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int capacity;
    private int start;

    public ArrayDeque() {
        size = 0;
        start = 0;
        capacity = 8;
        items = (T[]) new Object[capacity];
    }

    public void addFirst(T item) {
        size++;
    }

    public void addLast(T item) {
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        for (int i = start; i < start + size; i++) {
            System.out.print(items[i]);
            System.out.print(" ");
            System.out.println();
        }
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        size--;
        return null;
    }

    public T removeLast() {
        size--;
        return null;
    }

    public T get(int index) {

        return null;
    }
}
