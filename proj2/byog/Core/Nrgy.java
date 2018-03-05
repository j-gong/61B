package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


import java.io.Serializable;

public class Nrgy extends Tools implements Serializable {





    Nrgy(Game game) {
        super(game, Tileset.GRASS);
        used = false;
        name = "energy";

    }


    //TODO: place them somewhere
}
