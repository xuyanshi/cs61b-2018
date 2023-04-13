public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int length;

    public ArrayDeque() {
        length = 8;
        items = (T[]) (new Object[length]);
        size = 0;
        nextFirst = length / 2;
        nextLast = length / 2 + 1;
    }

    @Override
    public int size() {
        return size;
    }

    private int getIndex(int i) {
        return (length + i) % length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            resize(length * 2);
        }
        items[nextFirst] = item;
        nextFirst = getIndex(nextFirst - 1);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (nextFirst == nextLast) {
            resize(length * 2);
        }
        items[nextLast] = item;
        nextLast = getIndex(nextLast + 1);
        size++;
    }

    /**
     * Resizes the underlying array to the target capacity.
     */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (capacity > length || nextFirst > nextLast) {
            System.arraycopy(items, getIndex(nextFirst + 1),
                    a, 0, length - getIndex(nextFirst + 1));
            System.arraycopy(items, 0,
                    a, length - getIndex(nextFirst + 1), getIndex(nextLast - 1) + 1);
        } else {
            System.arraycopy(items, getIndex(nextFirst + 1), a, 0, size);
        }
        length = capacity;
        nextFirst = getIndex(-1);
        nextLast = size;
        items = a;
    }

    @Override
    public T removeFirst() {
        int nextIndex = getIndex(nextFirst + 1);
        T result = items[nextIndex];
        if (result != null) {
            items[nextIndex] = null;
            nextFirst = nextIndex;
            size--;
        }
        if (size < 0.25 * length && length > 16) {
            resize(length / 2);
        }
        return result;
    }

    @Override
    public T removeLast() {
        int nextIndex = getIndex(nextLast - 1);
        T result = items[nextIndex];
        if (result != null) {
            items[nextIndex] = null;
            nextLast = getIndex(nextLast - 1);
            size--;
        }
        if (size < 0.25 * length && length > 16) {
            resize(length / 2);
        }
        return result;
    }

    @Override
    public void printDeque() {
        int begin = getIndex(nextFirst + 1);
        int end = getIndex(nextLast - 1);
        while (begin != end) {
            System.out.print(get(begin) + " ");
            begin = getIndex(begin + 1);
        }
        System.out.print(get(end));
    }

    @Override
    public T get(int index) {
        int i = getIndex(nextFirst + 1 + index);
        return items[i];
    }

}
