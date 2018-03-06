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

     String prevAction;

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
        StdDraw.text(10, game.HEIGHT - 3, "Some teenage delinquents have been");
        StdDraw.text(13, game.HEIGHT - 5, "defacing public property. Apprehend them!");
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.show();
    }

    //will run this method while !gameover
    void drawHUD(){
            /*
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);*/
            StdDraw.setPenColor(Color.YELLOW);
            //StdDraw.line(0, height -12 , width, height - 12);
            //show below depends on whether the next while loops stays
            StdDraw.show();

    }

    void fillHUD() {


        StdDraw.setPenColor(Color.YELLOW);
        //StdDraw.text(game.WIDTH - this.width/5, game.HEIGHT - 9, this.mousepoint());
        StdDraw.text(game.WIDTH - game.WIDTH / 7, game.HEIGHT - 9, "energy: " + game.robocop.energy);
        StdDraw.text(game.WIDTH - game.WIDTH / 7, game.HEIGHT - 14,"Minutes before night: " + game.sunlight);
        StdDraw.text(game.WIDTH - game.WIDTH / 7, game.HEIGHT - 19,"Weapon: " + game.robocop.weapon.name + ". Uses: " + game.robocop.weapon.uses);
        StdDraw.show();

        showPrev();
        prevAction = " ";
        showVandals();

        StdDraw.show();

    }

    private void showVandals() {
        for (int i = 0; i < game.criminals.length; i += 1) {
            if (!game.criminals[i].caught) {
                StdDraw.text(game.WIDTH - game.WIDTH / 7, 17 - (i * 2), "Vandal" + i + ": ▲");
            } else {
                StdDraw.text(game.WIDTH - game.WIDTH / 7, 17 - (i * 2), "Vandal" + i + ": ❀");
            }
            StdDraw.show();
        }
    }

    void showMousePoint() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(game.WIDTH - game.WIDTH / 7, game.HEIGHT - 4, 4);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(game.WIDTH - game.WIDTH / 7, game.HEIGHT - 4, this.mousepoint());
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
        StdDraw.text(game.WIDTH / 2, game.HEIGHT / 2, "GameOver");
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.setFont(saved);
        StdDraw.show();
    }

    void win() {
        StdDraw.setPenColor(Color.CYAN);
        Font saved = StdDraw.getFont();
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 64));
        StdDraw.text(game.WIDTH / 2, Game.HEIGHT / 2, "You Win");
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.setFont(saved);
        StdDraw.show();
    }

    void darken() {
        prevAction = "Bad things happen after dark";
        Tileset.PLAYER = new TETile('@', Color.GRAY, Color.black, "player");
        Tileset.WALL = new TETile('#', new Color(142, 57, 57), new Color(56, 52, 52),
                "wall");
        Tileset.FLOOR = new TETile('·', new Color(62, 117, 62), Color.black,
                "floor");
        for (int x = 0; x < game.WIDTH; x += 1) {
            for (int y = 0; y < game.HEIGHT; y += 1) {
                if (game.WORLD[x][y].description().equals("floor")) {
                    game.WORLD[x][y] = Tileset.FLOOR;
                } else if (game.WORLD[x][y].description().equals("wall")) {
                    game.WORLD[x][y] = Tileset.WALL;
                } else if (game.WORLD[x][y].description().equals("player")) {
                    game.WORLD[x][y] = Tileset.PLAYER;
                }
            }
        }
    }

        //TODO: make all the tiles darker shades -> make the message show up as well, getting rendered over

    void screenUse(String weaponName) {
        prevAction = weaponName + " used";
    }

    private void showPrev() {
        if (prevAction == null) {
            prevAction = " ";
        }
        StdDraw.setPenColor(Color.CYAN);
        StdDraw.text(game.WIDTH - game.WIDTH / 7, game.HEIGHT - 22, prevAction);
        StdDraw.setPenColor(Color.YELLOW);
    }

}
