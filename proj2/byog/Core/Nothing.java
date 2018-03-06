package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class Nothing extends Tools implements Serializable {



    Nothing(Game game, TETile tile) {
        super(game, tile);
        this.name = "nothing";
    }

    void place() {
        return;
    }

    void use() {
        return;
    }
}
