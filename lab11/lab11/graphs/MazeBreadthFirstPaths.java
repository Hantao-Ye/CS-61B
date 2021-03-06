package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new Queue<>();
        for (int v = 0; v < maze.V(); v++) {
            distTo[v] = Integer.MAX_VALUE;
        }

        distTo[s] = 0;
        marked[s] = true;
        announce();
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();

            if (v == t) {
                targetFound = true;
                return;
            }

            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    announce();
                    if (w == t) {
                        targetFound = true;
                    }

                    q.enqueue(w);
                }

                if (targetFound) {
                    return;
                }
            }
        }


    }


    @Override
    public void solve() {
         bfs();
    }
}

