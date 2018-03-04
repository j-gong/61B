package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Character {
    protected int x;
    protected int y;
    protected Game game;
    TETile tile;

    Character(int x, int y, Game game, TETile tileset) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.tile = tileset;

        this.place();

    }

    private void place() {
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
        game.WORLD[x][y] = tile;
    }

    void move(TETile[][] world, int xDir, int yDir) {

        Location check = new Location(this.x + xDir, this.y + yDir);

        if (game.WORLD[check.xPos][check.yPos].description().equals("floor")) {

            world[this.x][this.y] = Tileset.FLOOR;

            this.x = check.xPos;
            this.y = check.yPos;

            world[this.x][this.y] = tile;

        }
    }

}
