package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Nrgy extends Tools {

    String name = "energy";

    Nrgy(Game game) {
        super(game, Tileset.GRASS);
        used = false;

    }


    //TODO: place them somewhere
}
