package byog.Core;

public class Player {
    private int x;
    private int y;
    private Game game;
    private String imgFileName;

    public Player(int x, int y, Game game, String avatar) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.imgFileName = avatar;
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
