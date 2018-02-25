package byog.Core;

public class Location {
    private int xPos;
    private int yPos;

    public Location(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public Location copy() {
        Location copy = new Location(this.xPos, this.yPos);
        return copy;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void changePos(Location start, int changeX, int changeY) {
        if (changeX == 0) {
            start.yPos += changeY;
        }
        else {
            start.xPos += changeX;
        }
    }

    public void setXPos(Location start, int setValue) {
        start.xPos = setValue;
    }

    public void setYPos(Location start, int setValue) {
        start.yPos = setValue;
    }

    public void incrementXPos(int x) {
        xPos += x;
    }

    public void incrementYPos(int y) {
        yPos += y;
    }
}
