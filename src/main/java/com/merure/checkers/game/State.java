package com.merure.checkers.game;

import org.springframework.stereotype.Component;

@Component
public class State {

    public State() {
    }

    boolean gameInProgress; // Is a game currently in progress?

    /* The next three variables are valid only when the game is in progress. */

    int currentPlayer;      // Whose turn is it now?  The possible values
    //    are CheckersData.RED and CheckersData.BLACK.

    int selectedRow, selectedCol;   // If the current player has selected a piece to
    //     move, these give the row and column
    //     containing that piece.  If no piece is
    //     yet selected, then selectedRow is -1.

    protected Move[] legalMoves;  // An array containing the legal moves for the

    protected Board boardState; // Current state of the grid

    protected String uiMessage; // Message to display on UI

    public void setBoard(Board board) {
        boardState = board;
    }

    public Board getBoard() {
        return boardState;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int player) {
        this.currentPlayer = player;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(int row) {
        selectedRow = row;
    }

    public int getSelectedCol() {
        return selectedCol;
    }

    public void setSelectedCol(int col) {
        selectedCol = col;
    }

    public Move[] getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(Move[] legalMoves) {
        this.legalMoves = legalMoves;
    }

    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }
}
