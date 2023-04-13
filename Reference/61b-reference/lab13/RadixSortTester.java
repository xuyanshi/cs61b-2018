import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RadixSortTester {

    private static String[] someASCIIs = {"bad", "apple", "ce", "eg", "gg", "for", "ab"};

    public static void assertIsSorted(String[] a) {
        String previous = a[0];
        for (String x : a) {
            assertTrue(x.compareTo(previous) >= 0);
            previous = x;
        }
    }

    @Test
    public void testBetterWithSomeNegative() {
        String[] sortedSomeASCIIs = RadixSort.sort(someASCIIs);
        for (String i : sortedSomeASCIIs) {
            System.out.println(i);
        }

        assertIsSorted(sortedSomeASCIIs);
    }

}
