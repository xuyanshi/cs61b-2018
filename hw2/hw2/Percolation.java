package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

/**
 * @author xuyanshi
 */
public class Percolation {

    private final int N;
    private boolean[][] site;
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
        this.site = new boolean[N][N];
        // N*N is dummy top, N*N+1 is dummy bottom.
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

    private int indexOfJointSet(int row, int col) {
        return row * N + col;
    }

    /**
     * open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        judgeLegal(legal(row, col));
        if (!site[row][col]) {
            site[row][col] = true;
            openSites++;
        }

    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        judgeLegal(legal(row, col));
        return site[row][col];
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
        return disjointSet.connected(N * N, N * N + 1);
    }

    /**
     * use for unit testing (not required)
     */
    public static void main(String[] args) {
        System.out.println("test");
        System.out.println(Arrays.deepToString(directions));
    }
}
