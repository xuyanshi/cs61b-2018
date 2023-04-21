import edu.princeton.cs.algs4.Picture;

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

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (energies[x][y] >= 0) {
            return energies[x][y];
        }
        double deltaX2 = 0.0, deltaY2 = 0.0, ans;
        ans = deltaX2 + deltaY2;
        energies[x][y] = ans;
        return ans;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
    }

    public static void main(String[] args) {

    }
}
