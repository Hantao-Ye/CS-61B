package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int openCount;
    private boolean[][] openArr;
    private WeightedQuickUnionUF fullArr;
    private WeightedQuickUnionUF percArr;

    /**
     * Calculate the index of array in [r, c]
     *
     * @param r the row number
     * @param c the column number
     * @return the index in array
     */
    private int xyTo1D(int r, int c) {
        return n * r + c;
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        n = N;
        openCount = 0;
        // assume fullArr[N * N] as the virtual head
        fullArr = new WeightedQuickUnionUF(N * N + 1);
        // assume percArr[N * N] as the virtual head, [N * N + 1] as tail
        percArr = new WeightedQuickUnionUF(N * N + 2);

        openArr = new boolean[N][N];
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        if (!openArr[row][col]) {
            openArr[row][col] = true;
            openCount++;

            int ptr = xyTo1D(row, col);
            if (row == 0) {
                fullArr.union(ptr, n * n);
                percArr.union(ptr, n * n);
            }
            if (row == n - 1) {
                percArr.union(ptr, n * n + 1);
            }

            union(row, col);
        }
    }

    private void union(int row, int col) {
        int ptr = xyTo1D(row, col);

        if (row - 1 >= 0 && openArr[row - 1][col]) {
            fullArr.union(xyTo1D(row - 1, col), ptr);
            percArr.union(xyTo1D(row - 1, col), ptr);
        }
        if (row + 1 < n && openArr[row + 1][col]) {
            fullArr.union(xyTo1D(row + 1, col), ptr);
            percArr.union(xyTo1D(row + 1, col), ptr);
        }
        if (col - 1 >= 0 && openArr[row][col - 1]) {
            fullArr.union(xyTo1D(row, col - 1), ptr);
            percArr.union(xyTo1D(row, col - 1), ptr);
        }
        if (col + 1 < n && openArr[row][col + 1]) {
            fullArr.union(xyTo1D(row, col + 1), ptr);
            percArr.union(xyTo1D(row, col + 1), ptr);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }

        return openArr[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }

        return fullArr.connected(n * n, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return percArr.connected(n * n, n * n + 1);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
