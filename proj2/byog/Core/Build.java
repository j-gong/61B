package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Build {

    private Map key;

    public Build(Map passed) {
        key = passed;
    }

    public void buildRoom(Room rm) {

        buildFloors(rm);

        buildWalls(rm);

        buildOpenings(rm.getOpenings());
    }

    public void buildHallway(Hallway hall) {

        Location start = hall.getEntrance().copy();
        TETile tile;

        for (int i = -1; i < 2; i += 1) {

            if (i == 0) {
                tile = Tileset.FLOOR;
            } else {
                tile = Tileset.WALL;
            }


            if (hall.getDirection() % 2 == 0) {

                if (hall.getDirection() == 0) {
                    start.setXPos(start, hall.getExit().getxPos());
                }
                buildRow(new Location(start.getxPos(), start.getyPos() + i), hall.getLength(), tile, false);

            } else {
                if (hall.getDirection() == 1) {
                    start.setYPos(start, hall.getExit().getyPos());
                }
                buildColumn(new Location(start.getxPos() + i, start.getyPos()), hall.getLength(), tile, false);
            }
        }
    }

    private void buildOpenings(Location[] holes) {
        TETile[][] Layout = key.getLAYOUT();
        for (int i = 0; i < 4; i += 1) {
            if (holes[i] != null) {
                Layout[holes[i].getxPos()][holes[i].getyPos()] = Tileset.FLOOR;
            }
        }
    }

    private void buildFloors(Room rm) {
        Location start = rm.getSite().copy();
        TETile flooring = Tileset.FLOOR;
        for (int i = 0; i < rm.getHeight(); i += 1) {
            buildRow(start, rm.getWidth(), flooring, true);
            start.changePos(start, 0, 1);
        }
    }

    private void buildWalls(Room rm) {
        Location start = new Location(rm.getSite().getxPos() - 1, rm.getSite().getyPos() - 1);
        TETile walling = Tileset.WALL;

        buildRow(start, rm.getWidth() + 2, walling, false); //build bottom wall

        start.changePos(start, 0, rm.getHeight() + 1);
        buildRow(start, rm.getWidth() + 2, walling, false); //build top wall

        start.changePos(start, 0, -1);
        buildColumn(start, rm.getHeight(), walling, false); //build left side wall

        start.changePos(start, rm.getWidth() + 1, 0);
        buildColumn(start, rm.getHeight(), walling, false);
    }

    public void buildRow(Location start, int length, TETile tile, boolean overwrite) {
        boolean over = overwrite;
        Location place = start.copy();
        TETile[][] Layout = key.getLAYOUT();
        for (int i = 0; i < length; i += 1) {
            TETile check = Layout[place.getxPos() + i][place.getyPos()];
            if (check == null || over) {
                Layout[place.getxPos() + i][place.getyPos()] = tile;
            }
        }
    }

    public void buildColumn(Location start, int length, TETile tile, boolean overwrite) {
        Location place = start.copy();
        TETile[][] Layout = key.getLAYOUT();
        for (int i = 0; i < length; i += 1) {
            TETile check = Layout[place.getxPos()][place.getyPos() - i];
            if (check == null || overwrite) {
                Layout[place.getxPos()][place.getyPos() - i] = tile;
            }
        }
    }

    public void turnCap(int oldDirection, int newDirection, Location exit) {
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

            corner[0] = new Location(place.getxPos(), place.getyPos() + compass[opposite]);
            corner[1] = new Location(place.getxPos() +
                    compass[oldDirection], place.getyPos() + compass[opposite]);

        }  else {

            corner[0] = new Location(place.getxPos() + compass[opposite], place.getyPos());
            corner[1] = new Location(place.getxPos() +
                    compass[opposite], place.getyPos() + compass[oldDirection]);

        }
        buildTurn(corner);
    }

    private void buildTurn(Location[] corner) {
        TETile walling = Tileset.WALL;
        TETile[][] Layout = key.getLAYOUT();
        for (int i = 0; i < 2; i += 1) {
            TETile check = Layout[corner[i].getxPos()][corner[i].getyPos()];
            if (check == null) {
                Layout[corner[i].getxPos()][corner[i].getyPos()] = walling;
            }
        }
    }

    public void dead(Location stop) {
        TETile[][] Layout = key.getLAYOUT();
        for (int x = -1; x < 2; x += 1) {
            for (int y = -1; y < 2; y += 1) {
                TETile spot = Layout[stop.getxPos() + x][stop.getyPos() + y];
                if (spot == null) {
                    Layout[stop.getxPos() + x][stop.getyPos() + y] = Tileset.WALL;
                }
            }
        }
    }

}
