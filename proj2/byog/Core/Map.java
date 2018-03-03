package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class Map {
    TETile[][] LAYOUT;
    int ROOMCOUNT = 0;
    int MAXROOMS;
    int MINROOMS = 15;

    int SEED;
    Random R;
    static int HEIGHT;
    static int WIDTH;

    public Map(TETile[][] passed, int sd, int height, int width) {
        LAYOUT = passed;
        SEED = sd;
        HEIGHT = height;
        WIDTH = width;
    }

    /* makes the map using rooms and hallways, triggers double recursion*/
    public TETile[][] makeMap() {
        R = new Random(SEED);
        MAXROOMS = R.nextInt(10) + 20;
        Location startPoint = new Location(R.nextInt(WIDTH / 5) + 1, R.nextInt(HEIGHT / 5) + 1);

        Room root = new Room(startPoint, this);
        root.makeRoom();

        fillMap();

        return LAYOUT;
    }


    /*fills the empty spaces in the map with NOTHIN tiles*/
    private void fillMap() {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int k = 0; k < HEIGHT; k += 1) {
                if (LAYOUT[i][k] == null) {
                    LAYOUT[i][k] = Tileset.NOTHING;
                }
            }
        }
    }
}