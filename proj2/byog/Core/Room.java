package byog.Core;

import static byog.Core.Map.*;

public class Room{

    public Location site;
    public Location[] openings = new Location[4];
    public int numOpenings = 0;
    /*public Hallway[] halls;*/

    public int width;
    public int height;

    public Room(Location startPoint) { //constructs the first room
        site = startPoint;
    }

    public Room(Location entrance, int directionfrom) { //constructs the second room
        // direction 0 = left, 1 = top, 2 = right, 3 = bottom
        numOpenings += 1;

        int side;
        if (directionfrom < 2) {
            side = directionfrom + 2;
        } else {
            side = directionfrom -2;
        }
        openings[side] = entrance;
        site = place(entrance, directionfrom); //picks the room's site based on which side the initial opening is
    }

    /* TESTING PURPOSES ONLY */
    public Room(Location st, int w, int h, Location[] holes){
        site = st;
        width = w;
        height = h;
        openings = holes;
    }

    public void makeRoom() {

        Map.ROOMCOUNT += 1;

        digRoom(); //creates the room's dimensions

        digOpenings(); // creates a list of openings

        Build.buildRoom(this); //lays down room's floor and wall tiles

        for (int i = 0; i < 4; i += 1) { //make a new hallway for each hole
            if (openings[i] != null) {
                Hallway hall = new Hallway(openings[i], i);
                hall.makeHallway();
            }
        }

    }

    private void digRoom() {
        boolean found = false;
        while (!found) {
            width = Map.R.nextInt(WIDTH / 8);
            height = Map.R.nextInt(HEIGHT / 8);

            if (noOverlap()) {
                found = true;
            }
        }
    }

    private boolean validSpace(Location check) {
        if ((check.xPos > 0 && check.xPos + width < WIDTH) && (check.yPos > 0 && check.yPos + height < HEIGHT)) {
            site = check;
            return noOverlap();
        }
        return false;
    }

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


    /* HAVE TO ADD INITIAL ENTRANCE TO OPENINGS*/
    private Location place(Location entrance, int directionfrom) {
        Location check = entrance.copy();
        boolean found = false; //stopping point

        while (!found) { //goes until valid room

            if (directionfrom % 2 == 1) { //if coming from vertical direction
                int rand = Map.R.nextInt(WIDTH / 8);

                check.xPos = entrance.xPos - rand; //opening is random spaces away from check

                width = Map.R.nextInt(WIDTH / 8) + rand; //width must be at least rand wide
                height = Map.R.nextInt(HEIGHT / 8);


                if (directionfrom == 1) { //if coming from the above, set yPos the other side of the opening
                    check.yPos = entrance.yPos - height;
                }


            } else { //if coming from horizontal direction
                int rand = Map.R.nextInt(HEIGHT / 8);

                check.yPos = entrance.yPos - rand;

                height = Map.R.nextInt(WIDTH / 8);
                width = Map.R.nextInt(WIDTH / 8) + height;

                if (directionfrom == 2) { //if coming from the right, set xPos the other side of the opening
                    check.xPos = entrance.xPos - width;
                }
            }
            if (validSpace(check)) {
                found = true;
            }
        }
        openings[directionfrom] = entrance.copy();
        return check;
    }

    private void digOpenings() {

        for (int i = 0; i < 4; i += 1) {
            if (!edges(i) && openings[i] == null) { //eligible openings (not close to an edge, no opening already there
                if (R.nextInt(2) > 0 || ROOMCOUNT < 5) { // 1/2 chance an eligible side has an opening

                    if (i % 2 == 0) { // if horizontal side
                        openings[i] = new Location(site.xPos, site.yPos + R.nextInt(height));
                    } else {
                        openings[i] = new Location(site.xPos + R.nextInt(width), site.yPos);
                    }


                }
            }
        }

    }

    private boolean edges(int i) {
        if (i % 2 == 0) {
            return site.xPos > (WIDTH / 6) && site.xPos < (5 * WIDTH / 6);
        } else {
            return site.yPos > (HEIGHT / 6) && site.yPos < (5 * WIDTH / 6);
        }
    }

}
