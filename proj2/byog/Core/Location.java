package byog.Core;

import java.io.Serializable;

public class Location implements Serializable {
    int xPos;
    int yPos;

    public Location(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public Location copy() {
        Location copy = new Location(this.xPos, this.yPos);
        return copy;
    }
}

