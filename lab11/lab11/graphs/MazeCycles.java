package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int[] edgeToCopy;
    private boolean foundCycle = false;



    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        edgeToCopy = new int[maze.V()];
        int v = maze.xyTo1D(1, 1);
        Stack<Integer> fringe = new Stack<>();
        fringe.push(v);

        while (!fringe.isEmpty()) {
            if (foundCycle) {
                break;
            }

            v = fringe.pop();
            marked[v] = true;
            announce();

            for (int w: maze.adj(v)) {
                if (!marked[w]) {
                    edgeToCopy[w] = v;
                    fringe.push(w);
                }

                if (marked[w] && edgeToCopy[v] != w) {
                    edgeTo[w] = v;
                    drawCycles(w);
                    foundCycle = true;
                    break;
                }
            }
        }
    }

    // Helper methods go here
    private void drawCycles(int start) {
        int pointer = start;

        while (true) {
            edgeTo[pointer] = edgeToCopy[pointer];
            pointer = edgeToCopy[pointer];
            if (pointer == start) {
                break;
            }
        }
        announce();
    }
}

