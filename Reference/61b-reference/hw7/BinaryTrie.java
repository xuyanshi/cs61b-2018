import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {
    Node root;

    private class Node implements Comparable<Node>, Serializable {
        Node left;
        Node right;
        char ch;
        int freq;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.left = left;
            this.right = right;
            this.freq = freq;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char c : frequencyTable.keySet()) {
            pq.add(new Node(c, frequencyTable.get(c), null, null));
        }
        while (pq.size() > 1) {
            Node left = pq.remove();
            Node right = pq.remove();
            Node newNode = new Node('\0', left.freq + right.freq, left, right);
            pq.add(newNode);
        }
        root = pq.remove();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        int len = querySequence.length();
        Node curr = root;
        int i;
        for (i = 0; i < len; i++) {
            if (curr.isLeaf()) {
                break;
            }
            if (querySequence.bitAt(i) == 0) {
                curr = curr.left;
            } else if (querySequence.bitAt(i) == 1) {
                curr = curr.right;
            }
        }
        BitSequence sequence = new BitSequence(querySequence.firstNBits(i));
        return new Match(sequence, curr.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        HashMap<Character, BitSequence> table = new HashMap<>();
        buildSequence(root, new BitSequence(), table);
        return table;
    }

    private void buildSequence(Node node, BitSequence currSeq, Map<Character, BitSequence> map) {
        if (node.isLeaf()) {
            map.put(node.ch, currSeq);
        } else {
            if (node.left != null) {
                buildSequence(node.left, currSeq.appended(0), map);
            }
            if (node.right != null) {
                buildSequence(node.right, currSeq.appended(1), map);
            }
        }
        return;
    }
}
