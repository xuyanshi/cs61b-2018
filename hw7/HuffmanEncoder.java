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
        String filename = args[0];
        char[] inputs = new char[1000];
        BinaryTrie bt = new BinaryTrie(buildFrequencyTable(inputs));
    }
}
