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
     Random rand;
     Game game;
     boolean gameover = false;
     Map key;

    //Screen might need to take in a seed?
    public Screen(int width, int height) {
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
        //if (!gameover) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.line(0, height - 2, width, height - 2);
            //show below depends on whether the next while loops stays
            StdDraw.show();
       // }
        //might need to move this while loop somewhere else
        /*while (!gameover) {
            StdDraw.text(width / 5, height * 0.8, "" + mousepoint());
            StdDraw.show();
        }*/
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
                return "Floor";
            }
            else if (layout[x][y].equals(Tileset.NOTHING)) {
                return "This should be blank, but filler for now";
            }
        }
        return "This is just a filler";
    }

}
