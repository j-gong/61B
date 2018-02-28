package byog.Core;

import java.awt.*;
import java.util.ArrayDeque;
import edu.princeton.cs.introcs.StdDraw;

//@Source. Code to take key inputs from youtube video
public class KeyInput {

    private Screen screen;
    private Player p;
    private Game game;
    private ArrayDeque<Character> history = new ArrayDeque<>();

    public KeyInput(Game game) {
        this.game = game;
    }

    //Reads what key is pressed
    public String readKey(int n) {
        StringBuilder sb = new StringBuilder(n);
        char d;
        //length should always be 1, but keeping for potential future options
        int newLength = n;
        while (newLength > 0) {
            if (StdDraw.hasNextKeyTyped()) {
                d = StdDraw.nextKeyTyped();
                history.addLast(d);
                sb.append(d);
                StdDraw.text(16, 16, sb.toString());
                newLength -= 1;
            }
        }
        return sb.toString();
    }

    //takes what readKey does and processes it
    public void keyPressed (int input){
        String key = readKey(input);

        if (key.equals("w") || key.equals("W")) {
            p.setY(p.getY() + 1);
        } else if (key.equals("a") || key.equals("A")) {
            p.setX(p.getX() - 1);
        } else if (key.equals("s") || key.equals("S")) {
            p.setY(p.getY() - 1);
        } else if (key.equals("d") || key.equals("D")) {
            p.setX(p.getX() + 1);
        }
    }

    //reads whether user starts new game, load, or quit
    public void mainMenuKey (int input) {
        String key = readKey(input);

        if (key.equals("n") || key.equals("N")) {
            StdDraw.clear();
            Font font = new Font("Monaco", Font.BOLD, 64);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(font);
            StdDraw.text(screen.getWidth()/2, screen.getHeight()/2 + screen.getHeight()/5, "New Game");
            StdDraw.text(screen.getWidth()/2, screen.getHeight()/2, "Please enter a seed. Press s to start.");
            //whatever code to start new game is
            //user prompted to enter random seed
            //when done entering seed, press S to load
        }
        else if (key.equals("s") || key.equals("S")) {
            //loads the game
            //must take the world back to exactly the same stage it was before
            //need to track steps. will have to run the seed with the exact same steps
        }
        else if (key.equals("q") || key.equals("Q")) {
            //quits the game
            //Must immediately quit and save
        }
    }
}

