package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.stream.IntStream;

public class PercolationStats {
    private int t;
    private int n;
    private int size;
    private double mean;
    private double stddev;
    private double[] fracArr;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        t = T;
        n = N;
        size = n * n;

        mean = 0;
        stddev = 0;

        fracArr = new double[size];

        for (int i = 0; i < t; i++) {
            int ptr;
            double count = 0;

            Percolation p = pf.make(n);

            int[] shuffleArr = IntStream.range(0, size).toArray();
            StdRandom.shuffle(shuffleArr);

            while (!p.percolates()) {
                ptr = shuffleArr[(int) count];
                p.open(ptr / n, ptr % n);
                count = count + 1;
            }

            fracArr[i] = count / size;
        }

        mean = StdStats.mean(fracArr);
        stddev = StdStats.stddev(fracArr);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean - 1.96 * stddev / Math.pow(t, 0.5);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean + 1.96 * stddev / Math.pow(t, 0.5);
    }
}
