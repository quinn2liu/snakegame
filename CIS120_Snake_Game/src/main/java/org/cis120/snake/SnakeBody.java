package org.cis120.snake;


import java.awt.*;
import java.util.LinkedList;


public class SnakeBody {
    private LinkedList<SnakePart> snakeList;
    private SnakePart head;

    public SnakeBody(int courtWidth, int courtHeight) {
        snakeList = new LinkedList<SnakePart>();
        addNewPart();
    }

    public SnakePart getHead() {
        return this.head;
    }

    public LinkedList<SnakePart> getSnakeList() {
        return this.snakeList;
    }

    public void addNewPart() {
        if (snakeList.size() == 0) {
            snakeList.add(new SnakePart(GameCourt.COURT_WIDTH,
                    GameCourt.COURT_HEIGHT, Color.BLACK, true));
            head = snakeList.getFirst();
            int[] posShift = new int[2];
            posShift[0] = 1;
            posShift[1] = 1;
            snakeList.getFirst().setPosition(posShift);
            snakeList.getFirst().setPy(20);
            snakeList.getFirst().setPx(20);
        } else {
            Direction d = snakeList.getLast().getDirection();
            int lastX = snakeList.getLast().getPx();
            int lastY = snakeList.getLast().getPy();
            int[] lastP = snakeList.getLast().getPosition();
            SnakePart newPart = new SnakePart(GameCourt.COURT_WIDTH,
                    GameCourt.COURT_HEIGHT, Color.BLACK, false);
            if (d == Direction.UP && head.getDirection() != Direction.DOWN) {
                lastY += 20;
                lastP[1] += 1;
            } else if (d == Direction.DOWN && head.getDirection() != Direction.UP) {
                lastY -= 20;
                lastP[1] -= 1;
            } else if (d == Direction.RIGHT && head.getDirection() != Direction.LEFT) {
                lastX -= 20;
                lastP[0] -= 1;
            } else if (d == Direction.LEFT && head.getDirection() != Direction.RIGHT) {
                lastX += 20;
                lastP[0] += 1;
            }
            newPart.setPosition(lastP);
            snakeList.addLast(newPart);
            snakeList.getLast().setPx(lastX);
            snakeList.getLast().setPy(lastY);
        }
    }

    public void move(Direction d) {
        int[] newPosition = head.getPosition();
        for (int i = snakeList.size() - 1; i > 0; i--) {
            snakeList.get(i).setPosition(snakeList.get(i - 1).getPosition());
            snakeList.get(i).setPy(snakeList.get(i - 1).getPy());
            snakeList.get(i).setPx(snakeList.get(i - 1).getPx());
        }

        if (d == Direction.UP) {
            newPosition[1] -= 1;
            snakeList.getFirst().setPy(snakeList.getFirst().getPy() - 20);
        } else if (d == Direction.DOWN) {
            newPosition[1] += 1;
            snakeList.getFirst().setPy(snakeList.getFirst().getPy() + 20);
        } else if (d == Direction.RIGHT) {
            newPosition[0] += 1;
            snakeList.getFirst().setPx(snakeList.getFirst().getPx() + 20);
        } else if (d == Direction.LEFT) {
            newPosition[0] -= 1;
            snakeList.getFirst().setPx(snakeList.getFirst().getPx() - 20);
        }
        head.setPosition(newPosition);
    }

    public void draw(Graphics g) {
        for (SnakePart s : snakeList) {
            s.draw(g);
        }
    }


}
