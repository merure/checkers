package com.merure.checkers.game;

public class Board {

    // States of fields on the checkers board (grid)
    static final int
            EMPTY = 0,
            PLAYER = 1,
            PLAYER_KING = 2,
            OPPONENT = 3,
            OPPONENT_KING = 4;

    // grid[r][c] is the contents of row r, column c.
    int[][] grid;

    /**
     * The grid representing the checkers board.
     */
    Board() {
        grid = new int[8][8];
        setUpGame();
    }

    /**
     * Set up the board with checkers in position for the beginning
     * of a game.  Note that checkers can only be found in squares
     * that satisfy  row % 2 == col % 2.  At the start of the game,
     * all such squares in the first three rows contain black squares
     * and all such squares in the last three rows contain red squares.
     */
    void setUpGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    if (row < 3)
                        grid[row][col] = PLAYER;
                    else if (row > 4)
                        grid[row][col] = OPPONENT;
                    else
                        grid[row][col] = EMPTY;
                } else {
                    grid[row][col] = EMPTY;
                }
            }
        }
    }  // end setUpGame()
}
