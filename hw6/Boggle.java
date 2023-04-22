import java.util.ArrayList;
import java.util.List;

// https://sp18.datastructur.es/materials/hw/hw6/hw6
public class Boggle {

    // File path of dictionary file
    // static String dictPath = "words.txt";
    static String dictPath = "trivial_words.txt";

    /**
     * To accomplish this, you will need to create your own efficent Trie data structure.
     * You can write this new class in a new file, (e.g. Trie.java), or in Boggle.java.
     */
    private static class Trie {

    }

    /**
     * Solves a Boggle puzzle.
     *
     * @param k             The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     * The Strings are sorted in descending order of length.
     * If multiple words have the same length,
     * have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k <= 0) {
            throw new IllegalArgumentException("k should be positive");
        }
        ArrayList<String> words = new ArrayList<>();

        /*
            This method returns the k longest unique words sorted in descending order of length.
            If multiple words have the same length, print them in ascending alphabetical order.
         */


        words.sort((s1, s2) -> {
            if (s1.length() != s2.length()) {
                return s2.length() - s1.length();
            }
            return s1.compareTo(s2);
        });
        return words;
    }

    public static void main(String[] args) {
        System.out.println(solve(4, "exampleBoard2.txt"));
    }
}
