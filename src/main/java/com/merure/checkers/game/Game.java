package com.merure.checkers.game;

import javax.swing.*;
import java.awt.*;

/**
 * Prepares JPanel where Checkers game will be shown.
 */
public class Game extends JPanel {

    public Game() {

        setLayout(null);  // I will do the layout myself.
        setPreferredSize(new Dimension(350, 250));

        setBackground(new Color(255, 255, 255));  // White background.

        // Setup the components
        Board board = new Board();
//        this.opts = new OptionPanel(this); // TODO refactor buttons as part of optionPanel
        add(board);
        add(board.newGameButton);
        add(board.resignButton);
        add(board.message);

        board.setBounds(20, 20, 164, 164); // Note:  size MUST be 164-by-164 !
        board.newGameButton.setBounds(210, 60, 120, 30);
        board.resignButton.setBounds(210, 120, 120, 30);
        board.message.setBounds(0, 200, 350, 30);

    }
}
