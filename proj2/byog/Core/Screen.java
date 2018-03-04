package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;
import java.util.Random;

public class Screen {
     int width;
     int height;
     boolean gameover = false;
     Game game;

    //Screen might need to take in a seed?
    Screen(int width, int height, Game game) {
        this.game = game;
        this.width = width;
        this.height = height;

        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
       // key = passed;
    }

    void MainMenu() {
        Font font = new Font("Monaco", Font.BOLD, 64);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(width/2, height/2 + height/5, "Title of Game");
        Font smallerFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallerFont);
        StdDraw.text(width/2, height/2 - 2, "New Game  (N)");
        StdDraw.text(width/2, height/2 - 5, "Load Game  (L)");
        StdDraw.text(width/2, height/2 - 8, "Quit  (Q)");
        StdDraw.show();
    }

    //will run this method while !gameover
    void drawHUD(){
            /*
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);*/
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.line(0, height -12 , width, height - 12);
            //show below depends on whether the next while loops stays
            StdDraw.show();

        //might need to move this while loop somewhere else
        /*while (!gameover) {
            StdDraw.text(width / 5, height * 0.8, "" + mousepoint());
            StdDraw.show();
        }*/
    }

    void fillHUD() {

        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.text(this.width - this.width/5, this.height - 9, this.mousepoint());
        StdDraw.show();
    }

    String mousepoint() {
        while(!gameover) {
            //TODO scale is wrong. on mine. x is positioned 2 to left
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY();
            if (game.WORLD[x][y].equals(Tileset.WALL)) {
                return "Wall" + Integer.toString(x) + Integer.toString(y);
            } else if (game.WORLD[x][y].equals(Tileset.PLAYER)) {
                return "Dats you" + Integer.toString(x) + Integer.toString(y);
            } else if (game.WORLD[x][y].equals(Tileset.FLOOR)) {
                return "Floor" + Integer.toString(x) + Integer.toString(y);
            } else if (game.WORLD[x][y].equals(Tileset.NOTHING)) {
                return " " + Integer.toString(x) + Integer.toString(y);
            }
        }
        return " ";
    }

    void nightTime() {
        //TODO: make all the tiles darker shades
    }
}
