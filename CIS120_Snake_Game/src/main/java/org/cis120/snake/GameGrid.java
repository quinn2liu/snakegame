package org.cis120.snake;

import java.awt.Color;
import java.awt.Graphics;

public class GameGrid {
    private static final int r = GameCourt.COURT_HEIGHT / 20;
    private static final int c = GameCourt.COURT_WIDTH / 20;
    private static GameObj[][] grid;

    public GameGrid() {
        this.grid = new GameObj[r][c];
    }

    public static void draw(Graphics g, SnakeBody s) {
        int x = 0;
        int y = 0;
        for (int a = 0; a < r; a++) {
            for (int b = 0; b < c; b++) {
                g.setColor(Color.BLUE);
                g.drawRect(x, y, 20, 20);
                for (SnakePart part : s.getSnakeList()) {
                    int[] partPosition = part.getPosition();
                    if (partPosition[0] == b && partPosition[1] == a) {
                        part.draw(g);
                    }
                }
                x += 20;
            }
            y += 20;
            x = 0;
        }
    }
}