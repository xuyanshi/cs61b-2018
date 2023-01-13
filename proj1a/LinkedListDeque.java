public class LinkedListDeque<T> {
    private class Node {
        private T item;
        private Node next;
        private Node prev;

        public Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
            if (n != null) {
                n.prev = this;
            }
            if (p != null) {
                p.next = this;
            }

        }
    }

    private Node sentinel;
    private Node last;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node((T) new Object(), null, null);
        last = sentinel;
    }

//    public LinkedListDeque(T it) {
//        size = 1;
//        sentinel = new Node((T) new Object(), null, null);
//        last = new Node(it, null, sentinel);
//    }

    public void addFirst(T it) {
        Node newnode = new Node(it, sentinel.next, sentinel);
        size++;
    }

    public void addLast(T it) {
        Node newnode = new Node(it, null, last);
        last = newnode;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        Node ptr = sentinel;
        while (ptr.next != null) {
            ptr = ptr.next;
            System.out.print(ptr.item);
            System.out.print(" ");
        }
        System.out.println("");
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node removenode = sentinel.next;
        sentinel.next = removenode.next;
        if (removenode == last) {
            last = sentinel;
        } else {
            removenode.next.prev = sentinel;
        }
        size--;
        return removenode.item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node removenode = last;
        last = removenode.prev;
        last.next = null;
        size--;
        return removenode.item;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node ptr = sentinel.next;
        int cnt = 0;
        while (cnt < index) {
            cnt++;
            ptr = ptr.next;
        }
        return ptr.item;
    }

    public T getRecursive(int index) {

        return helper(index, sentinel.next);
    }

    private T helper(int index, Node nownode) {
        if (index >= size || index < 0) {
            return null;
        }
        if (index == 0) {
            return nownode.item;
        }
        return helper(index - 1, nownode.next);
    }

    public static void main(String[] args) {

    }
}
