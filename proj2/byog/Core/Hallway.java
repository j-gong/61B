package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hallway {

    private Map key;

    private Location entrance;
    private Location exit;

    private int direction; // 0 = west, 1 = north, 2 = east, 3 = south
    private int length;

    private boolean connected;

    private Build builder;

    public Hallway(Location enter, int toward, Map passed) {
        direction = toward;
        entrance = enter;
        key = passed;
        builder = new Build(key);
    }

    public void makeHallway() {

        digHallway();

        if (exit == null) {
            return;
        }

        builder.buildHallway(this);

        if (connected) {
            return;
        }

        int rand = key.getR().nextInt(6); // number between 0 - 5
        if (rand > 2) {
            rand = 0;
        }

        if (key.getRoomNum() < key.getMin()) {
            rand = key.getR().nextInt(2);
        }
        int nextDirect = availablePaths();
        if (nextDirect < 0) {
            rand = 2;
        }
        if (key.getRoomNum() > key.getMax()) {
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





        //    we need:
        //make a room
        //if room doesnt fit deadEnd it
        //add hallway
        //if hallway doesnt fit deadEnd it
        //deadEnd
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
                int trylength = key.getR().nextInt(maxLength);
                if (trylength > 3) {
                    length = trylength;
                } else if (key.getRoomNum() > key.getMin() || maxLength <= 3 || tries > 5) {
                    builder.dead(entrance);
                    return;
                }
            }
        }

        exit = entrance.copy();

        int[] compass = new int[]{-1, 1, 1, -1};
        int tunneldirection = compass[direction];

        if (direction % 2 == 0) {
            exit.incrementXPos(tunneldirection * (length - 1));
        } else {
            exit.incrementYPos(tunneldirection * (length - 1));
        }
    }

    /* returns the length of a possible hallway up to 8 spots,
    returns -1 if the algorithm runs into another floor space*/
    public int validLength(int direct, Location beginning) {


        int tunneldirection = tunnelDirect(direct);
        int openSpaces = 0;
        TETile[][] layout = key.getLAYOUT();

        //check to see if there's a tile at the next spot
        Location place = beginning.copy();

        while (openSpaces < 8 && !onEdge(place)) {

            //advance the placeholder
            if (direct % 2 == 0) {
                place.incrementXPos(tunneldirection);
            } else {
                place.incrementYPos(tunneldirection);
            }

            TETile check = layout[place.getxPos()][place.getyPos()];

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
            place.incrementXPos(tunnelDirect(direction));
        } else {
            place.incrementYPos(tunnelDirect(direction));
        }

        boolean leftBad = validLength(left, place) < 5;
        boolean rightBad = validLength(right, place) < 5;

        if (leftBad && rightBad) {
            return -1;
        } else if (leftBad) {
            return right;
        } else if (rightBad) {
            return left;
        } else if (1 == key.getR().nextInt(2)) {
            nextDirection = right;
        }
        return nextDirection;
    }

    /* reports true if a location is on the edge of the key*/
    public boolean onEdge(Location place) {
        return (place.getxPos() < 1 || place.getxPos() > key.getWIDTH() - 2)
                || (place.getyPos() < 1 || place.getyPos() > key.getHEIGHT() - 2);
    }

    /* moves the start of the new hallway so building algorithm doesn't screw up the other walls */
    private Location turn(int nextdirect) {
        TETile[][] layout = key.getLAYOUT();
        Location result = exit.copy();
        if (direction % 2 == 0) {
            result.incrementXPos(tunnelDirect(direction));
            builder.turnCap(direction, nextdirect, result);
        } else {
            result.incrementYPos(tunnelDirect(direction));
            builder.turnCap(direction, nextdirect, result);
        }
        layout[result.getxPos()][result.getyPos()] = Tileset.FLOOR;
        return result;
    }

    private void deadRoom(Room rm) {
        if (rm.getSite() == null) {
            if (key.getRoomNum() < key.getMin()) {
                Hallway newhall = new Hallway(exit, direction, key);
                newhall.makeHallway();
            } else {
                builder.dead(exit);
            }
        }
    }

    public Location getEntrance() {
        return entrance;
    }

    public int getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }

    public Location getExit() {
        return exit;
    }
}

