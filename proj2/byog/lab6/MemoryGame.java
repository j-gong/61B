package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver = false;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        Random r = new Random(seed);
        MemoryGame game = new MemoryGame(40, 40, r);
        game.startGame();
    }

    public MemoryGame(int width, int height, Random r) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = r;
    }

    //@Source stackoverflow. generate string of specified number of characters
    public String generateRandomString(int n) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i += 1) {
            char c = CHARACTERS[random.nextInt(CHARACTERS.length)];
            sb.append(c);
        }
        //TODO: Generate random string of letters of length n
        return sb.toString();
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        Font font = new Font("Comic Sans", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.text(height/2, width/2, s);
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        /*while (!gameOver) {
            Font fontt = new Font("Comic Sans", Font.PLAIN, 16);
            StdDraw.setFont(fontt);
            StdDraw.text(0.5, 1.0, s);
            StdDraw.show();
        }*/

        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        StdDraw.clear();
        for (int i = 0; i < letters.length(); i += 1) {
            StdDraw.clear();
            String a_letter = Character.toString(letters.charAt(i));
            StdDraw.text(width/2, width/2, a_letter);
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.pause(500);
        }

        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public String solicitNCharsInput(int n) {
        StringBuilder sb = new StringBuilder(n);
        char d;
        int newLength = n;
        while (newLength > 0) {
            if (StdDraw.hasNextKeyTyped()) {
                d = StdDraw.nextKeyTyped();
                sb.append(d);
                StdDraw.text(16, 16, sb.toString());
                StdDraw.show();
                newLength -= 1;
            }
        }
        //TODO: Read n letters of player input
        return sb.toString();
    }

    public void startGame() {
        int round = 1;
        while (!gameOver) {
            String random = generateRandomString(round);
            drawFrame(random);
            flashSequence(random);
            String typed = solicitNCharsInput(round);
                if (!random.equals(typed)) {
                    StdDraw.clear();
                    StdDraw.text(0.5, 0.5, "Game Over! You made it to round:" + round);
                    gameOver = true;
                }
            round += 1;
        }

        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Game loop
    }

}
