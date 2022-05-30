package com.merure.checkers.game;

import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UI extends JPanel implements ActionListener, MouseListener {

    protected JButton newGameButton;

    protected JButton resignButton;

    protected static JLabel message;

    protected Board board;

    @Autowired
    State state;

    UI() {
        setBackground(Color.BLACK);
        addMouseListener(this);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);

        resignButton = new JButton("Resign");
        resignButton.addActionListener(this);

        message = new JLabel("", JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 14));
        message.setForeground(Color.black);

        board = new Board();
        State.setBoard(board); // Keep state of the board; TODO CheckersData

        // Note: paintComponent is called by JPanel, responsible for checkerboard & checkers markup
        updateUi();
    }

    public void updateUi() {
        repaint();
    }

    /**
     *
     *
     * @param graphics object from JPanel
     */
    public void paintComponent(Graphics graphics) {

        /* Turn on antialiasing to get nicer ovals. */
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw border around the edges of the canvas.
        graphics.setColor(Color.black);
        graphics.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        graphics.drawRect(1, 1, getSize().width - 3, getSize().height - 3);

        // Draw the squares of the checkerboard & checkers.
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2)
                    graphics.setColor(Color.LIGHT_GRAY);
                else
                    graphics.setColor(Color.GRAY);
                graphics.fillRect(2 + col * 20, 2 + row * 20, 20, 20);
                switch (board.grid[row][col]) {
                    case Board.PLAYER:
                        graphics.setColor(Color.RED);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        break;
                    case Board.OPPONENT:
                        graphics.setColor(Color.BLACK);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        break;
                    case Board.PLAYER_KING:
                        graphics.setColor(Color.RED);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        graphics.setColor(Color.WHITE);
                        graphics.drawString("K", 7 + col * 20, 16 + row * 20);
                        break;
                    case Board.OPPONENT_KING:
                        graphics.setColor(Color.BLACK);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        graphics.setColor(Color.WHITE);
                        graphics.drawString("K", 7 + col * 20, 16 + row * 20);
                        break;
                }
            }
        }
    }

    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        if (src == newGameButton)
            doNewGame();
        else if (src == resignButton)
            doResign();
    }

    private void doNewGame() {
        setUiMessage("New game started");
    }

    private void doResign() {
        setUiMessage("Game resigned");
    }

    protected static void setUiMessage(String text) {
        message.setText(text);
    }

    public void mousePressed(MouseEvent evt) {
        if (State.gameInProgress == false)
            UI.setUiMessage("Click \"New Game\" to start a new game.");
        else {
            int col = (evt.getX() - 2) / 20;
            int row = (evt.getY() - 2) / 20;
            if (col >= 0 && col < 8 && row >= 0 && row < 8)
                Move.doClickSquare(row, col);
                updateUi();
        }
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }
}
