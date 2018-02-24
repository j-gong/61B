package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Build {

    private static TETile[][] LAYOUT = Map.LAYOUT;

    public static void buildRoom(Room rm) {

        buildFloors(rm);

        buildWalls(rm);

        buildOpenings(rm.openings);
    }

    public static void buildHallway(Hallway hall) {

        Location start = hall.entrance.copy();
        TETile tile;

        for (int i = -1; i < 2; i += 1) {

            if (i == 0) {
                tile = Tileset.FLOOR;
            } else {
                tile = Tileset.WALL;
            }

            if (hall.direction % 2 == 0) {

                if (hall.direction == 0) {
                    start.xPos -= hall.exit.xPos;
                }
                Build.buildRow(new Location(start.xPos, start.yPos + i), hall.length, tile, false);

            } else {
                if(hall.direction == 1) {
                    start.yPos = hall.exit.yPos;
                }
                buildColumn(new Location(start.xPos + i, start.yPos), hall.length, tile, false);
            }
        }
    }

    /* don't know if this block is a good thing to have, should probably just reconfigure makeRow and makeColumn*/
    /*private static void freeEnds(Hallway hall) {
        Location end = hall.exit.copy();
        if (hall.direction % 2 == 0 ) {
            LAYOUT[end.xPos][end.yPos + 1] = null;
            LAYOUT[end.xPos][end.yPos - 1] = null;
        } else {
            LAYOUT[end.xPos + 1][end.yPos] = null;
            LAYOUT[end.xPos - 1][end.yPos] = null;
        }
    }
*/
    private static void buildOpenings(Location[] holes){
        for (int i = 0; i < 4; i += 1) {
            if (holes[i] != null) {
                LAYOUT[holes[i].xPos][holes[i].yPos] = Tileset.FLOOR;
            }
        }
    }

    private static void buildFloors(Room rm) {
        Location start = rm.site.copy();
        TETile flooring = Tileset.FLOOR;
        for (int i = 0; i < rm.height; i += 1) {
            buildRow(start, rm.width, flooring, true);
            start.yPos += 1;
        }
    }

    private static void buildWalls(Room rm) {
        Location start = new Location(rm.site.xPos-1, rm.site.yPos-1);
        TETile walling = Tileset.WALL;

        buildRow(start, rm.width + 2, walling, false); //build bottom wall

        start.yPos += rm.height + 1;
        buildRow(start, rm.width + 2, walling, false); //build top wall

        start.yPos -= 1;
        buildColumn(start, rm.height, walling, false); //build left side wall

        start.xPos += rm.width + 1;
        buildColumn(start, rm.height, walling, false);
    }

    public static void buildRow(Location start, int length, TETile tile, boolean overwrite) {
        boolean over = overwrite;
        Location place = start.copy();
        for (int i = 0; i < length; i += 1) {
            TETile check = LAYOUT[place.xPos + i][place.yPos];
            if (check == null || over) {
                LAYOUT[place.xPos + i][place.yPos] = tile;
            }
        }
    }

    public static void buildColumn(Location start, int length, TETile tile, boolean overwrite) {
        Location place = start.copy();
        for (int i = 0; i < length; i += 1) {
            TETile check = LAYOUT[place.xPos][place.yPos - i];
            if (check == null || overwrite) {
                LAYOUT[place.xPos][place.yPos - i] = tile;
            }
        }
    }

    public static void turnCap(int oldDirection, int newDirection, Location exit) {
        Location place = exit.copy();
        Location[] corner = new Location[2];
        int[] compass = new int[]{-1, 1, 1, -1};

        int opposite = newDirection;
        if (newDirection < 2) {
            opposite += 2;
        } else {
            opposite -= 2;
        }

        if (oldDirection % 2 == 0) {

            corner[0] = new Location(place.xPos, place.yPos + compass[opposite]);
            corner[1] = new Location(place.xPos + compass[oldDirection], place.yPos + compass[opposite]);

        }  else {

            corner[0] = new Location(place.xPos + compass[opposite], place.yPos);
            corner[1] = new Location(place.xPos + compass[opposite], place.yPos + compass[oldDirection]);

        }
        buildTurn(corner);
    }

    private static void buildTurn(Location[] corner) {
        TETile walling = Tileset.WALL;
        for (int i = 0; i < 2; i += 1) {
            TETile check = LAYOUT[corner[i].xPos][corner[i].yPos];
            if (check == null) {
                LAYOUT[corner[i].xPos][corner[i].yPos] = walling;
            }
        }
    }

    public static void dead(Location stop){
        for (int x = 0; x < 2; x += 1) {
            for (int y = 0; y < 2; y += 1) {
                TETile spot = LAYOUT[stop.xPos + x][stop.yPos + y];
                if (spot == null) {
                    spot = Tileset.WALL;
                }
            }
        }
    }

}

