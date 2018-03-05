package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


import javax.tools.Tool;
import java.io.Serializable;
import java.util.Random;

public class Player extends Character implements Serializable {

    int energy;
    int capacity;
    Tools weapon;

    Player(int x, int y, Game game) {
        super(x, y, game, Tileset.PLAYER);
        this.capacity = 100;
        this.energy = capacity;
    }

    private void pickup(Tools grab) {
        if (grab.name.equals("energy")) {
            refill(grab);
        } else {
            weapon = grab;
            //TODO: put old weapon back on ground
        }
    }

    private void refill(Tools grab) {
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

        //TODO: change number of apprehended criminals in the HUD.
    }

}
