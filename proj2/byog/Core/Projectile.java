package byog.Core;

import byog.TileEngine.Tileset;

public class Projectile extends Tools {

    String name = "sock launcher";

    public Projectile(int x, int y, Game game) {
        super(game, Tileset.WATER);

    }
}
