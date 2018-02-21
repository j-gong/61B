package byog.Core;

public class Hallway {

    public Location entrance;
    public Location exit;

    public int direction; // 0 = west, 1 = north, 2 = east, 3 = south
    public int length;

    public Hallway(Location enter, int toward) {
        direction = toward;
        entrance = enter;
    }

    public void makeHallway() {

        digHallway();

        Build.buildHallway(this);

        int rand = Map.R.nextInt(5); // 4 options for makeHallway to call

        /* IMPLEMENT THE SWITCH TILE THING */

        if (rand == 2 && Map.ROOMCOUNT < Map.MAXROOMS && edge()) {
            Room nextRoom = new Room(exit, direction);
            nextRoom.makeRoom();
        } else if (rand == 1) {
            Hallway next = new Hallway(exit, Map.R.nextInt());
            next.makeHallway();
        } else if (rand == 0 && Map.ROOMCOUNT > 5) {
            deadEnd();
        } else {
            Connect.connect(this);
        }

        /*this.connect();*/

    }


    private void digHallway() {
        int maxLength = validLength();

    }

    private int validLength() {
        Location start = entrance.copy();

        int[] compass = new int[]{-1, 1, 1, -1}; //compass is supposed to help with tunneldirection
    }

    private void deadEnd() {
        Location start = exit.copy();
    }

    private boolean edge() {
        Location check = exit.copy();

    }


}
