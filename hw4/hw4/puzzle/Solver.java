package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Solver {
    private class Node /* <T> */ implements Comparable /* <T extends WorldState> */ {
        WorldState worldState;
        int moves = 0;
        Node prev = null;

        public Node(WorldState worldState, int moves, Node prev) {
            this.worldState = worldState;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(Object o) {
            Node that = (Node) o;
            if ((this.moves + this.worldState.estimatedDistanceToGoal()) <
                    (that.moves + that.worldState.estimatedDistanceToGoal())) {
                return 1;
            }
            return -1;
        }

    }

    MinPQ<Node> pq;
    private HashSet<WorldState> hashSet;
    private final ArrayList<WorldState> solution;

    /**
     * Solver(initial):
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        pq.insert(new Node(initial, 0, null));
        hashSet = new HashSet<>();
        solution = new ArrayList<>();

        while (true) {
            if (pq.isEmpty()) {
                return;
            }

            Node node = pq.delMin();

            if (node.worldState.isGoal()) {
                // solution
                solution.add(node.worldState);
                while (node.prev != null) {
                    node = node.prev;
                    solution.add(node.worldState);
                }
                Collections.reverse(solution);
            }

            hashSet.add(node.worldState);

            for (WorldState neighbor : node.worldState.neighbors()) {
                boolean neighborExisted = false;
                for (Node n : pq) {
                    if (n.worldState.equals(neighbor)) {
                        neighborExisted = true;
                        break;
                    }
                }
                if (!hashSet.contains(neighbor) && !neighborExisted) {
                    Node childNode = new Node(neighbor, node.moves + 1, node);
                    pq.insert(childNode);
                }
            }

        }
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
