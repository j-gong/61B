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

    Game game;
    ArrayDeque<String> history = new ArrayDeque<String>(); //when a game is loaded, history needs to include all
    private Screen menu;
    private TETile[][] layout;
    private Player p;

    public KeyInput(Game game, Screen open) {
        this.game = game;
        this.menu = open;

    }

    boolean keystrokeReader() {
        if (!StdDraw.hasNextKeyTyped()) {
            return false;
        } else if (StdDraw.hasNextKeyTyped()) {
            char stroke = StdDraw.nextKeyTyped();
            keyPressed(String.valueOf(stroke));
            return true;
        }
        return true;
    }

    void keyPressed(String input) {

        this.layout = game.WORLD;
        this.p = game.robocop;

        if (input.equals("w")) {
            p.move(layout, 0, 1);
        } else if (input.equals("a")) {
            p.move(layout, -1, 0);
        } else if (input.equals("s")) {
            p.move(layout, 0, -1);
        } else if (input.equals("d")) {
            p.move(layout, 1, 0);
        } else if (input.equals(" ")){
            p.weapon.use();
            game.screen.use(p.weapon.name); //TODO: fix screen.use so it actually shows up
        } else if (input.equals("q") || input.equals("W")) {
            menu.gameover = true;
            saveWorld(this.game);
            System.exit(0);
        }
    }

    String readSeed() { //gets the game from the input string

            String input = "";
            String input2 = "";
            keyPressedSeed(input);
            while (history.isEmpty() || !history.peekLast().equals(("s"))) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                input += String.valueOf(key);
                input2 = String.valueOf(key);
                history.addLast(String.valueOf(key));
                keyPressedSeed(input2);
            }
        }
        return input;
    }

    private void keyPressedSeed(String input) { //takes in whats pressed, when an n or s is pressed, game starts

        if (input.equals("n") || input.equals("N")) {
            StdDraw.clear(Color.BLACK);
            Font font = new Font("Monaco", Font.BOLD, 64);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(font);
            StdDraw.text(menu.width / 2, menu.height / 2 + menu.height / 5, "New Game");
            StdDraw.text(menu.width / 2, menu.height / 2, "Please enter a seed. Press s to start.");
            StdDraw.show();
        }
        if (input.equals("l") || input.equals("L")) {
            Game g = loadworld();
            //supposedly this is all it needs
            //I want to be able to save the game g tho. perhaps this.game is all I need?
        }
        if (input.equals("s") || input.equals("S")) {
            if (!history.peekFirst().equals("n") || history.size() == 2) {
                Font smallerFont = new Font("Monaco", Font.BOLD, 30);
                StdDraw.setFont(smallerFont);
                StdDraw.clear(Color.BLACK);
                StdDraw.text(menu.width / 2, menu.height / 2, "Press n to start a new game");
                StdDraw.text(menu.width / 2, menu.height / 2 - 4, "l to load a saved game");
                StdDraw.text(menu.width / 2, menu.height / 2 - 8, "or q to quit");
                StdDraw.show();
                history.clear();
            }
        }
        //add a quit method "q"
    }


    //Is supposed to pull a file called world.txt and load it. makes one if doesn't exist
    Game loadworld() {
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
    void saveWorld(Game g) {
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
