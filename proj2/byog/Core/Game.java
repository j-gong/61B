package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.lab5.HexWorld;
import java.awt.Toolkit;
import java.awt.Dimension;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static int WIDTH = 80;
    private static int HEIGHT = 45;
    private static TETile[][] WORLD;
    private static KeyInput keys;
    private static Screen screen;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        KeyInput key1 = new KeyInput(this);
        key1.StartGame();
        /*ter.initialize(WIDTH, HEIGHT, 0, -2);
        //this seed doesn't parse the letters
        int seed = Integer.parseInt(key1.readSeed());
        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map(WORLD, seed, HEIGHT, WIDTH);

        KeyInput key2 = new KeyInput(this, map);*/
        /*Screen screen = new Screen(WIDTH, HEIGHT);
        screen.MainMenu();
        screen.drawHUD();
        //key2.readKey();*/

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
        int seed = (int) Long.parseLong(input.replaceAll("[\\D]", ""));
        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map(WORLD, seed, HEIGHT, WIDTH);
        WORLD = map.makeMap();
        return WORLD;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        //want to initialize with 0 offset width and -2? offset on height
        ter.initialize(WIDTusH, HEIGHT, 0, -2);
        int seed = 24573;
        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map(WORLD, seed, HEIGHT, WIDTH);
        WORLD = map.makeMap();
        ter.renderFrame(WORLD);

        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        System.out.println(width); //1280
        System.out.println(height); //720 */

       // Game game = new Game();
       // game.playWithInputString();
    }
}
