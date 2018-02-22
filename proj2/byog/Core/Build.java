package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Build {

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
                Build.buildRow(new Location(start.xPos, start.yPos + i), hall.length, tile);

            } else {
                if(hall.direction == 1) {
                    start.yPos = hall.exit.yPos;
                }
                buildColumn(new Location(start.xPos + i, start.yPos), hall.length, tile);
            }
        }
    }

    public static void buildOpenings(Location[] holes){
        for (int i = 0; i < 4; i += 1) {
            if (holes[i] != null) {
                Map.LAYOUT[holes[i].xPos][holes[i].yPos] = Tileset.FLOOR;
            }
        }
    }

    private static void buildFloors(Room rm) {
        Location start = rm.site.copy();
        TETile flooring = Tileset.FLOOR;
        for (int i = 0; i < rm.height; i += 1) {
            buildRow(start, rm.width, flooring);
            start.yPos += 1;
        }
    }

    private static void buildWalls(Room rm) {
        Location start = new Location(rm.site.xPos-1, rm.site.yPos-1);
        TETile walling = Tileset.WALL;

        buildRow(start, rm.width + 2, walling); //build bottom wall

        start.yPos += rm.height + 1;
        buildRow(start, rm.width + 2, walling); //build top wall

        start.yPos -= 1;
        buildColumn(start, rm.height, walling); //build left side wall

        start.xPos += rm.width + 1;
        buildColumn(start, rm.height, walling);
    }

    public static void buildRow(Location start, int length, TETile tile) {
        Location place = start.copy();
        for (int i = 0; i < length; i += 1) {
            Map.LAYOUT[place.xPos + i][place.yPos] = tile;
        }
    }

    public static void buildColumn(Location start, int length, TETile tile) {
        Location place = start.copy();
        for (int i = 0; i < length; i += 1) {
            Map.LAYOUT[place.xPos][place.yPos - i] = tile;
        }
    }

}

