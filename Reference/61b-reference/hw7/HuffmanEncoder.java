import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : inputSymbols) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 0);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }
        String fileName = args[0];
        char[] inputSymbols = FileUtils.readFile(fileName);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie binaryTrie = new BinaryTrie(frequencyTable);
        ObjectWriter writer = new ObjectWriter(fileName + ".huf");
        writer.writeObject(binaryTrie);
        Map<Character, BitSequence> lookUpTable = binaryTrie.buildLookupTable();
        List<BitSequence> bitSequenceList = new ArrayList<>();
        for (char c : inputSymbols) {
            bitSequenceList.add(lookUpTable.get(c));
        }
        BitSequence bitSequence = BitSequence.assemble(bitSequenceList);
        writer.writeObject(bitSequence);
    }
}
