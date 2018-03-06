package byog.Core;

import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;
import java.io.Serializable;

public class Screen implements Serializable {
     int width;
     int height;
     boolean gameover;
     private Game game;

     String prevAction;

    Screen() {
    }

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
        StdDraw.text(game.WIDTH, game.HEIGHT - 3, "Some teenage delinquents have been");
        StdDraw.text(game.WIDTH, game.HEIGHT - 5, "defacing public property. Apprehend them!");
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.show();
    }

    //will run this method while !gameover
    void drawHUD(){
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.show();

    }

    void fillHUD() {


        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(game.WIDTH / 12, game.HEIGHT - 9, "energy: " + game.robocop.energy);
        StdDraw.text(game.WIDTH / 12, game.HEIGHT - 14,"sunlight: " + game.sunlight);
        StdDraw.text(game.WIDTH / 12, game.HEIGHT - 19,"weapon: " + game.robocop.weapon.name);
        StdDraw.text(game.WIDTH / 12, game.HEIGHT - 21, "uses: " + game.robocop.weapon.uses);
        StdDraw.show();

        showPrev();
        prevAction = " ";
        showVandals();

        StdDraw.show();

    }

    private void showVandals() {
        for (int i = 0; i < game.criminals.length; i += 1) {
            if (!game.criminals[i].caught) {
                StdDraw.text(game.WIDTH / 12, 17 - (i * 2), "Vandal" + i + ": ▲");
            } else {
                StdDraw.text(game.WIDTH / 12, 17 - (i * 2), "Vandal" + i + ": ❀");
            }
            StdDraw.show();
        }
    }

    void showMousePoint() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledEllipse(game.WIDTH / 12, game.HEIGHT - 4, 6, 4);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(game.WIDTH / 12, game.HEIGHT - 4, this.mousepoint());
        StdDraw.show(); }

    private String mousepoint() {
            int x = (int) StdDraw.mouseX() - 12;
            int y = (int) StdDraw.mouseY();
            try {
                TETile check = game.WORLD[x][y];
                if (check.description().equals("wall")) {
                    return "Wall" + Integer.toString(x) + Integer.toString(y);
                } else if (check.description().equals("player")) {
                    return "Dats you" + Integer.toString(x) + Integer.toString(y);
                } else if (check.description().equals("floor")) {
                    return "Floor" + Integer.toString(x) + Integer.toString(y);
                } else if (check.description().equals("nothing")) {
                    return " " + Integer.toString(x) + Integer.toString(y);
                } else if (check.description().equals("mountain")) {
                    return "vandal " + Integer.toString(x) + Integer.toString(y);
                } else if (check.description().equals("grass")) {
                    return "energy pack " + Integer.toString(x) + Integer.toString(y);
                } else if (check.description().equals("sand")) {
                    return "sock launcher " + Integer.toString(x) + Integer.toString(y);
                } else if (check.description().equals("water")) {
                    return "roller blades " + Integer.toString(x) + Integer.toString(y);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print(" ");
            }
        return " " + Integer.toString(x) + Integer.toString(y);
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

    void screenUse(String weaponName) {
        prevAction = weaponName + " used";
    }

    private void showPrev() {
        if (prevAction == null) {
            prevAction = " ";
        }
        StdDraw.setPenColor(Color.CYAN);
        StdDraw.text(game.WIDTH / 12, game.HEIGHT - 27, prevAction);
        StdDraw.setPenColor(Color.YELLOW);
    }

    /**
     * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
     * the code.
     *
     * You are free to (and encouraged to) create and add your own tiles to this file. This file will
     * be turned in with the rest of your code.
     *
     * Ex:
     *      world[x][y] = Tileset.FLOOR;
     *
     * The style checker may crash when you try to style check this file due to use of unicode
     * characters. This is OK.
     */

     public static class Tileset implements Serializable {
         public static TETile PLAYER = new TETile('@', Color.white, Color.black, "player");
         public static TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
                "wall");
         public static  TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
                "floor");
         public static final  TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
         public static final  TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
         public static final  TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
         public static final  TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
         public static final  TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
                "locked door");
         public static final  TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
                "unlocked door");
         public static final  TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
         public static final  TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
         public static final  TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    }
}
