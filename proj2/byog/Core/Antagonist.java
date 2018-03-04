package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.LinkedList;

public class Antagonist extends Character {

    //@Source docs.Oracle.com
    LinkedList<Location> previous = new LinkedList<>();

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

        LinkedList<Pair> available = findSpaceOptions();

        if (available.size() == 0) {
            previous.clear();
            previous.add(new Location(this.x, this.y));
            aiMove();

        } else {

            Pair chosen = available.get(r.nextInt(available.size()));
            this.move(game.WORLD, chosen.x, chosen.y);
            previous.add(new Location(this.x, this.y));
            if (previous.size() > 3) {
                previous.remove();
            }
        }
    }

    private LinkedList<Pair> findSpaceOptions() {
        LinkedList<Pair> available = new LinkedList<>();
        for (int x = -1; x < 2; x += 1) {
            for (int y = -1; y < 2; y += 1) {
                Pair check = new Pair(x, y);
                if (validSpace(check)) {
                    available.add(new Pair(x, y));
                }
            }
        }
        return available;
    }

    private boolean validSpace(Pair check) {
        Location locate = new Location(this.x + check.x, this.y + check.y);
        TETile checkfloor = game.WORLD[locate.xPos][locate.yPos];


        if (!checkfloor.description().equals("floor")) {
            return false;
        } else {
            for (int i = 0; i < previous.size(); i += 1) {
                if (previous.get(i).xPos == locate.xPos && previous.get(i).yPos == locate.yPos) {
                    return false;
                }
            }
            return true;
        }
    }

}
