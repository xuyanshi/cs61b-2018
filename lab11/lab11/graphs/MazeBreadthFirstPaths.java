package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int source;
    private int target;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        // Changed from MazeDepthFirstPaths.java
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    /**
     * Conducts a breadth first search of the maze starting at the source.
     */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> qu = new LinkedList<>();
        qu.offer(source);
        int distance = 0;
        while (!qu.isEmpty() && !targetFound) {
            int len = qu.size();
            ++distance;
            for (int i = 0; i < len; i++) {
                int vertex = qu.poll();
                marked[vertex] = true;
                announce();
                if (vertex == target) {
                    targetFound = true;
                    break;
                }
                for (int w : maze.adj(vertex)) {
                    distTo[w] = distance;
                }
            }

        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

