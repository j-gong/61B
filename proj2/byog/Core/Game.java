package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.Serializable;
import java.lang.Character;
import java.util.Random;
import java.util.ArrayDeque;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();

    /* Feel free to change the width and height. */
    static final int WIDTH = 84;
    static final int HEIGHT = 42;
    TETile[][] WORLD;
    int seed;

    private KeyInput key;
    Screen screen;

    int sunlight = 200;
    Player robocop;
    Antagonist[] criminals;
    Tools[] items;
    Tools[] weapons;

    boolean inputString;
    boolean gameover = false;
    private boolean win = false;
    int crimsleft;
    Random r;
    private ArrayDeque<String> keystring = new ArrayDeque<>();
    private boolean remaining;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        inputString = true;
        startGame();


        if (key.newgame) {
            ter.initialize(WIDTH, HEIGHT);
            screen.intro();
            ter.renderFrame(WORLD);
        }
        else {
            //screen = new Screen(90, 50, this);
            ter.initialize(WIDTH, HEIGHT);
            //TETile[][] g = key.loadworld();
            Game g = key.loadworld();
            this.robocop = g.robocop;
            this.criminals = g.criminals;
            this.items = g.items;
            this.sunlight = g.sunlight;
            this.screen = g.screen;
            this.ter = g.ter;
            this.gameover = g.gameover;
            this.seed = g.seed;
            this.crimsleft = g.crimsleft;
            this.WORLD = g.WORLD;
            this.r = g.r;
            this.win = g.win;
            ter.renderFrame(WORLD);
        }
        readKey();

        triggerGameOver();

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
        ter.initialize(WIDTH, HEIGHT);
        remaining = false;

        for (int i = 0; i < input.length(); i += 1) {
            char a = input.charAt(i);
            keystring.addLast(Character.toString(a));
        }
        keystring.removeFirst();
        String stringSeed = "";
        while (!keystring.peekFirst().equals("s")) {
            stringSeed += keystring.removeFirst();
        }

        keystring.removeFirst();
        int seed = (int) Long.parseLong(stringSeed);
        r = new Random(seed);

        screen = new Screen();
        key = new KeyInput(this, screen);

        ter.initialize(WIDTH, HEIGHT);
        WORLD = new TETile[WIDTH][HEIGHT];
        Map map = new Map(WORLD, seed, HEIGHT, WIDTH);
        WORLD = map.makeMap();
        createObjects();

        while (!gameover) {
            key.keystrokeReader();
            updateGame();
        }

        ter.renderFrame(WORLD);
        return WORLD;
    }
    
    String deliverNext() {
        if (!keystring.peekFirst().equals(":")) {
            key.saveWorld(this);
            System.exit(0);
        } else if (keystring.size() > 1) {
            String next = keystring.removeFirst();
            return next;
        }
        return null;
    }

    private void startGame() {
        screen = new Screen(90, 50, this);
        screen.MainMenu();

        key = new KeyInput(this, screen);
        String input = key.readSeed();
        seed = Integer.parseInt(input);
        if (key.newgame) {

            ter.initialize(WIDTH, HEIGHT);
            Map map = new Map(WORLD, seed, WIDTH, HEIGHT);
            WORLD = map.makeMap();
            ter.renderFrame(WORLD);

            createObjects();
        }
    }

    private void createObjects(){

        robocop = new Player(0, 0, this);
        criminals = new Antagonist[r.nextInt(5) + 4];
        items = new Tools[r.nextInt(5) + 8 + 4];
        weapons = new Tools[4];

        crimsleft = criminals.length;

        for (int i = 0; i < criminals.length; i += 1) {
            Antagonist badguy = new Antagonist(this);
            criminals[i] = badguy;
        }


        for (int i = 0; i < items.length - 4; i += 1) {
            Nrgy addNrgy = new Nrgy(this);
            items[i] = addNrgy;

        }

        for (int i = items.length - 4; i < items.length - 2; i += 1) {
            Projectile addproj = new Projectile(this);
            items[i] = addproj;
            Roll addroll = new Roll(this);
            items[i + 2] = addroll;
        }

    }

    private void readKey() {

        screen.drawHUD(); //TODO: fix the offset thing
        screen.fillHUD();
        String input = "";
        key.keyPressed(input);


        while (!gameover) {

            screen.showMousePoint();

            boolean update = key.keystrokeReader();
            if (update) {
                updateGame();
                if (!gameover) {
                    ter.renderFrame(WORLD);
                    screen.fillHUD();
                }
            }
        }
    }

    private void updateGame() {

        for (Antagonist criminal : criminals) {
            criminal.aiMove();
            if (sunlight < 1) {
                criminal.aiMove();
            }
        }

        robocop.drain(1);
        sunlight -= 1;

        if (sunlight == 0) {
            nightTime();
        }

        if (sunlight < -50) {
            Antagonist.damage = 10;
        }

        if (robocop.energy < 1) {
            gameover = true;
        }

        if (crimsleft < 1) {
            win = true;
            gameover = true;
        }
    }

    private void nightTime () {
        screen.darken();
        Antagonist.damage = 5;
    }

    private void triggerGameOver() {
        ter.renderFrame(WORLD);
        if (win) {
            screen.win();
        } else {
            screen.gameover();
        }
    }

    public static void main(String[] args) {

        Game g = new Game();
        TETile[][] world = g.playWithInputString("n4979154725301381123swwawd");


        //Game game = new Game();
        //game.playWithKeyboard();
    }
}

        /* //seed = (int) Long.parseLong(input.replaceAll("[\\D]", ""));



        for (int i = 0; i < input.length(); i += 1) {
            char a = input.charAt(i);
            keystring.addLast(Character.toString(a));
        }
        keystring.removeFirst();
        String stringSeed = "";
        while (!keystring.peekFirst().equals("s")) {
            stringSeed += keystring.removeFirst();
        }

        keystring.removeFirst();
        int seed = Integer.parseInt(stringSeed);


        //char[] series = parseString(input);

        //int counter = parseSeed(input);

        /*TETile[][] world = new TETile[WIDTH][HEIGHT];
        Random r = new Random(seed);
        Map map = new Map(world, seed, HEIGHT, WIDTH);
        world = map.makeMap();
        KeyInput key1 = new KeyInput();*/
       /* while (!keystring.isEmpty()) {
            String input1 = keystring.removeFirst();

        }*/


       /* Game g = new Game();
        g.r = r;
        g.startGame();
        g.WORLD = world;
        g.setVars(g);
        world = runKeys(g, series, counter);

        return world;
    }

    private void setVars(Game g){
        g.gameover = false;
        g.ter = new TERenderer();
        g.createObjects();
        g.setVars(g);
        g.screen = new Screen();
        g.key = new KeyInput(g, g.screen);
        g.win = false;
   }

   /* private char[] parseString(String input) {
        char[] chars = new char[input.length()];
        int charscounter = 0;
        for (char ch : input.toCharArray()) {
            chars[charscounter] = ch;
        }
        return chars;
    }

    private int parseSeed(String input) {
        char[] chars = new char[input.length()];
        int charscounter = 0;
        int counter = 1;
        for (char ch : input.toCharArray()){
            chars[charscounter] = ch;
        }

        if (chars[0] == 'n') {

            StringBuilder sd = new StringBuilder();
            char check = chars[counter];
            while (check != 's') {
                sd.append(String.valueOf(chars[counter]));
                counter += 1;
            }

            counter += 1;

            seed = Integer.parseInt(sd.toString());

        }
        return counter;
    }

    private TETile[][] runKeys(Game game, char[] input, int counter) {
        if (input.length <= counter) {
            return game.WORLD;
        }

        for (int k = counter; k < input.length; k += 1) {
            game.WORLD = keyAction(game, input[k]);
        }
        return game.WORLD;
    }

    private TETile[][] keyAction(Game game, char key) {
        game.key.keyPressed(Character.toString(key));
        return game.WORLD;
    }*/