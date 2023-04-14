package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board implements WorldState {
    private static final int BLANK = 0;
    private final int n;

    private final int[][] board;

    /**
     * Board(tiles): Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, board[i], 0, n);
        }
    }

    private boolean illegal(int x) {
        return x < 0 || x >= n;
    }

    /**
     * tileAt(i, j): Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        if (illegal(i) || illegal(j)) {
            throw new IndexOutOfBoundsException("Corner cases");
        }
        return board[i][j];
    }

    /**
     * size(): Returns the board size N
     */
    public int size() {
        return n;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int N = size();
        int blankX = -1;
        int blankY = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == BLANK) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
        int[][] neighborTiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                neighborTiles[i][j] = tileAt(i, j);
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Math.abs(-blankX + i) + Math.abs(j - blankY) - 1 == 0) {
                    // swap blank tile and a near tile
                    neighborTiles[blankX][blankY] = neighborTiles[i][j];
                    neighborTiles[i][j] = BLANK;
                    Board neighbor = new Board(neighborTiles);
                    neighbors.enqueue(neighbor);
                    // then swap back for searching next neighbor
                    neighborTiles[i][j] = neighborTiles[blankX][blankY];
                    neighborTiles[blankX][blankY] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * hamming(): Hamming estimate described below
     */
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) {
                    break;
                }
                if (board[i][j] != i * n + j + 1) {
                    ++distance;
                }
            }
        }
        if (board[n - 1][n - 1] != BLANK) {
            ++distance;
        }
        return distance;
    }

    /**
     * manhattan(): Manhattan estimate described below
     */
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) {
                    break;
                }

            }
        }
        return distance;
    }

    /**
     * estimatedDistanceToGoal(): Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * equals(y): Returns true if this board's tile values are the same
     * position as y's
     * Skeleton:
     * public boolean equals(Object y) {
     * return false;
     * }
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Board board1 = (Board) o;

        if (n != board1.n) {
            return false;
        }
        return Arrays.deepEquals(board, board1.board);
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {

    }
}
