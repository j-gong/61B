package byog.Core;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class KeyInput implements Serializable{

    private Screen screen;
    private Player p;
    private Game game;
    private ArrayDeque<String> history = new ArrayDeque<>();
    private Map key;

    public KeyInput(Game game, Map passed) {
        this.game = game;
        key = passed;
    }

    //Reads what key is pressed
    public String readKey() {
        String input = "";
        keyPressed(input);
        while (!screen.getGameover()) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                input += String.valueOf(key);
                history.addLast(String.valueOf(key));
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
            screen.setGameover(true);
            saveWorld(this.game);
            System.exit(0);
            //quit the game
        }

        //main menu keys
        else if (input.equals("n") || input.equals("N")) {
            StdDraw.clear();
            Font font = new Font("Monaco", Font.BOLD, 64);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(font);
            StdDraw.text(screen.getWidth()/2, screen.getHeight()/2 + screen.getHeight()/5, "New Game");
            StdDraw.text(screen.getWidth()/2, screen.getHeight()/2, "Please enter a seed. Press s to start.");
            StdDraw.show();
            if (input.equals("s")|| input.equals("S")) {
                if (history.getFirst().equals("n") || history.getFirst().equals("N")) {
                    StdDraw.text(screen.getWidth()/2, screen.getHeight()/2, "Please enter a seed");
                    StdDraw.show();
                    history.removeLast();
                }
                else {
                    //one of these two functions
                    Game g = loadworld();
                    //has to be one or the other
                    game.playWithInputString(input);
                }
            }
        }
        else if (input.equals("l") || input.equals("L")) {
            loadworld();
            //supposedly this is all it needs

            //probs a version of playwithinput string, but updated a little
            //needs to be able to take in the world portion, and be able to do all the steps
        }
    }

    //Is supposed to pull a file called world.txt and load it. makes one if doesn't exist
    public static Game loadworld() {
        //@Source from SaveDemo
        File f = new File("./world.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Game loadWorld = (Game) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return new Game();
    }

    //Is supposed to save it. hope it works. i don't see how it saves keys
    public static void saveWorld(Game g) {
        //@source from SaveDemo
        File f = new File("./world.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(g);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

}

