import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private int width, height;

    private final double[][] energies;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();

        this.energies = new double[width][height];
        for (int i = 0; i < width; i++) {
            Arrays.fill(energies[i], -1.0);
        }
    }

    // current picture
    public Picture picture() {
        return null;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }


    private double squareSum(double num1, double num2, double num3) {
        return num1 * num1 + num2 * num2 + num3 * num3;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (energies[x][y] >= 0) {
            return energies[x][y];
        }
        double deltaX2 = 0.0, deltaY2 = 0.0, ans;

        int xAddOne = (x + 1 < width) ? (x + 1) : 0;
        int xSubOne = (x - 1 >= 0) ? x - 1 : width - 1;
        int yAddOne = (y + 1 < height) ? y + 1 : 0;
        int ySubOne = (y - 1 >= 0) ? y - 1 : height - 1;

        Color xAddOneColor = picture.get(xAddOne, y);
        Color xSubOneColor = picture.get(xSubOne, y);
        Color yAddOneColor = picture.get(x, yAddOne);
        Color ySubOneColor = picture.get(x, ySubOne);

        deltaX2 = squareSum(xAddOneColor.getRed() - xSubOneColor.getRed(),
                xAddOneColor.getGreen() - xSubOneColor.getGreen(),
                xAddOneColor.getBlue() - xSubOneColor.getBlue());
        deltaY2 = squareSum(yAddOneColor.getRed() - ySubOneColor.getRed(),
                yAddOneColor.getGreen() - ySubOneColor.getGreen(),
                yAddOneColor.getBlue() - ySubOneColor.getBlue());
        ans = deltaX2 + deltaY2;
        energies[x][y] = ans;
        return ans;
    }

    private double minPrevCost(int x, int y, double[][] cost) {
        if (y < 1) {
            throw new IndexOutOfBoundsException("y should be greater than 0");
        }
        double minCost = cost[y - 1][x];
        if (x > 0) {
            minCost = Math.min(minCost, cost[y - 1][x - 1]);
        }
        if (x < width - 1) {
            minCost = Math.min(minCost, cost[y - 1][x + 1]);
        }
        return minCost;
    }

    private int minIndex(double[] rowCost, int start, int end) {
        if (start > end) {
            throw new IndexOutOfBoundsException("");
        }
        start = Math.max(start, 0);
        end = Math.min(end, rowCost.length - 1);
        int minIdx = start;
        for (int i = start + 1; i <= end; i++) {
            if (rowCost[i] < rowCost[minIdx]) {
                minIdx = i;
            }
        }
        return minIdx;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] cost = new double[height][width];
        for (int i = 0; i < width; i++) {
            cost[0][i] = energy(i, 0);
        }
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cost[i][j] = energy(j, i) + minPrevCost(j, i, cost);
            }
        }

        int[] verticalSeam = new int[height];
        for (int i = height - 1; i >= 0; i--) {
            if (i == height - 1) {
                verticalSeam[i] = minIndex(cost[i], 0, width - 1);
            } else {
                verticalSeam[i] = minIndex(cost[i], verticalSeam[i + 1] - 1,
                        verticalSeam[i + 1] + 1);
            }
        }
        return verticalSeam;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Picture temp = new Picture(height(), width());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                temp.set(i, j, picture.get(j, i));
            }
        }
        SeamCarver sc = new SeamCarver(temp);
        return sc.findVerticalSeam();
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
    }

}
