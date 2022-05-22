package org.cis120.snake;

import java.awt.*;

public class SnakePart extends GameObj {
    public static final int SIZE = 20;
    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private Color color;
    private boolean isHead;

    public SnakePart(int courtWidth, int courtHeight, Color color, boolean isHead) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
        this.isHead = isHead;
        if (this.isHead) {
            this.color = Color.DARK_GRAY;
        } else {
            this.color = color;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

    public boolean getHead() {
        return this.isHead;
    }
    /*
    public void draw(Graphics g, boolean isHead) {
        if (isHead) {
            int eye1X = 0;
            int eye2X = 0;
            int eye1Y = 0;
            int eye2Y = 0;
            g.setColor(this.color);
            if (super.getVx() > 0) {
                eye1X = super.getPx() + 15;
                eye2X = super.getPx() + 15;
                eye1Y = super.getPy();
                eye2Y = super.getPy() - 20;
            } else if (super.getVx() < 0) {
                eye1X = super.getPx() + 5;
                eye2X = super.getPx() + 5;
                eye1Y = super.getPy();
                eye2Y = super.getPy() - 20;
            } else if (super.getVy() > 0) {
                eye1X = super.getPx();
                eye2X = super.getPx() + 20;
                eye1Y = super.getPy() + 5;
                eye2Y = super.getPy() + 5;
            } else if (super.getVy() < 0) {
                eye1X = super.getPx();
                eye2X = super.getPx() + 20;
                eye1Y = super.getPy() + 15;
                eye2Y = super.getPy() + 15;
            }
            g.fillOval(eye1X, eye1Y, 20, 20);
            g.fillOval(eye2X, eye2Y, 20, 20);
            g.setColor(Color.BLACK);
            g.fillOval(eye1X + 2, eye1Y + 2, 5, 5);
            g.fillOval(eye2X + 2, eye2Y + 2, 5, 5);
        } else {
            g.setColor(this.color);
            g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        }
    }

     */
}
