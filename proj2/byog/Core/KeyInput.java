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

    Screen screen;
    Player p;
    Game game;
    ArrayDeque<String> history = new ArrayDeque<String>(); //when a game is loaded, history needs to include all
    Map key;
    TETile[][] layout;

    TERenderer ter = new TERenderer();

    public KeyInput(Game game) {
        this.game = game;

        //key = passed;
    }

    public KeyInput(Game game, Map passed) {
        this.game = game;
        key = passed;
    }

    public void StartGame() {
        screen = new Screen(100, 80);
        screen.MainMenu();
        //Player dude = new Player(0, 0, game);
        layout = game.playWithInputString(readSeed());
        //dude.place();
    }

    //Reads what key is pressed
    public String readKey() {
        screen.drawHUD();
        String input = "";
        keyPressed(input);
        while (!screen.gameover) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                input = String.valueOf(key);
                history.addLast(String.valueOf(key));
                keyPressed(input);
            }
        }
        return input;
    }

    public String readSeed() { //gets the game from the input string
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

    public void keyPressedSeed(String input) { //takes in whats pressed, when an n or s is pressed, game starts

        if (input.equals("n") || input.equals("N")) {
            StdDraw.clear(Color.BLACK);
            Font font = new Font("Monaco", Font.BOLD, 64);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(font);
            StdDraw.text(screen.width / 2, screen.height / 2 + screen.height / 5, "New Game");
            StdDraw.text(screen.width / 2, screen.height / 2, "Please enter a seed. Press s to start.");
            StdDraw.show();
        }
        if (input.equals("l") || input.equals("L")) {
            Game g = loadworld();
            //supposedly this is all it needs
            //I want to be able to save the game g tho. perhaps this.game is all I need?
        }
        if (input.equals("s") || input.equals("S")) {
            if (history.size() == 2) {
                StdDraw.clear(Color.BLACK);
                StdDraw.text(screen.width / 2, screen.height / 2, "Please enter a seed");
                StdDraw.show();
                history.removeLast();
            }
        }
        //add a quit method "q"
    }


    //takes what readKey does and processes it
    public void keyPressed(String input){
        TETile[][] layout = this.layout;

        if (input.equals("w") || input.equals("W")) {
            if (!layout[p.x][p.y + 1].equals(Tileset.WALL)) {
                layout[p.x][p.y] = Tileset.FLOOR;
                layout[p.x][p.y + 1] = Tileset.PLAYER;
                p.y += 1;
            }
        } else if (input.equals("a") || input.equals("A")) {
            if (!layout[p.y - 1][p.y].equals(Tileset.WALL)) {
                layout[p.x][p.y] = Tileset.FLOOR;
                layout[p.x - 1][p.y] = Tileset.PLAYER;
                p.x -= 1;
            }
        } else if (input.equals("s") || input.equals("S")) {
            if (!layout[p.x][p.y - 1].equals(Tileset.WALL)) {
                layout[p.x][p.y] = Tileset.FLOOR;
                layout[p.x][p.y - 1] = Tileset.PLAYER;
                p.y -= 1;
            }
        } else if (input.equals("d") || input.equals("D")) {
            if (!layout[p.x + 1][p.y].equals(Tileset.WALL)) {
                layout[p.x][p.y] = Tileset.FLOOR;
                layout[p.x + 1][p.y] = Tileset.PLAYER;
                p.x += 1;
            }
        }
        else if (input.equals("q") || input.equals("Q")) {
            screen.gameover = true;
            saveWorld(this.game);
            System.exit(0);
            //quit the game
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

