import java.util.ArrayList;
import java.util.List;

public class HuffmanDecoder {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException();
        }
        String inputFileName = args[0];
        String outputFileName = args[1];
        ObjectReader reader = new ObjectReader(inputFileName);
        Object trie = reader.readObject();
        Object bits = reader.readObject();
        BinaryTrie binaryTrie = (BinaryTrie) trie;
        BitSequence bitSequence = (BitSequence) bits;
        List<Character> characterList = new ArrayList<>();
        while (bitSequence.length() != 0) {
            Match match = binaryTrie.longestPrefixMatch(bitSequence);
            characterList.add(match.getSymbol());
            bitSequence = bitSequence.allButFirstNBits(match.getSequence().length());
        }
        char[] chars = new char[characterList.size()];
        int length = chars.length;
        for (int i = 0; i < length; i++) {
            chars[i] = characterList.get(i);
        }
        FileUtils.writeCharArray(outputFileName, chars);
    }
}
