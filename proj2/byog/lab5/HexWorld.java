package byog.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    public static void addHexagon(int sideLen) {
        for (int i = 0; i < sideLen; i++) {
            for (int j = 0; j < sideLen; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        addHexagon(2);
    }
}
