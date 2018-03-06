package byog.Core;


import java.io.Serializable;

public class Nrgy extends Tools implements Serializable {



    boolean used;
    //took this out of tools, might cause an error

    Nrgy(Game game) {
        super(game, Screen.Tileset.GRASS);
        used = false;
        name = "energy";

    }

}
