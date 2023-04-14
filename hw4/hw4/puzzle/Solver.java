package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver {
    MinPQ<WorldState> pq;
    private HashSet<WorldState> hashSet;
    private ArrayList<WorldState> solution;

    /**
     * Solver(initial):
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        pq.insert(initial);
        hashSet = new HashSet<>();
        solution = new ArrayList<>();
    }

    /**
     * moves():
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        if (solution == null || solution.size() == 0) {
            return -1;
        }
        return solution.size() - 1;
    }

    /**
     * solution():
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        return solution;
    }

}
