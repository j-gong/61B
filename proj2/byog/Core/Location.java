package byog.Core;

public class Location {
    public int xPos;
    public int yPos;

    public Location(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public Location copy() {
        Location copy = new Location(this.xPos, this.yPos);
        return copy;
    }

}
