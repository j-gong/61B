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
     boolean gameover;
     private Game game;

    //Screen might need to take in a seed?
    Screen(int width, int height, Game game) {
        this.game = game;
        this.width = width;
        this.height = height;
        this.gameover = game.gameover;

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
        StdDraw.text(width/2, height/2 + height/5, "Robocop");
        Font smallerFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallerFont);
        StdDraw.text(width/2, height/2 - 2, "New Game  (N)");
        StdDraw.text(width/2, height/2 - 5, "Load Game  (L)");
        StdDraw.text(width/2, height/2 - 8, "Quit  (Q)");
        StdDraw.show();
    }

    void intro() {
        StdDraw.setPenColor(Color.CYAN);
        StdDraw.text(3, height - 4, "Some teenage delinquents have been defacing public property. Apprehend them!");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.show();
    }

    //will run this method while !gameover
    void drawHUD(){
            /*
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);*/
            StdDraw.setPenColor(Color.WHITE);
            //StdDraw.line(0, height -12 , width, height - 12);
            //show below depends on whether the next while loops stays
            StdDraw.show();

    }

    void fillHUD() {

        StdDraw.setPenColor(Color.YELLOW);
        //StdDraw.text(this.width - this.width/5, this.height - 9, this.mousepoint());
        StdDraw.text(this.width - this.width/5, this.height - 14, "energy: " + game.robocop.energy);
        StdDraw.text(this.width - this.width/5, this.height - 19,"Minutes before night: " + game.sunlight);
        StdDraw.show();

        showDefacers();

        StdDraw.show();

    }

    private void showDefacers() {
        for (int i = 0; i < game.criminals.length; i += 1) {
            if (!game.criminals[i].caught) {
                StdDraw.text(this.width - this.width / 5, 17 - (i * 2), "Defacer" + i + ": ▲");
            } else {
                StdDraw.text(this.width - this.width / 5, 17 - (i * 2), "Defacer" + i + ": ❀");
            }
            StdDraw.show();
        }
    }

    void showMousePoint() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(this.width - this.width/5, this.height - 9, 4);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(this.width - this.width/5, this.height - 9, this.mousepoint());
        StdDraw.show(); }

    private String mousepoint() {
        while(!gameover) {
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

        //TODO: inlcude other objects in mousepoint database
    }

    void gameover() {
        StdDraw.setPenColor(Color.CYAN);
        Font saved = StdDraw.getFont();
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 64));
        StdDraw.text(width / 2, height / 2, "GameOver");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(saved);
        StdDraw.show();
    }

    void win() {
        StdDraw.setPenColor(Color.CYAN);
        Font saved = StdDraw.getFont();
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 64));
        StdDraw.text(width / 2, height / 2, "You Win");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(saved);
        StdDraw.show();
    }

    void darken() {
        StdDraw.setPenColor(Color.CYAN);
        StdDraw.text(width / 2, height - 5, "Bad things happen after dark");
        //TODO: make all the tiles darker shades
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.show();
    }

}
