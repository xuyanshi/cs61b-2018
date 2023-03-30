package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author xuyanshi
 */
public class Percolation {

    private int N;

    /**
     * create N-by-N grid, with all sites initially blocked
     *
     * @param N N-by-N grid
     */
    public Percolation(int N) {
        this.N = N;
        if (N <= 0) {
            throw new IllegalArgumentException("The constructor should throw a java.lang.IllegalArgumentException if N ≤ 0.");
        }
    }

    private void legal(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("Indices are illegal.");
        }
    }

    /**
     * open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {

    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        return false;
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        return false;
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return 0;
    }

    /**
     * does the system percolate?
     *
     * @return percolates
     */
    public boolean percolates() {
        return false;
    }

    /**
     * use for unit testing (not required)
     */
    public static void main(String[] args) {
        System.out.println("main");
    }
}
