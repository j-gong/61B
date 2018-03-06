package byog.Core;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Weapon extends Tools implements Serializable {




    public Weapon(int x, int y, Game game) {
        super(game, Tileset.WATER);

    }
}
