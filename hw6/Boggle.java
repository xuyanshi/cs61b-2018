import java.util.ArrayList;
import java.util.List;

public class Boggle {

    // File path of dictionary file
    // static String dictPath = "words.txt";
    static String dictPath = "trivial_words.txt";

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
        
    }
}
