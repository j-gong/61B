package byog.Core;

import byog.TileEngine.Tileset;

public class Player {
    private int x;
    private int y;
    private Game game;
    private String imgFileName;
    private Tileset PLAYER;

    public Player(int x, int y, Game game, Tileset PLAYER) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.PLAYER = PLAYER;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
