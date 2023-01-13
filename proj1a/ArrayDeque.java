public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int capacity;
    private int start;

    public ArrayDeque() {
        size = 0;
        start = 0;
        // capacity = 8;
        capacity = 10000;
        items = (T[]) new Object[capacity];
    }

    private int prevOne(int i) {
        return (i - 1 + capacity) % capacity;
    }

    private int nextOne(int i) {
        return (i + 1 + capacity) % capacity;
    }

    public void addFirst(T item) {
        start = prevOne(start);
        items[start] = item;
        size++;
    }

    public void addLast(T item) {
        items[(start + size) % capacity] = item;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        for (int i = start; i < start + size && i < capacity; i++) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
        for (int i = 0; i < start + size - capacity && i < capacity; i++) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removeItem = items[start];
        start = nextOne(start);
        size--;
        return removeItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removeItem = items[(start + size - 1) % capacity];
        size--;
        return removeItem;
    }

    public T get(int index) {
        return items[(index + start) % capacity];
    }
}
