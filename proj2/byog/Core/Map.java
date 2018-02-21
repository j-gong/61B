package byog.Core;
import byog.TileEngine.TETile;
import java.util.Random;

public class Map {

    public static TETile[][] LAYOUT;
    public static int ROOMCOUNT;
    public static int MAXROOMS;
    private static int SEED;
    public static Random R;

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
        MAXROOMS = R.nextInt(7) + 10;

        /* TESTING PURPOSES ONLY */ R = new Random(5);

        Location startPoint = new Location(Map.R.nextInt(Map.HEIGHT) / 6, Map.R.nextInt(Map.WIDTH));
        Room root = new Room(startPoint);
        root.makeRoom();
    }

}
