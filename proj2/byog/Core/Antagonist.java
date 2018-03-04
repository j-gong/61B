package byog.Core;

import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class Antagonist extends Character {

    ArrayList<Location> previous;

    Antagonist(Game game) {
        super(10, 10, game, Tileset.MOUNTAIN);

    }

    void aiMove() {

        ArrayList<Integer> available = new ArrayList<>();

        for (int x = -1; x < 2; x += 1) {
            for (int y = -1; y < 2; y += 1) {
                if (validSpace(new Location(x, y))) {

                }
            }
        }
        //if  available is empty, clear previous


        previous.add(new Location(this.x, this.y));
        previous.remove(0);
    }

    private boolean validSpace(Location check) {
        if (!game.WORLD[check.xPos][check.yPos].description().equals("floor")) {
            for (int i = 0; i < previous.size(); i += 1) {
                if (!previous.get(i).equals(check)) {
                    return true;
            }
            return false;
    }



    /*void randomMove() {

        Random r = new Random(game.seed);

        Location track = new Location(this.x, this.y);
        Location start = track.copy();

        int[] dir = new int[]{-1, 0, 1};

        while (track.xPos == start.xPos && track.yPos == start.yPos) {

            this.move(game.WORLD, dir[r.nextInt(3)], dir[r.nextInt(3)]);
            track = new Location(this.x, this.y);

        }*/

    }




}
