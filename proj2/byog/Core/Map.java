package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Map {

    public /*static*/ TETile[][] LAYOUT;
    public /*static*/ int ROOMCOUNT = 0;
    public /*static*/ int MAXROOMS;
    public /*static*/ int MINROOMS = 10;

    private int SEED;
    public /*static*/ Random R;

    public /*static*/ int HEIGHT = Game.HEIGHT;
    public /*static*/ int WIDTH = Game.WIDTH;

    /* TESTING PURPOSES ONLY*/
    public void Map(){
        R = new Random(5);
        LAYOUT = new TETile[10][10];
    }

    public TETile[][] makeMap(TETile[][] inputMap, int sd) {
        LAYOUT = inputMap;
        SEED = sd;

        R = new Random(SEED);
        MAXROOMS = R.nextInt(10) + 10;

        Location startPoint = new Location(R.nextInt(/*Map.*/WIDTH / 5) + 1, R.nextInt(/*Map.*/HEIGHT / 5) + 1);
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