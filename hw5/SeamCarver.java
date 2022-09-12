import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    private Color getColor(int row, int col) {
        return picture.get(row, col);
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // cal 2x
        int rx = getColor(x + 1, y).getRed() - getColor(x - 1, y).getRed();
        int gx = getColor(x + 1, y).getGreen() - getColor(x - 1, y).getGreen();
        int bx = getColor(x + 1, y).getBlue() - getColor(x - 1, y).getBlue();
        int sumx = rx * rx + gx * gx + bx * bx;

        // cal 2y
        int ry = getColor(x, y + 1).getRed() - getColor(x, y - 1).getRed();
        int gy = getColor(x, y + 1).getGreen() - getColor(x, y - 1).getGreen();
        int by = getColor(x, y + 1).getBlue() - getColor(x, y - 1).getBlue();
        int sumy = ry * ry + gy * gy + by * by;
        return sumy + sumx;
    }

    public int[] findHorizontalSeam()            // sequence of indices for horizontal seam

    public int[] findVerticalSeam()              // sequence of indices for vertical seam

    public void removeHorizontalSeam(int[] seam)   // remove horizontal seam from picture

    public void removeVerticalSeam(int[] seam)
}
