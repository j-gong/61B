package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Player extends Character {

    int energy;
    int capacity;
    Tools weapon;
    String name = "Robocop";

    Player(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.capacity = 30;
    }

    void pickup(Tools grab) {
        if (grab.name.equals("energy")) {
            refill();
        } else {
            weapon = grab;
        }
    }

    void move(TETile[][] world, int xDir, int yDir) {
        world[this.x][this.y] = Tileset.FLOOR;

        this.x += xDir;
        this.y += yDir;

        world[this.x][this.y] = Tileset.PLAYER;
    }

    private void refill() {
        energy = capacity;
    }

}
