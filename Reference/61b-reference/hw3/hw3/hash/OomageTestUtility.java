package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /**
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] number = new int[M];
        for (int i = 0; i < M; i++) {
            number[i] = 0;
        }
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            number[bucketNum]++;
        }
        double lo = (double) oomages.size() / 50;
        double hi = oomages.size() / 2.5;
        for (int i = 0; i < M; i++) {
            if (number[i] < lo || number[i] > hi) {
                return false;
            }
        }
        return true;
    }
}
