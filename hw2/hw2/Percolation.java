package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int size;
    private WeightedQuickUnionUF arr;
    private boolean[] openArr;
    private boolean[] fullArr;

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
        size = N * N;
        arr = new WeightedQuickUnionUF(size);
        openArr = new boolean[size];
        fullArr = new boolean[size];
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }

        int ptr = xyTo1D(row, col);
        openArr[ptr] = true;

        int left = ptr - 1;
        int right = ptr + 1;
        int up = ptr - n;
        int down = ptr + n;

        // avoid side
        if (row == 0) {
            up = -1;
        }
        if (row == n - 1) {
            down = -1;
        }
        if (col == 0) {
            left = -1;
        }
        if (col == n - 1) {
            right = -1;
        }

        // union the open cell
        if (up != -1 && openArr[up]) {
            arr.union(ptr, up);
        }
        if (down != -1 && openArr[down]) {
            arr.union(ptr, down);
        }
        if (left != -1 && openArr[left]) {
            arr.union(ptr, left);
        }
        if (right != -1 && openArr[right]) {
            arr.union(ptr, right);
        }

        // when hit the top, full the cells
        if (row == 0 || (up != -1 && fullArr[up]) || (down != -1 && fullArr[down])
                || (left != -1 && fullArr[left]) || (right != -1 && fullArr[right])) {
            fullArr[ptr] = true;
            fullOpen(row, col);
        }

    }

    private void fullOpen(int row, int col) {
        int ptr = xyTo1D(row, col);

        int up = ptr - n;
        int down = ptr + n;
        int left = ptr - 1;
        int right = ptr + 1;

        // avoid side
        if (row == 0) {
            up = -1;
        }
        if (row == n - 1) {
            down = -1;
        }
        if (col == 0) {
            left = -1;
        }
        if (col == n - 1) {
            right = -1;
        }

        // stop when hit full block
        if (up != -1 && fullArr[up]) {
            up = -1;
        }
        if (down != -1 && fullArr[down]) {
            down = -1;
        }
        if (left != -1 && fullArr[left]) {
            left = -1;
        }
        if (right != -1 && fullArr[right]) {
            right = -1;
        }

        // make the side cell full
        if (up != -1 && arr.connected(ptr, up)) {
            fullArr[up] = true;
        } else {
            up = -1;
        }
        if (down != -1 && arr.connected(ptr, down)) {
            fullArr[down] = true;
        } else {
            down = -1;
        }
        if (left != -1 && arr.connected(ptr, left)) {
            fullArr[left] = true;
        } else {
            left = -1;
        }
        if (right != -1 && arr.connected(ptr, right)) {
            fullArr[right] = true;
        } else {
            right = -1;
        }

        // recursive loop
        if (up != -1) {
            fullOpen(row - 1, col);
        }
        if (down != -1) {
            fullOpen(row + 1, col);
        }
        if (left != -1) {
            fullOpen(row, col - 1);
        }
        if (right != -1) {
            fullOpen(row, col + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }

        return openArr[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }

        return fullArr[xyTo1D(row, col)];
    }

    // number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean open : openArr) {
            if (open) {
                count++;
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = size - n; i < size; i++) {
            if (fullArr[i]) {
                return true;
            }
        }
        return false;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
