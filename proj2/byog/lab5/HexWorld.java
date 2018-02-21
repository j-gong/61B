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

    public void addHexagon(TETile[][] world, int xcoord, int ycoord, int s, TETile t) {
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int rowY = ycoord + yi;

            int startX = xcoord + rowOffset(s, yi);
        }
    }

    private int rowOffset(int s, int i) {
        int result = i;
        if (i >= s) {
            result = 2 * s - 1 - result;
        }
        return result;
    }

    private int rowWidth(int s, int i) {
        int result = i;
        if (i >= s) {
            result = s + 2 * result;
        }
        return result;
    }

}
