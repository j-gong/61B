package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Tools{

    int x;
    int y;
    Game game;
    String name;
    TETile tile;
    int uses;

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
        while (!found) {
            if (game.WORLD[x][y].description().equals("floor") && in_room()) {
                found = true;
            } else {
                x = r.nextInt(game.WIDTH);
                y = r.nextInt(game.HEIGHT);
            }
        }
        game.WORLD[x][y] = tile;
    }

    private boolean in_room() {
        return ((game.WORLD[x + 1][y].description().equals("floor") || game.WORLD[x - 1][y].description().equals("floor"))
                && (game.WORLD[x][y + 1].description().equals("floor")) || game.WORLD[x][y-1].description().equals("floor"));

    }

    void use() {
        uses -= 1;
        if (uses < 1) {
            //discard();
            //TODO: write discard
        }
    }

}
