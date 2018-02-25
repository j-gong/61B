package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static int WIDTH = 80;
    private static int HEIGHT = 30;

    private static TETile[][] WORLD;

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


        int seed = (int) Long.parseLong(input.replaceAll("[\\D]", ""));

        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map(WORLD, seed, HEIGHT, WIDTH);
        WORLD = map.makeMap();

        return WORLD;
    }

    @Test
    public void testGeneration() {
        TETile[][] A = playWithInputString("n5197880843569031643s");
        TETile[][] B = playWithInputString("n5197880843569031643s");

        /*assertArrayEquals(A, B); */


        /*TETile[][]  */ //A = playWithInputString("n5197880s");
        /* TETile[][] */// B = playWithInputString("n5197880s");


        assertArrayEquals(A, B);



    }

    @Test
    public void getxgety() {
        TETile[][] area = new TETile[4][7];
        Location place =  new Location(4, 7);
        TETile[][] motherfucker = new TETile[place.getxPos()][place.getyPos()];
        assertArrayEquals(area, motherfucker);

        TETile[][] volume = new TETile[7][3];
        Location location =  new Location(4, 7);
        TETile[][] fatherfucker = new TETile[location.getxPos()][location.getyPos()];
        assertNotEquals(volume, fatherfucker);

    }

    @Test
    public void increment() {
        Location place = new Location(4, 6);
        place.incrementXPos(1);
        place.incrementYPos(1);
        Location place1 = new Location(5, 7);
        assertEquals(place.getxPos(), place1.getxPos());

        Location place2 = new Location(6, 8);
        place2.incrementYPos(-1);
        place2.incrementXPos(-1);
        assertEquals(place2.getxPos(), place1.getxPos());
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        //Room comes back and bites into the old room -> what stoppers do we have for room?
        //int seed = 513245;
        int seed = 24573;
        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map(WORLD, seed, HEIGHT, WIDTH);
        WORLD = map.makeMap();

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
