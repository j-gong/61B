package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.Random;

public class Tools implements Serializable {

    int x;
    int y;
    Game game;
    String name;
    TETile tile;
    int uses;
    boolean used;

    public Tools(Game game, TETile t) {
        this.game = game;
        x = game.r.nextInt(game.WIDTH);
        y = game.r.nextInt(game.HEIGHT);
        tile = t;
        this.place();

    }

    void place() {
        Random r = new Random(game.seed);
        boolean found = false;
        int tries = 15;
        while (!found) {
            if (game.WORLD[x][y].description().equals("floor") && in_room(tries)) {
                found = true;
            } else {
                x = r.nextInt(game.WIDTH);
                y = r.nextInt(game.HEIGHT);
                tries -= 1;
            }
        }
        game.WORLD[x][y] = tile;
    }

    private boolean in_room(int tries) {
        if (tries > 0) {
            return ((game.WORLD[x + 1][y].description().equals("floor") || game.WORLD[x - 1][y].description().equals("floor"))
                    && (game.WORLD[x][y + 1].description().equals("floor")) || game.WORLD[x][y - 1].description().equals("floor"));
        }
        return true;
    }

    void use() {
        uses -= 1;
        if (uses < 1) {
            game.robocop.weapon = new Nothing(game, Screen.Tileset.NOTHING);
            //TODO: write discard
        }
    }

}
