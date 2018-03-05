package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.LinkedList;

public class Antagonist extends Character {

    boolean caught = false;
    static int damage = 0;

    //@Source docs.Oracle.com
    LinkedList<Location> previous = new LinkedList<>();

    Antagonist(Game game) {
        super(10, 10, game, Tileset.MOUNTAIN);

    }

    public class Pair {
        public final int x;
        public final int y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    void aiMove() {

        if (caught) {
            return;
        }

        LinkedList<Pair> available = findSpaceOptions();

        if (available.size() == 0) {
            previous.clear();
            previous.add(new Location(this.x, this.y));
            try {
                aiMove();
            } catch (StackOverflowError e) {
                return;
            }

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
        double distBetween = Math.sqrt(Math.pow(game.robocop.x - x, 2) - Math.pow(game.robocop.y - y, 2));

        if (distBetween < 7) {
            previous.clear();
        }

        for (int x = -1; x < 2; x += 1) {
            for (int y = -1; y < 2; y += 1) {
                Pair check = new Pair(x, y);
                if (runAway(check, distBetween) && validSpace(check)) {
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
            for (Location prev : previous) {
                if (prev.xPos == locate.xPos && prev.yPos == locate.yPos) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean runAway(Pair check, double origDistance) {
        //double origDistance = Math.sqrt(Math.pow(game.robocop.x - x, 2) - Math.pow(game.robocop.y - y, 2));
        double newXdist = Math.pow(game.robocop.x - (x + check.x), 2);
        double newYdist = Math.pow(game.robocop.y - (y + check.y), 2);

        double newDistance = Math.sqrt(newXdist + newYdist);
        if (origDistance < 7) {
            return newDistance > origDistance;
        }
        return true;
    }

    void interact() {
        game.robocop.interact();
        game.robocop.drain(damage);
    }

    public static void main(String[] args) {
        System.out.print(Math.pow(0, 2));
    }

    // //TODO: make sure anatagonists can't mess with tools.

}
