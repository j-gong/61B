package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Toolkit;
import java.awt.Dimension;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    static final int WIDTH = 84;
    static final int HEIGHT = 42;
    TETile[][] WORLD;
    int seed;

    private KeyInput key;
    private Screen screen;

    int sunlight = 40; //TODO: implement sunlight everywhere, make things slightly darker when moving under
    Player robocop;
    Antagonist[] criminals = new Antagonist[4];
    boolean gameover = false;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        startGame();

        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(WORLD);
        readKey();


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
        Map map = new Map(world, seed, HEIGHT, WIDTH);
        world = map.makeMap();
        return world;
    }

    private void startGame() {
        screen = new Screen(90, 50, this);
        screen.MainMenu();

        key = new KeyInput(this, screen);
        String input = key.readSeed();

        WORLD = playWithInputString(input);

        createObjects();
    }


    private void createObjects(){

        robocop = new Player(0, 0, this);

        for (int i = 5; i > 0; i -= 1) {
            Antagonist badguy = new Antagonist(this);
            criminals[i] = badguy;
        }

    }

    private void readKey() {
        screen.drawHUD();
        String input = "";
        key.keyPressed(input);

        while (!gameover) {

            ter.renderFrame(WORLD);
            screen.fillHUD();

            boolean update = key.keystrokeReader();
            if (update) {
                updateGame();
            }
        }
    }

    private void updateGame() {
        //TODO: sunglight, move randos, place objects
        for (Antagonist c : criminals) {
            c.randomMove();
        }
    }



    public static void main(String[] args) {
        /*TERenderer ter = new TERenderer();
        //want to initialize with 0 offset width and -2? offset on height
        ter.initialize(WIDTH, HEIGHT);
        int seed = 1234;
        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map(WORLD, seed, HEIGHT, WIDTH);
        WORLD = map.makeMap();
        ter.renderFrame(WORLD);*/


       Game game = new Game();
       game.playWithKeyboard();
    }
}
