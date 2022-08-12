package lab11.graphs;


/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(v);
        int targetY = maze.toY(v);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private int cost(int v) {
        return distTo[v] + h(v);
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        ArrayHeap<Integer> pq = new ArrayHeap<>();
        pq.insert(s, 0);
        for (int i = s; i < maze.V(); i ++) {
        }

        while (!pq.isEmpty()) {
            int p = pq.removeMin();
            for (int q : maze.adj(p)) {
                if (cost(p) < cost(q)) {
                    distTo[q] = cost(p);
                    edgeTo[q] = p;
                    pq.changePriority(q, distTo[q]);
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

