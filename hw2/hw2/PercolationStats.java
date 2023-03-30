package hw2;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double mean, stddev, confidenceLow, confidenceHigh;

    /**
     * perform T independent experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        for (int i = 0; i < T; i++) {
            Percolation per = pf.make(N);

        }
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return mean;
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return stddev;
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLow() {
        return confidenceLow;
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHigh() {
        return confidenceHigh;
    }
}
