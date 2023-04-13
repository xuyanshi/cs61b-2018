package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Solver {
    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    private MinPQ<SearchNode> minPQ = new MinPQ<>();
    private SearchNode finalNode = null;
    private boolean targetFound = false;
    private Map<WorldState, Integer> map = new HashMap<>();

    private class SearchNode implements Comparable<SearchNode> {
        WorldState worldState;
        int move;
        SearchNode prev;
        int priority;

        SearchNode(WorldState worldState, int move, SearchNode prev, int priority) {
            this.worldState = worldState;
            this.move = move;
            this.prev = prev;
            this.priority = priority;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    public Solver(WorldState initial) {
        SearchNode initialNode = new SearchNode(initial, 0, null, 0);
        minPQ.insert(initialNode);
        while (!targetFound) {
            SearchNode cur = minPQ.delMin();
            if (cur.worldState.isGoal()) {
                targetFound = true;
                finalNode = cur;
                break;
            }
            for (WorldState ws : cur.worldState.neighbors()) {
                if (cur.prev != null && ws.equals(cur.prev.worldState)) {
                    continue;
                }
                int movement = cur.move + 1;
                int estimated;
                if (map.containsKey(ws)) {
                    estimated = map.get(ws);
                } else {
                    estimated = ws.estimatedDistanceToGoal();
                    map.put(ws, estimated);
                }
                minPQ.insert(new SearchNode(ws, movement, cur, movement + estimated));
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return finalNode.move;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        List<WorldState> sol = new LinkedList<>();
        SearchNode node = finalNode;
        while (node != null) {
            sol.add(0, node.worldState);
            node = node.prev;
        }
        return sol;
    }
}
