package hw2;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @author xuyanshi
 */
public class PercolationStats {
    private final double[] x;
    private final int T;

    /**
     * perform T independent experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N <= 0 or T <= 0");
        }
        this.T = T;
        this.x = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation per = pf.make(N);
            while (!per.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                per.open(row, col);
            }
            x[i] = per.numberOfOpenSites() * 1.0 / (N * N);
        }
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(x);
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(x);
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
