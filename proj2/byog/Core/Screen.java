package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;
import java.util.Random;

public class Screen {
    private int width;
    private int height;
    private Random rand;
    private Game game;
    private boolean gameover;
    private Map key;

    public Screen(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public void MainMenu() {
        Font font = new Font("Monaco", Font.BOLD, 64);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(width/2, height/2 + height/5, "Title of Game");
        Font smallerFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallerFont);
        StdDraw.text(width/2, height/2, "New Game  (N)");
        StdDraw.text(width/2, height/2 - 4, "Load Game  (L)");
        StdDraw.text(width/2, height/2 - 8, "Quit  (Q)");
        StdDraw.show();
    }

    //will run this method while !gameover
    public void drawHUD(){
        StdDraw.rectangle(width/2, height * 0.88, width/2, height * 0.1);
        Font font = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(width/5, height * 0.95, "" + mousepoint());
        StdDraw.show();

    }

    public String mousepoint() {
        //want to see what tile is under the mouse
        //may want to include a delay? otherwise will continually update
        //like if mouse point stays the same for 1 second, then show?

        TETile[][] layout = key.LAYOUT;
        while(!gameover) {
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY();
            if (layout[x][y].equals(Tileset.WALL)) {
                return "Wall";
            }
            else if (layout[x][y].equals(Tileset.PLAYER)) {
                return "Dats you";
            }
            else if (layout[x][y].equals(Tileset.FLOOR)) {
                return "The floor needs sweeping";
            }
            else if (layout[x][y].equals(Tileset.NOTHING)) {
                return "The cold dark void stares back at you";
            }
        }
        return "This is just a filler";
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playWithInputString("24573");
        Screen test = new Screen(64, 64, 123);
        test.MainMenu();
        test.drawHUD();

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
