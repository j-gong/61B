package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class Nothing extends Tools implements Serializable {

    String name = "nothing";

    Nothing(Game game, TETile tile) {
        super(game, tile);
    }

    void place() {
        return;
    }

    void use() {
        return;
    }
}
