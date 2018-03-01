package byog.Core;

import java.awt.*;
import java.util.ArrayDeque;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

//@Source. Code to take key inputs from youtube video
public class KeyInput {

    private Screen screen;
    private Player p;
    private Game game;
    private ArrayDeque<Character> history = new ArrayDeque<>();
    private Map key;

    public KeyInput(Game game) {
        this.game = game;
    }

    //Reads what key is pressed
    public String readKey(int n) {
        String input = "";
        keyPressed(input);
        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                input += String.valueOf(key);
                history.addLast(key);
            }
        }
        return input;
    }

    //takes what readKey does and processes it
    public void keyPressed (String input){
        TETile[][] layout = key.LAYOUT;
        if (input.equals("w") || input.equals("W")) {
            if (!layout[p.getX()][p.getY() + 1].equals(Tileset.WALL)) {
                p.setY(p.getY() + 1);
            }
        } else if (input.equals("a") || input.equals("A")) {
            if (!layout[p.getY() - 1][p.getY()].equals(Tileset.WALL)) {
                p.setX(p.getX() - 1);
            }
        } else if (input.equals("s") || input.equals("S")) {
            if (!layout[p.getX()][p.getY() - 1].equals(Tileset.WALL)) {
                p.setY(p.getY() - 1);
            }
        } else if (input.equals("d") || input.equals("D")) {
            if (!layout[p.getX() + 1][p.getY()].equals(Tileset.WALL)) {
                p.setX(p.getX() + 1);
            }
        }
        else if (input.equals("q") || input.equals("Q")) {
            //quit the game
        }
    }

    public String readMainMenuKey(int n) {
        String input = "";
        mainMenuKey(input);
        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                input += String.valueOf(key);
                history.addLast(key);
            }
        }
        return input;
    }

    //reads whether user starts new game, load, or quit
    public void mainMenuKey (String input) {

        if (input.equals("n") || input.equals("N")) {
            StdDraw.clear();
            Font font = new Font("Monaco", Font.BOLD, 64);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(font);
            StdDraw.text(screen.getWidth()/2, screen.getHeight()/2 + screen.getHeight()/5, "New Game");
            StdDraw.text(screen.getWidth()/2, screen.getHeight()/2, "Please enter a seed. Press s to start.");
            StdDraw.show();
            if (input.equals("s") || input.equals("S")) {
                game.playWithInputString(input);
            }
            //whatever code to start new game is
            //user prompted to enter random seed
            //when done entering seed, press S to load
        }
        else if (input.equals("l") || input.equals("L")) {
            //probs a version of playwithinput string, but updated a little
            //needs to be able to take in the world portion, and be able to do all the steps

            //loads the game
            //must take the world back to exactly the same stage it was before
            //need to track steps. will have to run the seed with the exact same steps
        }
        else if (input.equals("q") || input.equals("Q")) {
            //quits the game
            //Must immediately quit and save
        }
    }
}

