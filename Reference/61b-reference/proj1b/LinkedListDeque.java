public class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        T item;
        Node prev;
        Node next;

        Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    /**
     * The first item (if it exists) is at sentinel.next.
     */
    private Node sentinel;
    private int size;

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T item) {
        Node t = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = t;
        sentinel.next = t;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node t = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = t;
        sentinel.prev = t;
        size++;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next != sentinel) {
            T result = sentinel.next.item;
            sentinel.next.item = null;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size--;
            return result;
        }
        return null;
    }

    @Override
    public T removeLast() {
        if (sentinel.prev != sentinel) {
            T result = sentinel.prev.item;
            sentinel.prev.item = null;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size--;
            return result;
        }
        return null;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            if (p.next != sentinel) {
                System.out.print(" ");
            }
            p = p.next;
        }
    }

    @Override
    public T get(int index) {
        int i = 0;
        Node p = sentinel.next;
        while (p != sentinel && i != index) {
            p = p.next;
            i++;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        Node first = sentinel.next;
        return getRecursiveHelper(first, index);
    }

    private T getRecursiveHelper(Node p, int index) {
        if (index == 0 || p == sentinel) {
            return p.item;
        } else {
            return getRecursiveHelper(p.next, index - 1);
        }
    }
}
