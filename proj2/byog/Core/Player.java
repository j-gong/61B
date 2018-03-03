package byog.Core;

import byog.TileEngine.Tileset;

public class Player {
    int x;
    int y;
    Game game;
    String imgFileName;
    Tileset PLAYER;

    public Player(int x, int y, Game game, Tileset PLAYER) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.PLAYER = PLAYER;
    }

}
