package hw4.puzzle;

public class Board {
    /**
     * Board(tiles): Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {

    }

    /**
     * tileAt(i, j): Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        return 0;
    }

    /**
     * size(): Returns the board size N
     */
    public int size() {
        return 0;
    }

    /**
     * neighbors():  Returns the neighbors of the current board
     */
    public Iterable<WorldState> neighbors() {
        return null;
    }

    /**
     * hamming(): Hamming estimate described below
     */
    public int hamming() {
        return 0;
    }


    /**
     * manhattan():  Manhattan estimate described below
     */
    public int manhattan() {
        return 0;
    }

    /**
     * estimatedDistanceToGoal(): Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return 0;
    }

    /**
     * equals(y):    Returns true if this board's tile values are the same
     * position as y's
     */
    public boolean equals(Object y) {
        return false;
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
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
