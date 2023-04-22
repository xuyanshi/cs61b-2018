import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyanshi
 * @date 2023/4/22 17:43
 */
public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (char input : inputSymbols) {
            frequencyTable.put(input, frequencyTable.getOrDefault(input, 0) + 1);
        }
        return frequencyTable;
    }

    public static void main(String[] args) {
        // 1: Read the file as 8 bit symbols.
        String filename = args[0];
        char[] inputs = FileUtils.readFile(filename);
        // 2: Build frequency table.
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputs);
        // 3: Use frequency table to construct a binary decoding trie.
        BinaryTrie bt = new BinaryTrie(frequencyTable);
        // 4: Write the binary decoding trie to the .huf file.
        String hufFile = filename + ".huf";
        ObjectWriter ow = new ObjectWriter(hufFile);
        ow.writeObject(bt);
        // 5: (optional: write the number of symbols to the .huf file)

        // 6: Use binary trie to create lookup table for encoding.

        // 7: Create a list of bitsequences.

        // 8: For each 8 bit symbol:
        //    Lookup that symbol in the lookup table.
        //    Add the appropriate bit sequence to the list of bitsequences.

        // 9: Assemble all bit sequences into one huge bit sequence.

        // 10: Write the huge bit sequence to the .huf file.

    }
}
