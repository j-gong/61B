package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Map {

    private TETile[][] LAYOUT;
    private int ROOMCOUNT = 0;
    private int MAXROOMS;
    private int MINROOMS = 10;

    private int SEED;
    private Random R;

    private int HEIGHT;
    private int WIDTH;


    public Map(TETile[][] passed, int sd, int height, int width) {
        LAYOUT = passed;
        SEED = sd;
        HEIGHT = height;
        WIDTH = width;
    }

    public TETile[][] makeMap() {
        R = new Random(SEED);
        MAXROOMS = R.nextInt(10) + 10;

        Location startPoint = new Location(R.nextInt(WIDTH / 5) + 1, R.nextInt(HEIGHT / 5) + 1);
        Room root = new Room(startPoint, this);
        root.makeRoom();

        fillMap();
        return LAYOUT;
    }

    private void fillMap() {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int k = 0; k < HEIGHT; k += 1) {
                if (LAYOUT[i][k] == null) {
                    LAYOUT[i][k] = Tileset.NOTHING;
                }
            }
        }
    }

    public int getMax() {
        return MAXROOMS;
    }

    public int getMin() {
        return MINROOMS;
    }

    public int getRoomNum() {
        return ROOMCOUNT;
    }

    public TETile[][] getLAYOUT() {
        return LAYOUT;
    }

    public void incrementRoomCount(int x) {
        ROOMCOUNT += 1;
    }

    public Random getR() {
        return R;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }
}
