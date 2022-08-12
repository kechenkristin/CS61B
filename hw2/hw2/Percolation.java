package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;

public class Percolation {

    // fields
    private WeightedQuickUnionUF grid;
    private int opens;
    private int N;
    private boolean[] openSet;

    /**
     * create N-by-N grid, with all sites initially blocked.
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        grid = new WeightedQuickUnionUF(N * N);
        opens = 0;
        openSet = new boolean[N * N];
    }

    private int xyTo1D(int x, int y) {
        return x * N + y;
    }

    /**
     * return true if x or y are invalid arguments.
     */
    private boolean inValidArguments(int x, int y) {
        return x < 0 || x >= N || y < 0 || y >= N;
    }

    private void joinAround(int row, int col) {
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            grid.connected(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (row + 1 < N && isOpen(row + 1, col)) {
            grid.connected(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            grid.connected(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (col + 1 < N && isOpen(row, col + 1)) {
            grid.connected(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
    }


    /* open the site (row, col) if it is not open. */
    public void open(int row, int col) {
        if (inValidArguments(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            int site = xyTo1D(row, col);
            openSet[site] = true;
        }
    }

    /**
     * is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        if (inValidArguments(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return openSet[xyTo1D(row, col)];
    }

    /**
     * is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if (inValidArguments(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return true;
    }

    /**
     * number of open sites.
     */
    public int numberOfOpenSites() {
        return opens;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates()


    /**
     * use for unit testing (not required)
     */
    public static void main(String[] args)


}
