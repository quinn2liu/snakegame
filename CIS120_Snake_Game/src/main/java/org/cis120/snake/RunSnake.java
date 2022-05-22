package org.cis120.snake;
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnake implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("SNAKE BABAYYYYYYYYY");
        frame.setLocation(500, 500);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Score: ");
        status_panel.add(status);

        // Save button
        /*
        final JPanel save_panel = new JPanel();
        frame.add(save_panel, BorderLayout.SOUTH);

         */

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        //Functionality for save button
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.saveState("files/SaveState", true);
            }
        });
        control_panel.add(save);

        //Functionality for the load button
        final JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.loadSave("files/SaveState");
            }
        });
        control_panel.add(load);

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(control_panel,
                        "You control the line of squares (or your snake)," +
                                "and your objective of the game" + "is to collect as" +
                                " many apples items as possible. \nEach apple will add "
                                + "on a new" + "body part to the tail, making your " +
                                "snake longer. Make sure to avoid all \n" + "walls, " +
                                "ghosts, and even yourself as they will cause you to " +
                                "lost. Try to get the" + "highest score possible! \n" +
                                "Use the arrow keys to move:" +
                                "Up arrow - UP \n" +
                                "Down arrow - DOWN \n" +
                                "Left arrow - LEFT \n" +
                                "Right arrow - RIGHT \n"
                );
                court.reset();
            }
        });
        control_panel.add(instructions);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }
}