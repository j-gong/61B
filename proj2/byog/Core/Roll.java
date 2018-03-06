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

        int rollX = game.robocop.last.x;
        int rollY = game.robocop.last.y;
        for (int i = 0; i < Game.WIDTH; i += 1) {
            game.robocop.move(game.WORLD, rollX, rollY);
        }
    }

}
