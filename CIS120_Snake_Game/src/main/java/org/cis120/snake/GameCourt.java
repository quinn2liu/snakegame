package org.cis120.snake;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.LinkedList;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private SnakeBody snake; // the snake, keyboard control
    private Apple apple; // the apple, moves after the snake intersects it
    private Ghost ghost1; // the ghost, moves when snake intersects apple
    private Ghost ghost2;
    private Ghost ghost3;

    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel save;

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 300;
    public static final int SNAKE_VELOCITY = 20;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 125;

    private static int score;

    public GameCourt(JLabel status) {
        snake = new SnakeBody(COURT_WIDTH, COURT_HEIGHT);
        apple = new Apple(COURT_WIDTH, COURT_HEIGHT, Color.RED);
        ghost1 = new Ghost(COURT_WIDTH, COURT_HEIGHT);
        ghost2 = new Ghost(COURT_WIDTH, COURT_HEIGHT);
        ghost2.setPx(160);
        ghost3 = new Ghost(COURT_WIDTH, COURT_HEIGHT);
        ghost3.setPy(200);


        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT &&
                        snake.getHead().getDirection() != Direction.RIGHT) {
                    snake.getHead().setDirection(Direction.LEFT);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT &&
                        snake.getHead().getDirection() != Direction.LEFT) {
                    snake.getHead().setDirection(Direction.RIGHT);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN &&
                        snake.getHead().getDirection() != Direction.UP) {
                    snake.getHead().setDirection(Direction.DOWN);
                } else if (e.getKeyCode() == KeyEvent.VK_UP &&
                        snake.getHead().getDirection() != Direction.DOWN) {
                    snake.getHead().setDirection(Direction.UP);
                }
            }
        });

        this.status = status;
        this.score = 0;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        snake = new SnakeBody(COURT_WIDTH, COURT_HEIGHT);
        ghost1 = new Ghost(COURT_WIDTH, COURT_HEIGHT);
        ghost2 = new Ghost(COURT_WIDTH, COURT_HEIGHT);
        ghost3 = new Ghost(COURT_WIDTH, COURT_HEIGHT);
        apple = new Apple(COURT_WIDTH, COURT_HEIGHT, Color.RED);

        playing = true;
        score = 0;
        status.setText("Score: " + score);

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    public void tick() {
        if (playing) {
            // advance the snake in their current direction.
            snake.move(snake.getHead().getDirection());
            int[] headPos = snake.getHead().getPosition();

            // check for the game end conditions
            if (snake.getHead().intersects(ghost1) || snake.getHead().intersects(ghost2) ||
                    snake.getHead().intersects(ghost3)) {
                playing = false;
                status.setText("You lose! Score: " + score);
            } else if (snake.getHead().intersects(apple)) {
                score += 10;
                playing = true;

                ghost1.move(snake);
                ghost2.move(snake);
                ghost3.move(snake);
                apple.move(snake);
                snake.addNewPart();
                while (ghost1.getPosition().equals(apple.getPosition())
                        || ghost2.getPosition().equals(apple.getPosition())
                        || ghost3.getPosition().equals(apple.getPosition())) {
                    apple.move(snake);
                }
                if (snake.getSnakeList().size() == 2) {
                    if (snake.getHead().getDirection().equals(Direction.UP)) {
                        headPos[1] -= 1;
                    } else if (snake.getHead().getDirection().equals(Direction.DOWN)) {
                        headPos[1] += 1;
                    } else if (snake.getHead().getDirection().equals(Direction.RIGHT)) {
                        headPos[0] += 1;
                    } else if (snake.getHead().getDirection().equals(Direction.LEFT)) {
                        headPos[0] += 1;
                    }
                    snake.getHead().setPosition(headPos);
                }
                status.setText("Score: " + score);
            } else if (headPos[0] < 0 || headPos[0] > 14 || headPos[1] < 0 || headPos[1] > 14) {
                playing = false;
                status.setText("You lose! Score: " + score);
            }

            for (int i = snake.getSnakeList().size() - 1; i > 1; i--) {
                if (snake.getHead().intersects(snake.getSnakeList().get(i))) {
                    playing = false;
                    status.setText("You lose! Score: " + score);
                }
            }

            // update the display
            repaint();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        ghost1.draw(g);
        ghost2.draw(g);
        ghost3.draw(g);
        apple.draw(g);
        GameGrid.draw(g, snake);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

    public void saveState(String filePath, boolean append) {
        /*
        stringsToWrite stores the integers that preserve the state
        of the game items. The order goes the snake head's x and y
        position, then the apple's x and y position, then the x and
        y position for the 3 ghosts, the direction of the snake
        when the game was saved, and how long the snake is .
        */
        LinkedList<String> stringsToWrite = new LinkedList<>();

        int[] headPos = snake.getHead().getPosition();

        String headPX = Integer.toString(headPos[0]);
        String headPY = Integer.toString(headPos[1]);

        stringsToWrite.add(headPX);
        stringsToWrite.add(headPY);

        int[] applePos = apple.getPosition();

        stringsToWrite.add(Integer.toString(applePos[0]));
        stringsToWrite.add(Integer.toString(applePos[1]));
        int[] ghost1Pos = ghost1.getPosition();
        int[] ghost2Pos = ghost2.getPosition();
        int[] ghost3Pos = ghost3.getPosition();

        stringsToWrite.add(Integer.toString(ghost1Pos[0]));
        stringsToWrite.add(Integer.toString(ghost1Pos[1]));

        stringsToWrite.add(Integer.toString(ghost2Pos[0]));
        stringsToWrite.add(Integer.toString(ghost2Pos[1]));

        stringsToWrite.add(Integer.toString(ghost3Pos[0]));
        stringsToWrite.add(Integer.toString(ghost3Pos[1]));


        if (snake.getHead().getDirection().equals(Direction.UP)) {
            stringsToWrite.add("UP");
        } else if (snake.getHead().getDirection().equals(Direction.DOWN)) {
            stringsToWrite.add("DOWN");
        } else if (snake.getHead().getDirection().equals(Direction.RIGHT)) {
            stringsToWrite.add("RIGHT");
        } else if (snake.getHead().getDirection().equals(Direction.LEFT)) {
            stringsToWrite.add("LEFT");
        }

        int snakeSize = snake.getSnakeList().size();
        stringsToWrite.add(Integer.toString(snakeSize));

        stringsToWrite.add(Integer.toString(this.score));

        File file = new File(filePath);

        try {
            Writer w = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(w);
            for (String s : stringsToWrite) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("error writing to file");
        }

        this.status.setText("Game Saved");
        this.playing = false;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public SnakeBody getSnake() {
        return this.snake;
    }

    public Apple getApple() {
        return this.apple;
    }

    public Ghost getGhost() {
        return this.ghost1;
    }

    public void loadSave(String filePath) {
        LinkedList<String> positions = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("files/SaveState"));
            String line = reader.readLine();
            while (line != null) {
                positions.addLast(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* reconstructing the snake
         */
        reset();
        int[] headPos = new int[2];
        headPos[0] = Integer.parseInt(positions.get(0));
        headPos[1] = Integer.parseInt(positions.get(1));
        snake.getHead().setPosition(headPos);
        snake.getHead().setPx(((Integer.parseInt(positions.get(0))) + 1) * 20);
        snake.getHead().setPy(((Integer.parseInt(positions.get(1))) + 1) * 20);

        if (positions.get(10).equals("UP")) {
            snake.getHead().setDirection(Direction.UP);
            headPos[1] -= 1;
            snake.getHead().setPosition(headPos);
        } else if (positions.get(10).equals("DOWN")) {
            snake.getHead().setDirection(Direction.DOWN);
            headPos[1] += 1;
            snake.getHead().setPosition(headPos);
        } else if (positions.get(10).equals("RIGHT")) {
            snake.getHead().setDirection(Direction.RIGHT);
            headPos[0] += 1;
            snake.getHead().setPosition(headPos);
        } else if (positions.get(10).equals("LEFT")) {
            snake.getHead().setDirection(Direction.LEFT);
            headPos[0] -= 1;
            snake.getHead().setPosition(headPos);
        }

        for (int i = 1; i < Integer.parseInt(positions.get(11)); i++) {
            snake.addNewPart();
        }


        /* reconstructing the ghosts
         */
        int[] ghostPos = new int[6];
        ghostPos[0] = Integer.parseInt(positions.get(2));
        ghostPos[1] = Integer.parseInt(positions.get(3));
        ghost1.setPosition(ghostPos);
        ghost1.setPx(((Integer.parseInt(positions.get(2))) + 1) * 20);
        ghost1.setPy(((Integer.parseInt(positions.get(3))) + 1) * 20);

        ghostPos[2] = Integer.parseInt(positions.get(4));
        ghostPos[3] = Integer.parseInt(positions.get(5));
        ghost2.setPosition(ghostPos);
        ghost2.setPx(((Integer.parseInt(positions.get(4))) + 1) * 20);
        ghost2.setPy(((Integer.parseInt(positions.get(5))) + 1) * 20);

        ghostPos[4] = Integer.parseInt(positions.get(6));
        ghostPos[5] = Integer.parseInt(positions.get(7));
        ghost3.setPosition(ghostPos);
        ghost3.setPx(((Integer.parseInt(positions.get(6))) + 1) * 20);
        ghost3.setPy(((Integer.parseInt(positions.get(7))) + 1) * 20);

        apple = new Apple(COURT_WIDTH, COURT_HEIGHT, Color.RED);
        int[] applePos = new int[2];
        applePos[0] = Integer.parseInt(positions.get(8));
        applePos[1] = Integer.parseInt(positions.get(9));
        apple.setPosition(applePos);
        apple.setPx(((Integer.parseInt(positions.get(8))) + 1) * 20);
        apple.setPy(((Integer.parseInt(positions.get(9))) + 1) * 20);

        this.score = Integer.parseInt(positions.getLast());

        playing = true;
        status.setText("Score: " + score);

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

}



