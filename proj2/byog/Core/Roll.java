package byog.Core;

import byog.TileEngine.Tileset;

public class Roll extends Tools{

    Roll(Game game) {
        super(game, Tileset.WATER);
        this.name = "roller blades";
        this.uses = 5;
        this.used = false;
    }

    void use() {

    }

}
