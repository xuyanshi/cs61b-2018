package hw2;

import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private double[] fraction;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        validate(N, T);
        this.fraction = new double[T];
        for (int t = 0; t < T; t++) {
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(N);
                int y = StdRandom.uniform(N);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                }
            }
            this.fraction[t] = (double) percolation.numberOfOpenSites() / (N * N);
        }
    }   // perform T independent experiments on an N-by-N grid

    private void validate(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N " + N + "T " + T);
        }
    }

    public double mean() {
        return StdStats.mean(this.fraction);
    } // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(this.fraction);
    } // sample standard deviation of percolation threshold

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.fraction.length);
    } // low endpoint of 95% confidence interval

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.fraction.length);
    } // high endpoint of 95% confidence interval

//    public static void main(String[] args) {
//        PercolationFactory pf = new PercolationFactory();
//
//        Stopwatch timer1 = new Stopwatch();
//        PercolationStats speedTest1 = new PercolationStats(200, 100, pf);
//        double time1 = timer1.elapsedTime();
//        StdOut.printf("%.2f seconds\n", time1); // WQU 0.44s QF 22.68 seconds
//
//        Stopwatch timer2 = new Stopwatch();
//        PercolationStats speedTest2 = new PercolationStats(200, 200, pf);
//        double time2 = timer2.elapsedTime();
//        StdOut.printf("%.2f seconds\n", time2); // WQU 0.78s QF 43.56 seconds
//    }
}
