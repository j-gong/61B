package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static int WIDTH = 80;
    private static int HEIGHT = 30;
    private static TETile[][] WORLD;
    private static KeyInput keyinput;
    private static Screen screen;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        /*
        keyinput = new KeyInput(this);
        long seed = Long.parseLong(123457);
        screen = new Screen(WIDTH, HEIGHT, seed);
        */
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

        int seed = (int) Long.parseLong(input.replaceAll("[\\D]", ""));
        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map(WORLD, seed, HEIGHT, WIDTH);
        WORLD = map.makeMap();
        return WORLD;
    }



}