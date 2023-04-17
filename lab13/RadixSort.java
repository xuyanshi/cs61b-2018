import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    private static final int ASCII_R = 256;
    private static int max_length = 0;

    // private static final char placeholder = '_';
    private static final int PLACEHOLDER = (int) '_';

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        String[] arr = new String[asciis.length];
        System.arraycopy(asciis, 0, arr, 0, asciis.length);
        max_length = 0;
        for (String str : arr) {
            if (str.length() > max_length) {
                max_length = str.length();
            }
        }
        for (int d = 0; d < max_length; d++) {
            sortHelperLSD(arr, d);
        }
        return arr;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        // String[][] buckets = new String[ASCII_R][asciis.length];
        ArrayList<ArrayList<String>> buckets = new ArrayList<>(ASCII_R);
        for (int i = 0; i < ASCII_R; i++) {
            buckets.add(new ArrayList<>(asciis.length));
        }
        for (String str : asciis) {
            if (str.length() + index < max_length) {
                buckets.get(PLACEHOLDER).add(str);
            } else {
                char ascii = str.charAt(max_length - index - 1);
                buckets.get((int) ascii).add(str);
            }
        }
        int k = 0;
        for (int i = 0; i < ASCII_R; i++) {
            ArrayList<String> bucket = buckets.get(i);
            if (bucket.isEmpty()) {
                continue;
            }
            for (String str : bucket) {
                asciis[k] = str;
                ++k;
            }
        }

        /*
        String[] new_arr = new String[asciis.length];
        for (int i = 0; i < ASCII_R; i++) {
            ArrayList<String> bucket = buckets.get(i);
            if (bucket.isEmpty()) {
                continue;
            }
            for (String str : bucket) {
                new_arr[k] = str;
                ++k;
            }
        }
        asciis = new_arr;

         */
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        // String[] arr = new String[]{"nba", "vldb", "algo", "ucb", "acm"};
        String[] arr = new String[]{"nba", "vld", "alg", "ucb", "acm"};
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(sort(arr)));
    }
}
