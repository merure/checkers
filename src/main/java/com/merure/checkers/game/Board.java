package com.merure.checkers.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel implements ActionListener, MouseListener {

    protected JButton newGameButton;

    protected JButton resignButton;

    protected JLabel message;

    static final int

            EMPTY = 0,
            PLAYER = 1,
            PLAYER_KING = 2,
            OPPONENT = 3,
            OPPONENT_KING = 4;

    int[][] board;  // board[r][c] is the contents of row r, column c.

    Board() {
        setBackground(Color.BLACK);
        addMouseListener(this);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);

        resignButton = new JButton("Resign");
        resignButton.addActionListener(this);

        message = new JLabel("", JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 14));
        message.setForeground(Color.black);

        board = new int[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    if (row < 3)
                        board[row][col] = OPPONENT;
                    else if (row > 4)
                        board[row][col] = PLAYER;
                    else
                        board[row][col] = EMPTY;
                } else {
                    board[row][col] = EMPTY;
                }
            }
        }
        repaint();
    }

    public void paintComponent(Graphics graphics) {

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
                switch (board[row][col]) {
                    case PLAYER:
                        graphics.setColor(Color.RED);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        break;
                    case OPPONENT:
                        graphics.setColor(Color.BLACK);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        break;
                    case PLAYER_KING:
                        graphics.setColor(Color.RED);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        graphics.setColor(Color.WHITE);
                        graphics.drawString("K", 7 + col * 20, 16 + row * 20);
                        break;
                    case OPPONENT_KING:
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
        message.setText("New game started");
    }

    private void doResign() {
        message.setText("Game resigned");
    }

    public void mousePressed(MouseEvent evt) {
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
