package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static int WIDTH = 80;
    public static int HEIGHT = 30;

    public static TETile[][] WORLD;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
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


        int seed =(int) Long.parseLong(input.replaceAll("[\\D]", ""));

        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map();
        WORLD = map.makeMap(WORLD, seed);

        return WORLD;
    }

    @Test
    public void testGeneration() {
        TETile[][] A = playWithInputString("n5197880843569031643s");
        TETile[][] B = playWithInputString("n5197880843569031643s");

        /*assertArrayEquals(A, B); */


        /*TETile[][]  */ A = playWithInputString("n5197880s");
        /* TETile[][] */ B = playWithInputString("n5197880s");


        assertArrayEquals(A, B);



    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        Random rand = new Random();


        //Room comes back and bites into the old room -> what stoppers do we have for room?
        //int seed = 513245;
        int seed = 2384573;



        WORLD = new TETile[WIDTH][HEIGHT];

        Map map = new Map();
        WORLD = map.makeMap(WORLD, seed);

        ter.renderFrame(WORLD);


        /*TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        int seed = 942582342;
        WORLD = new TETile[WIDTH][HEIGHT];
        Map.makeMap(WORLD, seed);
        ter.renderFrame(WORLD);*/
        /*String cool = "4awefj3242u3kljlk23j423";//Integer.parseInt(cool.replaceAll("[\\D]", ""));
        System.out.println(Long.parseLong(cool.replaceAll("[\\D]", "")));*/
    }

}