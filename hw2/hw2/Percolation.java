package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author xuyanshi
 */
public class Percolation {

    private final int N, dummyTop;
    private final boolean[][] sites;
    private final WeightedQuickUnionUF disjointSet;
    private int openSites = 0;

    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /**
     * create N-by-N grid, with all sites initially blocked
     *
     * @param N N-by-N grid
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("The constructor errors if N ≤ 0.");
        }
        this.N = N;
        this.sites = new boolean[N][N];

        // N*N is dummy top, N*N+1 is dummy bottom.
        this.dummyTop = N * N;
        // this.dummyBottom = N * N + 1;
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
        if (!sites[row][col]) {
            sites[row][col] = true;
            openSites++;
        }

        if (row == 0) {
            disjointSet.union(dummyTop, indexOfJointSet(row, col));
        }

        // To avoid back washing
        //        if (row == N - 1) {
        //            disjointSet.union(bottom, indexOfJointSet(row, col));
        //        }

        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (legal(newRow, newCol) && isOpen(newRow, newCol)) {
                disjointSet.union(indexOfJointSet(newRow, newCol), indexOfJointSet(row, col));
            }
        }
    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        judgeLegal(legal(row, col));
        return sites[row][col];
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        judgeLegal(legal(row, col));
        int idx = indexOfJointSet(row, col);
        return isOpen(row, col) && disjointSet.connected(dummyTop, idx);
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
        // I fixed back washing by deleting the dummy bottom, but a new issue occurred.
        // All methods should take constant time, but it is not now.
        for (int j = 0; j < N; j++) {
            int bottom = indexOfJointSet(N - 1, j);
            if (disjointSet.connected(dummyTop, bottom)) {
                return true;
            }
        }
        // return disjointSet.connected(dummyTop, dummyBottom);
        return false;
    }

    /**
     * use for unit testing (not required)
     */
    public static void main(String[] args) {
        System.out.println("test");
    }
}
