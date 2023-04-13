public interface Deque<T> {
    boolean isEmpty();

    int size();

    void addFirst(T item);

    void addLast(T item);

    T removeFirst();

    T removeLast();

    void printDeque();

    T get(int index);

}
