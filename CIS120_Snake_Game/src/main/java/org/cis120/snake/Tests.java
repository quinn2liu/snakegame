package org.cis120.snake;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;


public class Tests {

    private SnakePart head;
    private SnakeBody snake;
    private Apple apple;
    private Ghost ghost;
    private GameCourt gameCourt;
    private JLabel status = new JLabel();

    public Tests() {
        gameCourt = new GameCourt(status);
        snake = gameCourt.getSnake();
        head = gameCourt.getSnake().getHead();
        apple = gameCourt.getApple();
        ghost = gameCourt.getGhost();
    }

    @Test
    public void testCollisionTopWall() {
        Direction d = Direction.UP;
        int[] headPos = head.getPosition();
        snake.move(d);
        snake.move(d);
        assertEquals(1, headPos[0]);
        assertEquals(-1, headPos[1]);
        assertFalse(gameCourt.isPlaying());
    }

    @Test
    public void testCollisionBottomWall() {
        Direction d = Direction.DOWN;
        for (int i = 0; i < 14; i++) {
            snake.move(d);
        }
        int[] headPos = head.getPosition();
        assertEquals(1, headPos[0]);
        assertEquals(15, headPos[1]);
        assertFalse(gameCourt.isPlaying());
    }

    @Test
    public void testCollisionRightWall() {
        Direction d = Direction.RIGHT;
        for (int i = 0; i < 14; i++) {
            snake.move(d);
        }
        int[] headPos = head.getPosition();
        assertEquals(15, headPos[0]);
        assertEquals(1, headPos[1]);
        assertFalse(gameCourt.isPlaying());
    }

    @Test
    public void testCollisionLeftWall() {
        Direction d = Direction.LEFT;
        snake.move(d);
        snake.move(d);
        int[] headPos = head.getPosition();
        assertEquals(-1, headPos[0]);
        assertEquals(1, headPos[1]);
        assertFalse(gameCourt.isPlaying());
    }

    @Test
    public void addNewPartTest() {
        Direction d = Direction.RIGHT;
        snake.addNewPart();
        snake.move(d);
        SnakePart tail = snake.getSnakeList().getLast();
        int[] headPos = head.getPosition();
        int[] tailPos = tail.getPosition();
        assertEquals(2, snake.getSnakeList().size());
        assertEquals(tailPos[0] + 1, headPos[0]);
        assertEquals(tailPos[1], headPos[1]);

    }

    @Test
    public void ghostCollisionTest() {
        int[] ghostPos = ghost.getPosition();
        ghostPos[0] = 2;
        ghostPos[1] = 1;
        ghost.setPx(40);
        ghost.setPy(20);
        ghost.setPosition(ghostPos);
        assertFalse(head.intersects(ghost));
        Direction d = Direction.RIGHT;
        snake.move(d);
        assertTrue(head.intersects(ghost));
    }

    @Test
    public void appleCollisionTest() {
        int[] applePos = apple.getPosition();
        applePos[0] = 2;
        applePos[1] = 1;
        apple.setPx(40);
        apple.setPy(20);
        apple.setPosition(applePos);
        assertFalse(head.intersects(apple));
        Direction d = Direction.RIGHT;
        snake.move(d);
        assertTrue(head.intersects(apple));
    }

    @Test
    public void moveTest() {
        Direction d = Direction.RIGHT;
        int[] headPos = head.getPosition();
        snake.move(d);
        assertEquals(2, headPos[0]);
        assertEquals(1, headPos[1]);

        d = Direction.DOWN;
        snake.move(d);
        assertEquals(2, headPos[0]);
        assertEquals(2, headPos[1]);
        d = Direction.LEFT;
        snake.move(d);
        assertEquals(1, headPos[0]);
        assertEquals(2, headPos[1]);
        d = Direction.UP;
        snake.move(d);
        assertEquals(1, headPos[0]);
        assertEquals(1, headPos[1]);
    }

}
