package byog.Core;

import byog.TileEngine.TETile;

public class Nothing extends Tools{

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
