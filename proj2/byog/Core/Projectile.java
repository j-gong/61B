package byog.Core;

import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Projectile extends Tools implements Serializable {

    Projectile(Game game) {
        super(game, Tileset.SAND);
        this.name = "sock launcher";
        this.uses = 8;
        this.used = false;
    }

    void use() {
        int flyX = game.robocop.last.x;
        int flyY = game.robocop.last.y;
        Location fly = new Location(game.robocop.x + flyX, game.robocop.y + flyY);
        while (game.WORLD[fly.xPos][fly.yPos].description().equals("floor")) {
            fly.xPos += flyX;
            fly.yPos += flyY;
        }
        if (game.WORLD[fly.xPos][fly.yPos].description().equals("mountain")) {
            for (Antagonist bad : game.criminals) {
                if (bad.x == fly.xPos && bad.y == fly.yPos) {
                    game.robocop.apprehend(bad);
                }
            }
        }
        super.use();
    }


}
