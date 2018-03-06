package byog.TileEngine;

import java.awt.Color;

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

public class Tileset {
    public static TETile PLAYER = new TETile('@', Color.white, Color.black, "player");
    public static  TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static  TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static  TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static  TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static  TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static  TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static  TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static  TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static  TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static  TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static  TETile TREE = new TETile('♠', Color.green, Color.black, "tree");


    //TODO: add new Tiles for different blocks
}


