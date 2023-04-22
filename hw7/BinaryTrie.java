import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xuyanshi
 * @date 2023/4/20 03:55
 */
public class BinaryTrie implements Serializable {
    // alphabet size of extended ASCII
    private static final int R = 256;

    /**
     * Constructor.
     * Given a frequency table which maps symbols of type V to their relative frequencies,
     * the constructor should build a Huffman decoding trie according to the procedure discussed in class.
     * You may find implementations of Huffman codes on the web useful for inspiration.
     * <a href="https://algs4.cs.princeton.edu/55compression/Huffman.java.html">algs4</a>
     */
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            char ch = entry.getKey();
            int freq = entry.getValue();
            if (freq > 0) {
                Node node = new Node(ch, freq, null, null);
                pq.insert(node);
            }

        }
    }

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    /**
     * longestPrefixMatch.
     * The longestPrefixMatch method finds the longest prefix that matches the given querySequence
     * and returns a Match object for that Match.
     */
    public Match longestPrefixMatch(BitSequence querySequence) {
        return null;
    }

    /**
     * buildLookupTable.
     * The buildLookupTable method returns the inverse of the coding trie.
     */
    public Map<Character, BitSequence> buildLookupTable() {
        return null;
    }
}
