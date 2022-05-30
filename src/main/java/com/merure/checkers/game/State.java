package com.merure.checkers.game;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class State {

    protected static boolean gameInProgress; // Is a game currently in progress?

    /* The next three variables are valid only when the game is in progress. */

    protected static int currentPlayer;      // Whose turn is it now?  The possible values
    //    are CheckersData.RED and CheckersData.BLACK.

    protected static int selectedRow, selectedCol;   // If the current player has selected a piece to
    //     move, these give the row and column
    //     containing that piece.  If no piece is
    //     yet selected, then selectedRow is -1.

    protected static Move[] legalMoves;  // An array containing the legal moves for the

    protected static Board boardState; // Current state of the grid

    public static void setBoard(Board board) {
        boardState = board;
    }

    @Bean
    public static Board getBoard() {
        return boardState;
    }

    @Bean
    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    @Bean
    public static int getSelectedRow() {
        return selectedRow;
    }

    public static void setSelectedRow(int row) {
        selectedRow = row;
    }

    @Bean
    public static int getSelectedCol() {
        return selectedCol;
    }

    public static void setSelectedCol(int col) {
        selectedCol = col;
    }

    public Move[] getLegalMoves() {
        return legalMoves;
    }

}
