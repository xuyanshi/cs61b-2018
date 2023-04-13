package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] tiles;

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        this.tiles = new int[tiles[0].length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, tiles.length);
        }
    }

    private boolean validate(int index) {
        return index >= 0 && index < size();
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        if (validate(i) && validate(j)) {
            return this.tiles[i][j];
        } else {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    /**
     * Returns the board size N
     */
    public int size() {
        return this.tiles.length;
    }

    /**
     * Returns neighbors of this board.
     *
     * @source http://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int size = size();
        int blankX = -1;
        int blankY = -1;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (tileAt(x, y) == 0) {
                    blankX = x;
                    blankY = y;
                }
            }
        }
        int[][] tile = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                tile[x][y] = tileAt(x, y);
            }
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (Math.abs(-blankX + x) + Math.abs(y - blankY) - 1 == 0) {
                    tile[blankX][blankY] = tile[x][y];
                    tile[x][y] = 0;
                    Board neighbor = new Board(tile);
                    neighbors.enqueue(neighbor);
                    tile[x][y] = tile[blankX][blankY];
                    tile[blankX][blankY] = 0;
                }
            }
        }
        return neighbors;
    }

    /**
     * Hamming estimate
     */
    public int hamming() {
        int size = size();
        int dist = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (tileAt(x, y) != correctNumberAt(x, y) && tileAt(x, y) != 0) {
                    dist++;
                }
            }
        }
        return dist;
    }

    private int correctNumberAt(int x, int y) {
        if (validate(x) && validate(y)) {
            int result = x * size() + y + 1;
            if (result == size() * size()) {
                result = 0;
            }
            return result;
        } else {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }


    /**
     * Manhattan estimate
     */
    public int manhattan() {
        int size = size();
        int dist = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int number = tileAt(x, y);
                if (number != 0) {
                    dist += Math.abs(correctX(number) - x) + Math.abs(correctY(number) - y);
                }
            }
        }
        return dist;
    }

    private int correctX(int number) {
        if (number == 0) {
            return size() - 1;
        }
        return (number - 1) / size();
    }

    private int correctY(int number) {
        if (number == 0) {
            return size() - 1;
        }
        return (number - 1) % size();
    }

    /**
     * Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's
     */
    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board that = ((Board) y);
        if (size() != that.size()) {
            return false;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) != that.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int size = size();
        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result = result * 11 + tileAt(i, j);
            }
        }
        return result;
    }

    /**
     * Returns the string representation of the board.
     */
    @Override
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

}
