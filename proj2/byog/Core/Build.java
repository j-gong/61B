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

    }

    public static void buildOpenings(Location[] holes){
        for (int i = 0; i < holes.length; i += 1) {
            Map.LAYOUT[holes[i].xPos][holes[i].yPos] = Tileset.FLOOR;
        }
    }

    private static void buildFloors(Room rm) {
        TETile flooring = Tileset.FLOOR;
        for (int i = 0; i < rm.height; i += 1) {
            buildRow(rm.site, rm.width, flooring);
        }
    }

    private static void buildWalls(Room rm) {
        Location start = new Location(rm.site.xPos-1, rm.site.yPos-1);
        TETile walling = Tileset.WALL;

        buildRow(start, rm.width + 1, walling); //build bottom wall

        start.yPos += rm.height + 1;
        buildRow(start, rm.width + 1, walling); //build top wall

        start.yPos -= 1;
        buildColumn(start, rm.height, walling); //build left side wall

        start.xPos += rm.width + 1;
        buildColumn(start, rm.height, walling);
    }

    private static void buildRow(Location start, int length, TETile tile) {
        Location place = start.copy();
        for (int i = 0; i < length; i += 1) {
            Map.LAYOUT[place.xPos + i][place.yPos] = tile;
            place.xPos += 1;
        }
    }

    private static void buildColumn(Location start, int length, TETile tile) {
        Location place = start.copy();
        for (int i = 0; i < length; i += 1) {
            Map.LAYOUT[place.xPos][place.yPos - i] = tile;
        }
    }

}

