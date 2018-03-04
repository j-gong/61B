package byog.Core;

import java.io.Serializable;

public class Weapon extends Tools implements Serializable {

    public Weapon(int x, int y, Game game) {
        super(x, y, game);
    }
}
