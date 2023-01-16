public interface Deque<T> {
    void addFirst(T item);

    void addLast(T item);

    boolean isEmpty();

    int size();

    void printDeque();

    T getRecursive(int i);

    T removeFirst();

    T removeLast();

    T get(int index);
}
