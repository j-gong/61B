package byog.Core;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.security.Key;
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
    boolean newgame;

    public KeyInput(Game game, Screen open) {
        this.game = game;
        this.menu = open;

    }

    boolean keystrokeReader() {
        if (game.inputString) {
            keyPressed(game.deliverNext());
            return true;
        }
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
            System.out.println(input);
            p.move(layout, 0, 1);
        } else if (input.equals("a")) {
            System.out.println(input);
            p.move(layout, - 1, 0);
        } else if (input.equals("s")) {
            System.out.println(input);
            p.move(layout, 0, -1);
        } else if (input.equals("d")) {
            System.out.println(input);
            p.move(layout, 1, 0);
        }
        else if (input.equals(" ")){
            p.weapon.use();
            if (game.inputString) {
                game.screen.screenUse(p.weapon.name);
            }
        } else if (input.equals("q") || input.equals("W")) {
            if (game.inputString) {
                menu.gameover = true;
            }
            saveWorld(this.game);
            if (!game.inputString) {
                System.exit(0);
            }
        } else if (input.equals("l")) {
            if (!game.inputString) {
                //loadworld();

            }
            newgame = false;
        }
    }

    String readSeed() { //gets the game from the input string

            String input = "";
            String input2 = "";
            keyPressedSeed(input);
            //works if i don't call !history.peekLast().equals(("l"))
            while (history.isEmpty() || !history.peekLast().equals(("s")) && newgame) {
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
            newgame = true;
            StdDraw.clear(Color.BLACK);
            Font font = new Font("Monaco", Font.BOLD, 64);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(font);
            StdDraw.text(menu.width / 2, menu.height / 2 + menu.height / 5, "New Game");
            StdDraw.text(menu.width / 2, menu.height / 2, "Please enter a seed. Press s to start.");
            StdDraw.show();
        }

        if (input.equals("l") || input.equals("L")) {
            //Game g = loadworld();
            newgame = false;

        }

        if (input.equals("s")) {
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
                fs.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
            } catch (IOException e) {
                System.out.println(e);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
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
            fs.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
