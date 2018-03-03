package byog.Core;

import byog.TileEngine.Tileset;

import java.util.Random;

public class Character {
    int x;
    int y;
    Game game;
    String imgFileName;

    void place() {
        Random r = new Random(game.seed);
        boolean found = false;
        while (!found) {
            if (game.WORLD[x][y].description().equals("floor")) {
                found = true;
            } else {
                x = r.nextInt(game.WIDTH);
                y = r.nextInt(game.HEIGHT);
            }
        }
        game.WORLD[x][y] = Tileset.PLAYER;
    }

}
