public class SLList {
    private IntNode first;

    public SLList(int x) {
        first = new IntNode(x, null);
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public int getFirst() {
        return first.item;
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
        SLList L = new SLList(15);
        L.addFirst(10);
        L.addFirst(5);
        L.print();
    }
}
