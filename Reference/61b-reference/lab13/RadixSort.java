/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
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
        String[] sorted = new String[asciis.length];
        int maxLength = 0;
        for (int i = 0; i < asciis.length; i++) {
            if (asciis[i].length() > maxLength) {
                maxLength = asciis[i].length();
            }
            sorted[i] = asciis[i];
        }
        sortHelperLSD(sorted, maxLength);
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        if (index < 0) {
            return;
        }

        // gather all the counts for each value
        int[] counts = new int[257];
        for (String ascii : asciis) {
            if (index >= ascii.length()) {
                counts[0]++;
            } else {
                int i = (int) ascii.charAt(index) + 1;
                counts[i]++;
            }
        }

        // counting sort that uses start position calculation
        int[] starts = new int[257];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            int item;
            if (index >= asciis[i].length()) {
                item = 0;
            } else {
                item = (int) asciis[i].charAt(index) + 1;
            }
            int place = starts[item];
            sorted[place] = asciis[i];
            starts[item] += 1;
        }
        System.arraycopy(sorted, 0, asciis, 0, asciis.length);

        sortHelperLSD(asciis, index - 1);
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
}
