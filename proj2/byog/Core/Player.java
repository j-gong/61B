package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Player extends Character {

    int energy;
    int capacity;
    Tools weapon;
    Pair last;

    Player(int x, int y, Game game) {
        super(x, y, game, Tileset.PLAYER);
        this.capacity = 100;
        this.energy = capacity;
        this.weapon = new Nothing(game, Tileset.NOTHING);
    }

    class Pair {
        public final int x;
        public final int y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    void move(TETile[][] world, int xDir, int yDir) {
        last = new Pair(xDir, yDir);
        super.move(world, xDir, yDir);
    }

    private void pickup(Tools grab) {
        if (grab.name.equals("energy")) {
            refill((Nrgy) grab);
        } else {
            weapon = grab;
            //TODO: put old weapon back on ground, write swapTools()
        }
    }

    private void refill(Nrgy grab) {
        if (!grab.used) {
            this.energy = capacity;
            grab.used = true;
        }
    }

    void drain(int amount) {
        this.energy -= amount;
    }

    void interact() {
        for (Antagonist bad : game.criminals) {
            if (bad.x == x && bad.y == y) {
                apprehend(bad);
            }
        }

        for (Tools item : game.items) {
            if (item.x == this.x && item.y == this.y) {
                //TODO: make multiple tiles with tools as description
                this.pickup(item);
            }
        }
    }

    void apprehend(Antagonist subject) {
        subject.tile = Tileset.FLOWER;
        subject.aiMove();
        subject.caught = true;
        game.WORLD[x][y] = Tileset.PLAYER;
        game.crimsleft -= 1;
    }

}
