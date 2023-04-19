import java.io.Serializable;
import java.util.Map;

/**
 * @author xuyanshi
 * @date 2023/4/20 03:55
 */
public class BinaryTrie implements Serializable {
    /**
     * Constructor.
     * Given a frequency table which maps symbols of type V to their relative frequencies,
     * the constructor should build a Huffman decoding trie according to the procedure discussed in class.
     * You may find implementations of Huffman codes on the web useful for inspiration.
     */
    public BinaryTrie(Map<Character, Integer> frequencyTable) {

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
