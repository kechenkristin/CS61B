package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;

public class Board implements WorldState {

    private int N;
    private int[][] tile;
    private int[][] goal;
    private final int BLANK = 0;

    private void initialzeGoal() {
        int num = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != N - 1 || j != N - 1) {
                    goal[i][j] = num;
                    num += 1;
                } else {
                    goal[i][j] = BLANK;
                }
            }
        }
    }

    private void initializeTile(int[][] t) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tile[i][j] = t[i][j];
            }
        }
    }

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j.
     */
    public Board(int[][] tiles) {
        N = tiles[0].length;
        this.tile = new int[N][N];
        initializeTile(tiles);
        goal = new int[N][N];
        initialzeGoal();
    }

    private boolean inValidIJ(int i, int j) {
        return i < 0 || i >= N || j < 0 || j >= N;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank).
     */
    public int tileAt(int i, int j) {
        if (inValidIJ(i, j)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return tile[i][j];
    }

    /**
     * return the board size N.
     */
    public int size() {
        return N;
    }

    /**
     * return the neighbors of the current board.
     *
     * @source <a href="http://joshh.ug/neighbors.html">...</a>
     */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * The number of tiles in the wrong position.
     */
    public int hamming() {
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != BLANK) {
                    if (tileAt(i, j) != goal[i][j]) {
                        result += 1;
                    }
                }
            }
        }
        return result;
    }

    /**
     * The sum of the Manhattan distances
     * (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.
     */
    public int manhattan() {
        /* HashMap<Integer, Integer> tilePosition = new HashMap<>();
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == BLANK) {
                    continue;
                }
                if (tileAt(i, j) != goal[i][j]) {
                    tilePosition.put(tileAt(i, j), i + j);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tilePosition.containsKey(goal[i][j])) {
                    result += Math.abs((i + j) - tilePosition.get(goal[i][j]));
                }
            }
        }
        return result;*/
        int dist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tile[i][j] == 0) {
                    continue;
                } else {
                    int targeti = (tile[i][j] - 1) / N;
                    int targetj = (tile[i][j] - 1) % N;
                    dist += Math.abs(targeti - i);
                    dist += Math.abs(targetj - j);
                }
            }
        }
        return dist;
    }

    /**
     * Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's.
     */
    @Override
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (!(y instanceof Board)) {
            return false;
        }
        if (y == this) {
            return true;
        }
        Board yb = (Board) y;
        if (yb.size() != this.size()) {
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (yb.tileAt(i, j) != this.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result = result * 31 + tileAt(i, j);
            }
        }
        return result;
    }

}
