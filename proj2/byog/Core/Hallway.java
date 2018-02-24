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

        int rand = Map.R.nextInt(4);

        if (rand > 1) {

            if (Map.ROOMCOUNT <= Map.MAXROOMS && !onEdge(exit)) {

                Room add = new Room(exit, direction);

                if (add.site == null) {
                    if (Map.ROOMCOUNT < 5) {
                        Hallway newHall = new Hallway(exit,3 - direction);
                        newHall.makeHallway();
                    }
                    deadEnd();
                }
            }

        } else {

            int nextDirection = availablePaths();

            if (nextDirection < 0 || (Map.ROOMCOUNT > 5 && rand == 1)) {
                deadEnd();

            } else {

                Location nextStart = turn(nextDirection);

                Hallway next = new Hallway(nextStart, nextDirection);
                next.makeHallway();
            }
        }
    }

    /* sets the valid length and exit location of a hallway */
    private void digHallway() {
        int maxLength = validLength(direction, entrance);

        while(length != 0) {
            int trylength = Map.R.nextInt(maxLength);
            if (trylength < 3) {
                LAYOUT[entrance.xPos][entrance.yPos] = Tileset.WALL;
            } else if (maxLength != -1) {
                length = trylength;
            }
        }

        exit = entrance.copy();

        int[] compass = new int[]{-1, 1, 1, -1};
        int tunneldirection = compass[direction];

        if (direction % 2 == 0) {
            exit.xPos += tunneldirection * (length - 1);
        } else {
            exit.yPos += tunneldirection * (length - 1);
        }
    }

    /* returns the length of a possible hallway up to 8 spots, returns -1 if the algorithm runs into another floor space*/
    public int validLength(int direct, Location beginning) {


        int tunneldirection = tunnelDirect(direct);
        int openSpaces = 0;

        //check to see if there's a tile at the next spot
        Location place = beginning.copy();

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
            if (direction == 0) {
                left = 3;
            }
        int right = direction + 1;
            if (direction == 3) {
                right = 0;
            }

        int nextDirection = left;

        Location place = exit.copy();
        if (direction % 2 == 0) {
            place.xPos += tunnelDirect(direction);
        } else {
            place.yPos += tunnelDirect(direction);
        }

        boolean leftBad = validLength(left, place) < 3;
        boolean rightBad = validLength(right, place) < 3;

        if (leftBad && rightBad) {
            return -1;
        } else if (leftBad) { return right;
        } else if (rightBad) { return left;
        } else if (1 == Map.R.nextInt(2)){
            nextDirection = right;
        }
        return nextDirection;
    }

    /* reports true if a location is on the edge of the map*/
    public static boolean onEdge(Location place) {
        return (place.xPos < 1 || place.xPos > Map.WIDTH - 2) || (place.yPos < 1 || place.yPos > Map.HEIGHT - 2);
    }

    private void deadEnd() {

        Location start = exit.copy();

        if (direction % 2 == 0) {
            start.yPos += 1;
            Build.buildColumn(start, 3, Tileset.WALL, true);

        } else { start.xPos -= 1;
            Build.buildRow(start, 3, Tileset.WALL, true);
        }
    }

    /* moves the start of the new hallway so that the building algorithm doesn't screw up the other walls */
    private Location turn(int nextdirect) {
        Location result = exit.copy();
        if (direction % 2 == 0) {
            result.xPos += tunnelDirect(direction);
            Build.turnCap(direction, nextdirect, result);
        } else {
            result.yPos += tunnelDirect(direction);
            Build.turnCap(direction, nextdirect, result);
        }
        Map.LAYOUT[result.xPos][result.yPos] = Tileset.FLOOR;
        return result;
    }

}
