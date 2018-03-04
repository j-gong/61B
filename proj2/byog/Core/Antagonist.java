package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;

public class Antagonist extends Character {

    //@Source docs.Oracle.com
    ArrayList<Location> previous = new ArrayList<>();

    Antagonist(Game game) {
        super(10, 10, game, Tileset.MOUNTAIN);

    }

    private class Pair {
        public final int x;
        public final int y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    void aiMove() {

        ArrayList<Pair> available = findSpaceOptions();

        if (available.size() == 0) {
            previous.clear();
            previous.add(new Location(this.x, this.y));
            aiMove();

        } else {

            Pair chosen = available.get(r.nextInt(available.size()));
            this.move(game.WORLD, chosen.x, chosen.y);
            previous.add(new Location(this.x, this.y));
            if (previous.size() == 3) {
                previous.remove(0);

            }
        }
    }

    private ArrayList<Pair> findSpaceOptions() {
        ArrayList<Pair> available = new ArrayList<>();
        for (int x = -1; x < 2; x += 1) {
            for (int y = -1; y < 2; y += 1) {
                if (validSpace(new Location(x, y))) {
                    available.add(new Pair(x, y));
                }
            }
        }
        return available;
    }

    private boolean validSpace(Location check) {
        TETile checkfloor = game.WORLD[this.x + check.xPos][this.y + check.yPos];
        Location locate = new Location(check.xPos, check.yPos);

        if (!checkfloor.description().equals("floor")) {
            return false;
        } else {
            for (int i = 0; i < previous.size(); i += 1) {
                if (locate.equals(previous.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

}
