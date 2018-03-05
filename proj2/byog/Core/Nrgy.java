package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Nrgy extends Tools {

    boolean used;
    //took this out of tools, might cause an error

    Nrgy(Game game) {
        super(game, Tileset.GRASS);
        used = false;
        name = "energy";

    }

}
