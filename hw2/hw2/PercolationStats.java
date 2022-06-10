package hw2;

import java.util.Random;

public class PercolationStats {
    private int t;
    private int n;
    private int size;
    private double mean;
    private double dev;
    private double[] fracArr;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        t = T;
        n = N;
        size = n * n;

        mean = 0;
        dev = 0;

        fracArr = new double[size];

        Random r = new Random();

        for (int i = 0; i < t; i++) {
            int ptr;
            double count = 0;

            Percolation p = pf.make(n);

            while (!p.percolates()) {
                ptr = r.nextInt(size);
                p.open(ptr / n, ptr % n);
                count = count + 1;
            }

            fracArr[i] = count / size;
            mean += fracArr[i];
        }

        mean /= t;

        for (int i = 0; i < t; i++) {
            dev += Math.pow((fracArr[i] - mean), 2);
        }

        dev /= (t - 1);
        dev = Math.pow(dev, 0.5);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return dev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean - 1.96 * dev / Math.pow(t, 0.5);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean + 1.96 * dev / Math.pow(t, 0.5);
    }
}
