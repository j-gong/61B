package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hallway {

    private Map key;

    Location entrance;
    Location exit;

    int direction; // 0 = west, 1 = north, 2 = east, 3 = south
    int length;

    private boolean connected;

    private Build builder;

    public Hallway(Location enter, int toward, Map passed) {
        direction = toward;
        entrance = enter;
        key = passed;
        builder = new Build(key);
    }

    /* makes a hallway from the constructed direction and entrance*/
    public void makeHallway() {

        digHallway();

        if (exit == null) {
            return;
        }

        builder.buildHallway(this);

        if (connected) {
            return;
        }

        int rand = key.R.nextInt(6); // number between 0 - 5
        if (rand > 2) {
            rand = 0;
        }

        if (key.ROOMCOUNT < key.MINROOMS) {
            rand = key.R.nextInt(2);
        }
        int nextDirect = availablePaths();
        if (nextDirect < 0) {
            rand = 2;
        }
        if (key.ROOMCOUNT > key.MAXROOMS) {
            rand = 2;
        }

        switch (rand) {
            case 0: Room addroom = new Room(exit, direction, key);
                deadRoom(addroom);
                break;
            case 1: Location angle = turn(nextDirect);
                Hallway newhall = new Hallway(angle, nextDirect, key);
                newhall.makeHallway();
                break;
            case 2: builder.dead(exit);
                break;
            default: builder.dead(exit);
        }

    }

    /* sets the valid length and exit location of a hallway */
    private void digHallway() {
        int maxLength = validLength(direction, entrance);
        int tries = 0;

        if (maxLength < 1) {
            length = -maxLength;
            connected = true;

        } else {
            while (length == 0) {
                tries += 1;
                int trylength = key.R.nextInt(maxLength);
                if (trylength > 3) {
                    length = trylength;
                } else if (key.ROOMCOUNT > key.MINROOMS || maxLength <= 3 || tries > 5) {
                    builder.dead(entrance);
                    return;
                }
            }
        }

        exit = entrance.copy();

        int[] compass = new int[]{-1, 1, 1, -1};
        int tunneldirection = compass[direction];

        if (direction % 2 == 0) {
            exit.xPos += (tunneldirection * (length - 1));
        } else {
            exit.yPos += (tunneldirection * (length - 1));
        }
    }

    /* returns the length of a possible hallway up to 8 spots,
    returns -1 if the algorithm runs into another floor space*/
    public int validLength(int direct, Location beginning) {


        int tunneldirection = tunnelDirect(direct);
        int openSpaces = 0;
        TETile[][] layout = key.LAYOUT;

        //check to see if there's a tile at the next spot
        Location place = beginning.copy();

        while (openSpaces < 8 && !onEdge(place)) {

            //advance the placeholder
            if (direct % 2 == 0) {
                place.xPos += (tunneldirection);
            } else {
                place.yPos += (tunneldirection);
            }

            TETile check = layout[place.xPos][place.yPos];

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

    /* returns an integer based on horizontal or vertical side
    that lets us add that value to get the next position in the path*/
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
            place.xPos += (tunnelDirect(direction));
        } else {
            place.yPos += (tunnelDirect(direction));
        }

        boolean leftBad = validLength(left, place) < 5;
        boolean rightBad = validLength(right, place) < 5;

        if (leftBad && rightBad) {
            return -1;
        } else if (leftBad) {
            return right;
        } else if (rightBad) {
            return left;
        } else if (1 == key.R.nextInt(2)) {
            nextDirection = right;
        }
        return nextDirection;
    }

    /* reports true if a location is on the edge of the key*/
    public boolean onEdge(Location place) {
        return (place.xPos < 1 || place.xPos > key.WIDTH - 2)
                || (place.yPos < 1 || place.yPos > key.HEIGHT - 2);
    }

    /* moves the start of the new hallway so building algorithm doesn't screw up the other walls */
    private Location turn(int nextdirect) {
        TETile[][] layout = key.LAYOUT;
        Location result = exit.copy();
        if (direction % 2 == 0) {
            result.xPos += (tunnelDirect(direction));
            builder.turnCap(direction, nextdirect, result);
        } else {
            result.yPos += (tunnelDirect(direction));
            builder.turnCap(direction, nextdirect, result);
        }
        layout[result.xPos][result.yPos] = Tileset.FLOOR;
        return result;
    }

    /* if the map needs more rooms, try to make a hallway. Else, trigger a deadEnd*/
    private void deadRoom(Room rm) {
        if (rm.site == null) {
            if (key.ROOMCOUNT < key.MINROOMS) {
                Hallway newhall = new Hallway(exit, direction, key);
                newhall.makeHallway();
            } else {
                builder.dead(exit);
            }
        }
    }

}