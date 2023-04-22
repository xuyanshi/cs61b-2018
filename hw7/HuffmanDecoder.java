/**
 * @author xuyanshi
 * @date 2023/4/22 17:44
 */
public class HuffmanDecoder {
    public static void main(String[] args) {
        // 1: Read the Huffman coding trie.
        String filename = args[0];
        ObjectReader or = new ObjectReader(filename);
        Object trie = or.readObject();
        // 2: If applicable, read the number of symbols.
        Object number = or.readObject();
        // 3: Read the massive bit sequence corresponding to the original txt.
        Object hugeBitSequence = or.readObject();
        // 4: Repeat until there are no more symbols:
        //    4a: Perform a longest prefix match on the massive sequence.
        //    4b: Record the symbol in some data structure.
        //    4c: Create a new bit sequence containing the remaining unmatched bits.

        // 5: Write the symbols in some data structure to the specified file.

    }
}
