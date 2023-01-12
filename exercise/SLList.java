public class SLList {
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode first;
    private int size;

    public SLList() {
        first = null;
        size = 0;
    }

    public SLList(int x) {
        first = new IntNode(x, null);
        size = 1;
    }

    public void addFirst(int x) {
        size++;
        first = new IntNode(x, first);
    }

    public int getFirst() {
        return first.item;
    }

    public void addLast(int x) {
        this.size++;
        if (size == 1) {
            first = new IntNode(x, first);
        } else {
            IntNode ptr = first;
            while (ptr.next != null) {
                ptr = ptr.next;
            }
            ptr.next = new IntNode(x, null);
        }
    }

    public int size() {
        return this.size;
    }

    public void print() {
        IntNode ptr = this.first;
        while (ptr != null) {
            System.out.print(ptr.item);
            System.out.print(" ");
            ptr = ptr.next;
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        SLList L = new SLList();
        L.addLast(10);
        L.print();
    }
}
