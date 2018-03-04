package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Player extends Character {

    int energy;
    int capacity;
    Tools weapon;

    Player(int x, int y, Game game) {
        super(x, y, game, Tileset.PLAYER);
        this.capacity = 30;
    }

    void pickup(Tools grab) {
        if (grab.name.equals("energy")) {
            refill();
        } else {
            weapon = grab;
        }
    }

    private void refill() {
        energy = capacity;
    }

}
