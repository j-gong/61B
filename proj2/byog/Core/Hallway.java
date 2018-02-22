package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Hallway {

    public Location entrance;
    public Location exit;

    public int direction; // 0 = west, 1 = north, 2 = east, 3 = south
    public int length;

    private TETile[][] LAYOUT = Map.LAYOUT;

    public Hallway(){};

    public Hallway(Location enter, int toward) {
        direction = toward;
        entrance = enter;
    }

    public void makeHallway() {

        digHallway();

        Build.buildHallway(this);

        int rand = Map.R.nextInt(3);

        /* IMPLEMENT THE SWITCH TILE THING */

        //@source from Stack overflow
        if (rand > 1) {

            if (Map.ROOMCOUNT <= Map.MAXROOMS && !onEdge(exit)) {
                Room add = new Room(exit, direction);

                if (add.site == null) {
                    deadEnd();
                }
            }

        } else {

            int nextDirection = availablePaths();

            if (nextDirection < 0 || (Map.ROOMCOUNT > 5 && rand == 1)) {
                deadEnd();
            }

            Hallway next = new Hallway(exit, direction);
            next.makeHallway();
        }
    }

    /* sets the valid length and exit location of a hallway */
    private void digHallway() {
        int maxLength = validLength(direction);

        if (maxLength == 0) {
            exit = entrance.copy();
            deadEnd();
        } else if (maxLength != -1) {
            length = Map.R.nextInt(maxLength) + 1;
        }

        exit = entrance.copy();

        int[] compass = new int[]{-1, 1, 1, -1};
        int tunneldirection = compass[direction];

        if (direction % 2 == 0) {
            exit.xPos += tunneldirection * length;
        } else {
            exit.yPos += tunneldirection * length;
        }
    }

    /* returns the length of a possible hallway up to 8 spots, returns -1 if the algorithm runs into another floor space*/
    public int validLength(int direct) {


        int tunneldirection = tunnelDirect(direct);
        int openSpaces = 0;

        //check to see if there's a tile at the next spot
        Location place = entrance.copy();

        while (openSpaces < 8 && !onEdge(place)) {

            //advance the placeholder
            if (direct % 2 == 0) {
                place.xPos += tunneldirection;
            } else {
                place.yPos += tunneldirection;
            }

            TETile check = LAYOUT[place.xPos][place.yPos];

            //see what block the placeholder is at now
            if (check != null) {
                if (check.description().equals("floor")) {
                    return -1;
                }
                break;
            } else {
                openSpaces += 1;
            }
        }
        return openSpaces;
    }

    /* returns an integer based on horizontal or vertical side that lets us add that value to get the next position in the path*/
    private int tunnelDirect(int direct) {
        int[] compass = new int[]{-1, 1, 1, -1};
        return compass[direct];
    }

    /* returns the direction of a new path for a new hallway, only allows 90 degree turns */
    private int availablePaths() {
        int left = direction - 1;
        int right = direction + 1;
        int nextDirection = left;

        if (direction == 0) {
            left = 3;
        } else if (direction == 3) {
            right = 0;
        }

        boolean leftBad = validLength(left) < 3;
        boolean rightBad = validLength(right) < 3;

        if (leftBad && rightBad) {
            return -1;
        } else if (leftBad) { return right;
        } else if (rightBad) { return left;
        } else if (1 == Map.R.nextInt(2)){
            nextDirection = right;
        }
        return nextDirection;
    }

    /* returns the direction of the cardinal opposite of another
    private int oppositeDirection(int i) {
        if (i > 1) {
            return i - 2;
        }
        return i + 2;
    }

    /* reports true if a location is on the edge of the map*/
    public static boolean onEdge(Location place) {
        return (place.xPos < 1 || place.xPos > Map.WIDTH - 2) || (place.yPos < 1 || place.yPos > Map.HEIGHT - 2);
    }

    private void deadEnd() {

        Location start = exit.copy();

        if (direction % 2 == 0) {
            start.yPos += 1;
            Build.buildColumn(start, 3, Tileset.WALL);

        } else { start.xPos -= 1;
            Build.buildRow(start, 3, Tileset.WALL);
        }
    }

}
