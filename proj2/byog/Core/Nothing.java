package byog.Core;

import byog.TileEngine.TETile;

public class Nothing extends Tools{



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
