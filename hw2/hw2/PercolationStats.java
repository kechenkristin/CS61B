package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int T;
    private double[] threshold;

    // perform T independent experiments on an N by N grid.
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        threshold = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            int count = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    count++;
                }
            }
            threshold[i] = (double) count / N * N;
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLow() {
        return (mean() - 1.96 * stddev() / Math.sqrt(T));
    }

     public double confidenceHigh() {
        return (mean() + 1.96 * stddev() / Math.sqrt(T));
    }

}
