import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    } // current picture

    public int width() {
        return picture.width();
    } // width of current picture

    public int height() {
        return picture.height();
    } // height of current picture

    private double calcGradient(Color x, Color y) {
        return (x.getGreen() - y.getGreen()) * (x.getGreen() - y.getGreen())
                + (x.getBlue() - y.getBlue()) * (x.getBlue() - y.getBlue())
                + (x.getRed() - y.getRed()) * (x.getRed() - y.getRed());
    }

    private double deltaX2(int x, int y) {
        int right = Math.floorMod(x + 1, width());
        int left = Math.floorMod(x - 1, width());
        return calcGradient(picture.get(right, y), picture.get(left, y));
    }

    private double deltaY2(int x, int y) {
        int top = Math.floorMod(y + 1, height());
        int bottom = Math.floorMod(y - 1, height());
        return calcGradient(picture.get(x, top), picture.get(x, bottom));
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IndexOutOfBoundsException();
        }
        return deltaX2(x, y) + deltaY2(x, y);
    } // energy of pixel at column x and row y

    private int indexOfMin(double[] cost, int start, int end) {
        if (start > end) {
            throw new IndexOutOfBoundsException();
        }
        if (end >= cost.length) {
            end = cost.length - 1;
        }
        if (start < 0) {
            start = 0;
        }
        int idx = start;
        for (int i = start; i <= end; i++) {
            if (cost[idx] > cost[i]) {
                idx = i;
            }
        }
        return idx;
    }

    private double minPrevCost(int x, int y, double[][] cost) {
        if (y < 1) {
            throw new IndexOutOfBoundsException();
        }
        double min = cost[y - 1][x];
        if (x > 0) {
            min = Math.min(min, cost[y - 1][x - 1]);
        }
        if (x < width() - 1) {
            min = Math.min(min, cost[y - 1][x + 1]);
        }
        return min;
    }

    public int[] findVerticalSeam() {
        double[][] cost = new double[height()][width()];
        for (int j = 0; j < width(); j++) {
            cost[0][j] = energy(j, 0);
        }
        for (int i = 1; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                cost[i][j] = energy(j, i) + minPrevCost(j, i, cost);
            }
        }
        int[] indices = new int[height()];
        for (int i = height() - 1; i >= 0; i--) {
            if (i == height() - 1) {
                indices[i] = indexOfMin(cost[i], 0, width() - 1);
            } else {
                indices[i] = indexOfMin(cost[i], indices[i + 1] - 1, indices[i + 1] + 1);
            }
        }
        return indices;
    }  // sequence of indices for vertical seam

    public int[] findHorizontalSeam() {
        Picture temp = new Picture(height(), width());
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                temp.set(i, j, picture.get(j, i));
            }
        }
        SeamCarver sc = new SeamCarver(temp);
        return sc.findVerticalSeam();
    } // sequence of indices for horizontal seam

    private boolean isValidSeam(int[] seam) {
        int prev = seam[0];
        for (int i : seam) {
            if (prev - i > 1 || prev - i < -1) {
                return false;
            }
            prev = i;
        }
        return true;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width() && isValidSeam(seam)) {
            throw new IllegalArgumentException();
        }
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }   // remove horizontal seam from picture

    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height() && isValidSeam(seam)) {
            throw new IllegalArgumentException();
        }
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }     // remove vertical seam from picture

}
