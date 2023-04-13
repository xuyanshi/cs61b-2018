package lab11.graphs;

import java.util.HashMap;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int duplicate = -1;
    private boolean cycleFound = false;
    private Maze maze;
    private HashMap<Integer, Integer> map = new HashMap<>();

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        distTo[0] = 0;
        dfs(0);
    }

    private void dfs(int v) {

        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {
            if (map.containsKey(v) && map.get(v) == w) {
                continue;
            }
            map.put(w, v);
            if (!marked[w]) {
                distTo[w] = distTo[v] + 1;  // update distance
                dfs(w);
            } else {
                cycleFound = true;
                duplicate = w;
            }

            if (cycleFound) {
                if (distTo[v] >= distTo[duplicate]) {
                    edgeTo[w] = v;
                }
                announce();
                return;
            }

        }

    }
}

