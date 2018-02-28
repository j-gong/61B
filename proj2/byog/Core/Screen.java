package byog.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;
import java.util.Random;

public class Screen {
    private int width;
    private int height;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void MainMenu() {
        Font font = new Font("Monaco", Font.BOLD, 64);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(width/2, height/2 + height/5, "Title of Game");
        Font smallerFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(smallerFont);

        StdDraw.text(width/2, height/2, "New Game (N)");
        StdDraw.text(width/2, height/2 - 4, "Load Game (L)");
        StdDraw.text(width/2, height/2 - 8, "Quit (Q)");
        StdDraw.show();
    }

    public static void main(String[] args) {
        Screen test = new Screen(64, 64);
        test.MainMenu();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
