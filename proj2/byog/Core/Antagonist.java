package byog.Core;

import byog.TileEngine.Tileset;

import java.util.Random;

public class Antagonist extends Character {

    Antagonist(Game game) {
        super(10, 10, game, Tileset.MOUNTAIN);

    }

    void randomMove() {

        Random r = new Random(5);

        Location track = new Location(this.x, this.y);
        Location start = track.copy();
        while (track == start) {

            this.move(game.WORLD, r.nextInt(2), r.nextInt(2));

        }

    }


}
