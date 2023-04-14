package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Solver {
    private static class Node /* <T> */ implements Comparable<Node> /* <T extends WorldState> */ {
        WorldState worldState;
        int moves = 0;
        Node prev = null;

        public Node(WorldState worldState, int moves, Node prev) {
            this.worldState = worldState;
            this.moves = moves;
            this.prev = prev;
        }


        @Override
        public int compareTo(Node o) {
            return (this.moves + this.worldState.estimatedDistanceToGoal()) -
                    (o.moves + o.worldState.estimatedDistanceToGoal());
        }
    }

    private MinPQ<Node> pq = new MinPQ<>();
    private HashSet<WorldState> hashSet;
    private final ArrayList<WorldState> solution;

    private Node finalNode = null;

    /**
     * Solver(initial):
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        pq.insert(new Node(initial, 0, null));
        System.out.printf("Enqueued WorldState: %s, number of total things: %d \n", pq.min().worldState, pq.size());
        hashSet = new HashSet<>();
        hashSet.add(pq.min().worldState);
        solution = new ArrayList<>();

        while (true) {
            if (pq.isEmpty()) {
                return;
            }

            Node node = pq.delMin();

            if (node.worldState.isGoal()) {
                finalNode = node;
                return;
            }


            for (WorldState neighbor : node.worldState.neighbors()) {
                if (!hashSet.contains(neighbor) /* && !neighborExisted */) {
                    Node childNode = new Node(neighbor, node.moves + 1, node);
                    pq.insert(childNode);
                    System.out.printf("Enqueued WorldState: %s, number of total things: %d \n", pq.min().worldState, pq.size());
                }
                hashSet.add(neighbor);
            }

        }
    }

    /**
     * moves():
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        if (finalNode == null) {
            return -1;
        }
        return finalNode.moves;
    }

    /**
     * solution():
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        Node node = finalNode;
        while (node != null) {
            solution.add(0, node.worldState);
            node = node.prev;
        }
        return solution;
    }

}
