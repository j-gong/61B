package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.Random;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    static final int WIDTH = 84;
    static final int HEIGHT = 42;
    TETile[][] WORLD;
    int seed;

    private KeyInput key;
    Screen screen;

    int sunlight = 200;
    Player robocop;
    Antagonist[] criminals;
    Tools[] items;
    Tools[] weapons;

    boolean gameover = false;
    boolean win = false;
    int crimsleft;
    Random r;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        startGame();

        if (key.newgame) {
            ter.initialize(WIDTH, HEIGHT);
            screen.intro();
            ter.renderFrame(WORLD);
        }
        else {
            //screen = new Screen(90, 50, this);
            ter.initialize(WIDTH, HEIGHT);
            //TETile[][] g = key.loadworld();
            Game g = key.loadworld();
            this.robocop = g.robocop;
            this.criminals = g.criminals;
            this.items = g.items;
            this.sunlight = g.sunlight;
            this.screen = g.screen;
            this.ter = g.ter;
            this.gameover = g.gameover;
            this.seed = g.seed;
            this.crimsleft = g.crimsleft;
            this.WORLD = g.WORLD;
            this.r = g.r;
            this.win = g.win;
            ter.renderFrame(WORLD);
        }
        readKey();

        triggerGameOver();

    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {

        //ter.initialize(WIDTH, HEIGHT, 0, -2);
        seed = (int) Long.parseLong(input.replaceAll("[\\D]", ""));
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        r = new Random(seed);
        Map map = new Map(world, seed, HEIGHT, WIDTH);
        world = map.makeMap();
        return world;
    }

    private void startGame() {
        screen = new Screen(90, 50, this);
        screen.MainMenu();

        key = new KeyInput(this, screen);
        String input = key.readSeed();
        if (key.newgame) {

            WORLD = playWithInputString(input);

            createObjects();
        }
    }


    private void createObjects(){

        robocop = new Player(0, 0, this);
        criminals = new Antagonist[r.nextInt(5) + 4];
        items = new Tools[r.nextInt(5) + 8];
        weapons = new Tools[4];

        crimsleft = criminals.length;

        for (int i = 0; i < criminals.length; i += 1) {
            Antagonist badguy = new Antagonist(this);
            criminals[i] = badguy;
        }


        for (int i = 0; i < items.length; i += 1) {
            Nrgy addNrgy = new Nrgy(this);
            items[i] = addNrgy;

        }

        //TODO: add 2 types of weapons -> projectile, stun, speed
        for (int i = 0; i < 2; i += 1) {
            Projectile addproj = new Projectile(this);
            items[i] = addproj;
        }


    }

    private void readKey() {
        screen.drawHUD(); //TODO: fix the offset thing
        screen.fillHUD();
        String input = "";
        key.keyPressed(input);

        while (!gameover) {

            screen.showMousePoint();

            boolean update = key.keystrokeReader();
            if (update) {
                updateGame();
                if (!gameover) {
                    ter.renderFrame(WORLD);
                    screen.fillHUD();
                }
            }
        }
    }

    private void updateGame() {

        for (Antagonist criminal : criminals) {
            criminal.aiMove();
            if (sunlight < 1) {
                criminal.aiMove();
            }
        }

        robocop.drain(1);
        sunlight -= 1;

        if (sunlight < 1) {
            nightTime();
        }

        if (robocop.energy < 1) {
            gameover = true;
        }

        if (crimsleft < 1) {
            win = true;
            gameover = true;
        }
    }

    private void nightTime () {
        screen.darken();
        Antagonist.damage = 5;
    }

    private void triggerGameOver() {
        ter.renderFrame(WORLD);
        if (win) {
            screen.win();
        } else {
            screen.gameover();
        }
    }

    public static void main(String[] args) {

       Game game = new Game();
       game.playWithKeyboard();
    }
}
