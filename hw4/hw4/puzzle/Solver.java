package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.HashSet;

public class Solver {
    /**
     * Solver(initial):
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        MinPQ<WorldState> pq = new MinPQ<>();
        pq.insert(initial);
        HashSet<WorldState> hashSet = new HashSet<>();

    }

    /**
     * moves():
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return 0;
    }

    /**
     * solution():
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        return null;
    }

}
