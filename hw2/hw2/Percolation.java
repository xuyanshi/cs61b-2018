package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author xuyanshi
 */
public class Percolation {

    private final int N;
    //    private boolean[][] grid;
    private WeightedQuickUnionUF disjointSet;
    private int openSites = 0;

    private static final int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /**
     * create N-by-N grid, with all sites initially blocked
     *
     * @param N N-by-N grid
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("The constructor should throw a java.lang.IllegalArgumentException if N ≤ 0.");
        }
        this.N = N;
//        this.grid = new boolean[N][N];
        this.disjointSet = new WeightedQuickUnionUF(N * N + 2);
    }

    private boolean legal(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    private void judgeLegal(boolean isLegal) {
        if (!isLegal) {
            throw new IndexOutOfBoundsException("Indices are illegal.");
        }
    }

    /**
     * open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        judgeLegal(legal(row, col));
    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        judgeLegal(legal(row, col));
        return false;
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        judgeLegal(legal(row, col));
        return false;
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
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
        System.out.println("test");
        System.out.println(Arrays.deepToString(directions));
    }
}
