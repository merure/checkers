package com.merure.checkers.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * User Interface for the Checkers game and button behavior.
 */
public class UI extends JPanel implements ActionListener, MouseListener {

    protected JButton newGameButton;

    protected JButton resignButton;

    protected static JLabel message;

    protected Board board;

    State state;

    UI(State state) {
        this.state = state;

        setBackground(Color.BLACK);
        addMouseListener(this);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);

        resignButton = new JButton("Resign");
        resignButton.addActionListener(this);

        message = new JLabel("", JLabel.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 14));
        message.setForeground(Color.black);

        board = new Board(state);
        state.setBoard(board);

        updateUi(); // Note: paintComponent is called by JPanel, responsible for checkerboard & checkers markup
        resignButton.setEnabled(false);
        doNewGame();
    }

    /**
     * Update the checkers board, buttons and/or text.
     */
    public void updateUi() {
        repaint();
    }

    /**
     * Generates the JComponent graphics and allows further editing.
     * Called on repaint().
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
                    case Board.RED:
                        graphics.setColor(Color.RED);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        break;
                    case Board.BLACK:
                        graphics.setColor(Color.BLACK);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        break;
                    case Board.RED_KING:
                        graphics.setColor(Color.RED);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        graphics.setColor(Color.WHITE);
                        graphics.drawString("K", 7 + col * 20, 16 + row * 20);
                        break;
                    case Board.BLACK_KING:
                        graphics.setColor(Color.BLACK);
                        graphics.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                        graphics.setColor(Color.WHITE);
                        graphics.drawString("K", 7 + col * 20, 16 + row * 20);
                        break;
                }
            }
        }

        if (this.state.gameInProgress) {

            // Draw a 2-pixel cyan border around the pieces that can be moved.
            graphics.setColor(Color.cyan);

            Move[] legalMoves = this.state.legalMoves;
            int selectedRow = this.state.selectedRow;
            int selectedCol = this.state.selectedCol;

            // Highlight legal moves
            if (legalMoves != null) {
                for (Move legalMove : legalMoves) {
                    graphics.drawRect(2 + legalMove.fromCol * 20, 2 + legalMove.fromRow * 20, 19, 19);
                    graphics.drawRect(3 + legalMove.fromCol * 20, 3 + legalMove.fromRow * 20, 17, 17);
                }

                // Draw white and green border around respectable movable piece and field
                if (selectedRow >= 0) {
                    graphics.setColor(Color.white);
                    graphics.drawRect(2 + selectedCol * 20, 2 + selectedRow * 20, 19, 19);
                    graphics.drawRect(3 + selectedCol * 20, 3 + selectedRow * 20, 17, 17);
                    graphics.setColor(Color.green);
                    for (Move legalMove : legalMoves) {
                        if (legalMove.fromCol == selectedCol && legalMove.fromRow == selectedRow) {
                            graphics.drawRect(2 + legalMove.toCol * 20, 2 + legalMove.toRow * 20, 19, 19);
                            graphics.drawRect(3 + legalMove.toCol * 20, 3 + legalMove.toRow * 20, 17, 17);
                        }
                    }
                }
            }
        }
    }

    /**
     * Button click.
     *
     * @param evt containing object related to element triggering the action.
     */
    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        if (src == newGameButton)
            doNewGame();
        else if (src == resignButton)
            doResign();
    }

    /**
     * Starts a new game.
     */
    private void doNewGame() {
        if (!this.state.gameInProgress) {

            // Prepare new game
            int player = Board.RED;
            int selectedRow = -1; // Player has not yet selected a piece to move.
            Move[] legalMoves = board.getLegalMoves(player);

            // Update state
            this.state.setCurrentPlayer(player);
            this.state.setLegalMoves(legalMoves);
            this.state.setGameInProgress(true);
            this.state.setSelectedRow(selectedRow);

            //
            board.setUpGame(); // Set up the pieces.

            // Update UI
            setUiMessage("New game started - Player: Make your move.");
            newGameButton.setEnabled(false);
            resignButton.setEnabled(true);
            updateUi();

        } else {
            setUiMessage("Finish the current game first!");
        }
    }

    /**
     * Resigns the currently active player from the game.
     */
    private void doResign() {

        // Inform user game has been resigned
        setUiMessage("Game resigned");

        // Fail-safe
        if (!this.state.gameInProgress) {
            message.setText("There is no game in progress!");
            return;
        }

        // Reset game
        if (this.state.currentPlayer == Board.RED) {
            gameOver("RED resigns. BLACK wins.");
        } else {
            gameOver("BLACK resigns. RED wins.");
        }
    }

    /**
     * Reset to starting state, where user can start new game.
     *
     * @param str game over text to displayed in JPanel message
     */
    public void gameOver(String str) {
        setUiMessage(str);
        newGameButton.setEnabled(true);
        resignButton.setEnabled(false);
        this.state.gameInProgress = false;
        updateUi();
    }

    /**
     * Updates text to display in UI.
     *
     * @param text value displayed in JLabel.
     */
    protected static void setUiMessage(String text) {
        message.setText(text);
    }

    /**
     * User interaction when clicking on checker board.
     *
     * @param evt containing mouse coordinates
     */
    @Override
    public void mousePressed(MouseEvent evt) {
        if (!this.state.gameInProgress)
            setUiMessage("Click \"New Game\" to start a new game.");
        else {
            int col = (evt.getX() - 2) / 20;
            int row = (evt.getY() - 2) / 20;
            if (col >= 0 && col < 8 && row >= 0 && row < 8)
                board.doClickSquare(row, col);
            updateUi();
        }
    }

    // Not used, only implemented as part of MouseListener
    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent evt) {}

    public void mouseEntered(MouseEvent evt) {}

    public void mouseExited(MouseEvent evt) {}
}
