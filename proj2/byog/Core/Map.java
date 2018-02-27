package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Map {

    TETile[][] LAYOUT;
    int ROOMCOUNT = 0;
    int MAXROOMS;
    int MINROOMS = 10;

    int SEED;
    Random R;

    int HEIGHT = Game.HEIGHT;
    int WIDTH = Game.WIDTH;


    public TETile[][] makeMap(TETile[][] inputMap, int sd) {
        LAYOUT = inputMap;
        SEED = sd;

        R = new Random(SEED);
        MAXROOMS = R.nextInt(10) + 10;

        Location startPoint = new Location(R.nextInt(WIDTH / 5) + 1,
                R.nextInt(HEIGHT / 5) + 1);
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

}
