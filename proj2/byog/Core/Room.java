package byog.Core;

import byog.TileEngine.TETile;

import static byog.Core.Map.*;

public class Room{

    public Location site;
    public Location[] openings = new Location[4];
    public int numOpenings = 0;

    public int width;
    public int height;
    public boolean noRoom;

    private static TETile[][] LAYOUT = Map.LAYOUT;

    public Room(Location startPoint) { //constructs the first room
        site = startPoint;
        digRoom();
    }

    /* Room constructor set from a hallway's exit location */
    public Room(Location entrance, int directionfrom) { //constructs the second room
        // direction 0 = left, 1 = top, 2 = right, 3 = bottom
        numOpenings += 1;

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
    public Room(Location st, int w, int h, Location[] holes){
        site = st;
        width = w;
        height = h;
        openings = holes;
    }

    /* holds procedures for making a room*/
    public void makeRoom() {

        Map.ROOMCOUNT += 1;

        digOpenings(); // creates a list of openings

        Build.buildRoom(this); //lays down room's floor and wall tiles

        for (int i = 0; i < 4; i += 1) { //make a new hallway for each hole
            if (openings[i] != null) {
                Hallway hall = new Hallway(openings[i], i);
                hall.makeHallway();
            }
        }

    }

    /* Searches for a valid place for the new Room and sets the width and height*/
    private void digRoom() {

        width = Map.R.nextInt(WIDTH / 8) + 2;
        height = Map.R.nextInt(HEIGHT / 8) + 2;

        if (!validSpace(site)) {
            site.xPos += 2;
            site.yPos += 2;
            digRoom();
        }

    }

    /* looks to see if a location is a valid space*/
    private boolean validSpace(Location check) {
        if ((check.xPos > 1 && check.xPos + width < WIDTH - 1) && (check.yPos > 1 && check.yPos + height < HEIGHT - 1)) {
            return noOverlap();
        }
        return false;
    }

    /* checks to see if any of the room's spaces are going to overlap with tiles already set down*/
    private boolean noOverlap() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (Map.LAYOUT[x][y] != null) {
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

        int minX = R.nextInt(8) + 1;
        int minY = R.nextInt(8) + 1;

        while (attempts > 0) {

            check = entrance.copy();
            height = R.nextInt(minY) + minY + 1;
            width = R.nextInt(minX) + minX + 1;

            if (directionfrom == 0) {
                check.yPos -= (minY - 1);
                check.xPos += 1; //site needs to be inside the walls
            } else if (directionfrom == 1) {
                check.yPos -= height;
                check.xPos -= (minX - 1);
            } else if (directionfrom == 2) {
                check.yPos -= (minY - 1);
                check.xPos -= width;
            } else {
                check.xPos -= (minX - 1);
                check.yPos += 1;
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
        if (found == false) {
            check = null;
        }
        site = check;
    }

    /* creates a set of random openings in the walls */
    private void digOpenings() {
        boolean need = false;
        if (ROOMCOUNT < 5) {
            need = true;
        }
        while (need) {
            int numHoles = R.nextInt(3 - numOpenings);
            if (ROOMCOUNT < 5 && numHoles < 1) {
                numHoles = 3;
            }
            for (int i = numHoles; i > 0; i -= 1) {
                int side = R.nextInt(3);
                if (openings[side] == null && !closeToEdge(site, side)) {
                    digHole(side);
                    need = false;
                }
            }
        }
    }

    private void digHole(int side) {
        if (side == 0){
            openings[side] = new Location(site.xPos - 1, site.yPos + R.nextInt(height));
        } else if (side == 1){
            openings[side] = new Location(site.xPos + R.nextInt(width), site.yPos + height);
        } else if (side == 2) {
            openings[side] = new Location(site.xPos + width, site.yPos + R.nextInt(height));
        } else {
            openings[side] = new Location(site.xPos + R.nextInt(width), site.yPos);
        }
    }

    private boolean closeToEdge(Location check, int side) {
        if (side == 0) {
            return check.xPos < Map.WIDTH / 6;
        } else if (side == 1) {
            return check.yPos > 5 * Map.HEIGHT / 6;
        } else if (side == 2) {
            return check.xPos > 5 * Map.WIDTH / 6;
        } else {
            return check.yPos < Map.WIDTH / 6;
        }
    }

}
