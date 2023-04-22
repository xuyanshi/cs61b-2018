/**
 * @author xuyanshi
 * @date 2023/4/22 17:44
 */
public class HuffmanDecoder {
    public static void main(String[] args) {
        // 1: Read the Huffman coding trie.
        String filename = args[0];
        ObjectReader or = new ObjectReader(filename);
        Object trieObject = or.readObject();
        BinaryTrie trie = (BinaryTrie) trieObject;
        // 2: If applicable, read the number of symbols.
        Object numberObject = or.readObject();
        int number = (Integer) numberObject;
        // 3: Read the massive bit sequence corresponding to the original txt.
        Object hugeBitSequenceObject = or.readObject();
        BitSequence bs = (BitSequence) hugeBitSequenceObject;
        // 4: Repeat until there are no more symbols:
        //    4a: Perform a longest prefix match on the massive sequence.
        //    4b: Record the symbol in some data structure.
        //    4c: Create a new bit sequence containing the remaining unmatched bits.
        char[] outputs = new char[number];
        int decodedChars = 0;
        while (decodedChars < number) {
            Match match = trie.longestPrefixMatch(bs);
            outputs[decodedChars] = match.getSymbol();
            bs = bs.allButFirstNBits(match.getSequence().length());
            ++decodedChars;
        }
        // 5: Write the symbols in some data structure to the specified file.
        String outputFilename = args[1];
        FileUtils.writeCharArray(outputFilename, outputs);
    }
}
