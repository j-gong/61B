package byog.Core;

import byog.TileEngine.TETile;

import java.awt.Color;
import java.io.Serializable;

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

 class Tileset implements Serializable {
     static TETile PLAYER = new TETile('@', Color.white, Color.black, "player");
     static  TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
     static  TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
     static  TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
     static  TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
     static  TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
     static  TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
     static  TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
     static  TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
     static  TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
     static  TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
     static  TETile TREE = new TETile('♠', Color.green, Color.black, "tree");


    //TODO: add new Tiles for different blocks
}


