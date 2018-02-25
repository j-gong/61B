package byog.Core;
import byog.TileEngine.TETile;

public class Room {

    private Location site;
    private Location[] openings = new Location[4];
    private int numOpenings = 0;
    private Map key;
    private int width;
    private int height;
    private Build builder;

    //private static TETile[][] Map.LAYOUT = Map.Map.LAYOUT;

    public Room(Location startPoint, Map passed) { //constructs the first room
        key = passed;
        site = startPoint;
        builder = new Build(key);
        digRoom();
    }

    /* Room constructor set from a hallway's exit location */
    public Room(Location entrance, int directionfrom, Map passed) { //constructs the second room
        // direction 0 = left, 1 = top, 2 = right, 3 = bottom
        key = passed;
        numOpenings += 1;
        builder = new Build(key);

        int side;
        if (directionfrom < 2) {
            side = directionfrom + 2;
        } else {
            side = directionfrom - 2;
        }
        openings[side] = entrance;
        place(entrance, side); //picks the room's site based on which side the initial opening is

        if (site == null) {
            return;
        }

        makeRoom();
    }

    /* TESTING PURPOSES ONLY */
    public Room(Location st, int w, int h, Location[] holes) {
        site = st;
        width = w;
        height = h;
        openings = holes;
    }

    /* holds procedures for making a room*/
    public void makeRoom() {

        key.incrementRoomCount();

        digOpenings(5); // creates a list of openings

        builder.buildRoom(this); //lays down room's floor and wall tiles

        for (int i = 0; i < 4; i += 1) { //make a new hallway for each hole
            if (openings[i] != null) {
                Hallway hall = new Hallway(openings[i], i, key);
                hall.makeHallway();
            }
        }

    }

    /* Searches for a valid place for the new Room and sets the width and height*/
    private void digRoom() {

        width = key.getR().nextInt(key.getWIDTH() / 6) + 2;
        height = key.getR().nextInt(key.getHEIGHT() / 6) + 2;

        if (!validSpace(site)) {
            site.incrementXPos(2);
            site.incrementYPos(2);
            digRoom();
        }

    }

    /* looks to see if a location is a valid space*/
    private boolean validSpace(Location check) {
        if ((check.getxPos() > 1 && check.getxPos() + width < key.getWIDTH() - 1)
                && (check.getyPos() > 1 && check.getyPos() + height < key.getHEIGHT() - 1)) {
            return noOverlap();
        }
        return false;
    }

    /* checks to see if any of the room's spaces are going to overlap with tiles already set down*/
    private boolean noOverlap() {
        TETile[][] layout = key.getLAYOUT();
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (layout[x][y] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /* this function takes a specific point as a wall and sets the rooms dimensions from it.*/
    private void place(Location entrance, int directionfrom) {
        int attempts = 3;
        boolean found = false;
        Location check = null;

        int minX = key.getR().nextInt(8) + 1;
        int minY = key.getR().nextInt(8) + 1;

        while (attempts > 0) {

            check = entrance.copy();
            height = key.getR().nextInt(minY) + minY + 1;
            width = key.getR().nextInt(minX) + minX + 1;

            if (directionfrom == 0) {
                check.incrementYPos(-(minY - 1));
                check.incrementXPos(1); //site needs to be inside the walls
            } else if (directionfrom == 1) {
                check.incrementYPos(-height);
                check.incrementXPos(-(minX - 1));
            } else if (directionfrom == 2) {
                check.incrementYPos(-(minY - 1));
                check.incrementXPos(-width);
            } else {
                check.incrementYPos(1);
                check.incrementXPos(-(minX - 1));
            }

            if (validSpace(check)) {
                attempts = 0;
                found = true;
            } else {
                attempts -= 1;
                minX = (int) Math.ceil((double) minX / 2);
                minY = (int) Math.ceil((double) minY / 2);
            }

        }
        if (!found) {
            check = null;
            builder.dead(entrance);
        }
        site = check;
    }

    /* creates a set of random openings in the walls */
    private void digOpenings(int tries) {

        boolean need = key.getRoomNum() < 7;
        int numHoles = key.getR().nextInt(3 - numOpenings);
        if (need && numHoles < 1) {
            numHoles = 3;
        }
        for (int i = numHoles; i > 0; i -= 1) {
            int side = key.getR().nextInt(4);
            if (openings[side] == null && !closeToEdge(site, side)) {
                digHole(side);
            }
        }

        if (openings[3] == null && !closeToEdge(site, 3)) {
            digHole(3);
        }

        need = (key.getRoomNum() < key.getMin() && numOpenings < 3);
        if (need && tries > 0) {
            digOpenings(tries - 1);
        }

    }

    private void digHole(int side) {

        if (side == 0) {
            openings[side] = new Location(site.getxPos() - 1,
                    site.getyPos() + key.getR().nextInt(height));
        } else if (side == 1) {
            openings[side] = new Location(site.getxPos() + key.getR().nextInt(width),
                    site.getyPos() + height);
        } else if (side == 2) {
            openings[side] = new Location(site.getxPos() + width,
                    site.getyPos() + key.getR().nextInt(height));
        } else {
            openings[side] = new Location(site.getxPos() + key.getR().nextInt(width),
                    site.getyPos());
        }
        numOpenings += 1;
    }

    private boolean closeToEdge(Location check, int side) {
        if (side == 0) {
            return check.getxPos() < key.getWIDTH() / 6;
        } else if (side == 1) {
            return check.getyPos() > 5 * key.getHEIGHT() / 6;
        } else if (side == 2) {
            return check.getxPos() > 5 * key.getWIDTH() / 6;
        } else {
            return check.getyPos() < key.getWIDTH() / 6;
        }
    }

    public Location[] getOpenings() {
        return openings;
    }

    public Location getSite() {
        return site;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
