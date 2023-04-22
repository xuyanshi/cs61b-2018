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
        
    }
}
