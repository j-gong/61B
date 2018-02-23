package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Map {

    public static TETile[][] LAYOUT;
    public static int ROOMCOUNT;
    public static int MAXROOMS;
    private static int SEED;
    public static Random R;

    public static Hallway[] OPENHALLS = new Hallway[25];
    public static int HALLCOUNTER = 0;

    public static int HEIGHT = Game.HEIGHT;
    public static int WIDTH = Game.WIDTH;

    /* TESTING PURPOSES ONLY*/
    public void Map(){
        R = new Random(5);
        LAYOUT = new TETile[10][10];
    }

    public static void makeMap(TETile[][] inputMap, int sd) {
        LAYOUT = inputMap;
        SEED = sd;

        R = new Random(SEED);
        MAXROOMS = R.nextInt(7) + 7;

        Location startPoint = new Location(Map.R.nextInt(Map.WIDTH) / 5 + 1, Map.R.nextInt(Map.HEIGHT) / 5 + 1);
        Room root = new Room(startPoint);
        root.makeRoom();

        fillMap();
    }

    private static void fillMap() {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int k = 0; k < HEIGHT; k += 1) {
                if (LAYOUT[i][k] == null) {
                    LAYOUT[i][k] = Tileset.NOTHING;
                }
            }
        }
    }

}
