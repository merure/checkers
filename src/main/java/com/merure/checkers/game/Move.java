package com.merure.checkers.game;

import org.springframework.beans.factory.annotation.Autowired;

public class Move {

    @Autowired
    State state;

    protected int selectedRow, selectedCol;

    int fromRow, fromCol;  // Position of piece to be moved.
    int toRow, toCol;      // Square it is to move to.

    Move(int r1, int c1, int r2, int c2) {
        // Constructor.  Just set the values of the instance variables.
        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;
    }

    boolean isJump() {
        // Test whether this move is a jump.  It is assumed that
        // the move is legal.  In a jump, the piece moves two
        // rows.  (In a regular move, it only moves one row.)
        return (fromRow - toRow == 2 || fromRow - toRow == -2);
    }

    /**
     * This is called by mousePressed() when a player clicks on the
     * square in the specified row and col.  It has already been checked
     * that a game is, in fact, in progress.
     */
    static void doClickSquare(int row, int col) {
        Move[] legalMoves = State.legalMoves;

            /* If the player clicked on one of the pieces that the player
             can move, mark this row and col as selected and return.  (This
             might change a previous selection.)  Reset the message, in
             case it was previously displaying an error message. */

        for (Move legalMove : legalMoves)
            if (legalMove.fromRow == row && legalMove.fromCol == col) {
                State.setSelectedRow(row);
                State.setSelectedCol(col);
                if (State.getCurrentPlayer() == Board.PLAYER) {
                    UI.setUiMessage("Player:  Make your move!");
                } else {
                    UI.setUiMessage("Opponent:  Making a move...");
                }
                return;
            }

            /* If no piece has been selected to be moved, the user must first
             select a piece.  Show an error message and return. */

        int selectedRow = State.getSelectedRow();
        int selectedCol = State.getSelectedCol();

        if (selectedRow < 0) {
            UI.setUiMessage("Click the piece you want to move.");
            return;
        }

            /* If the user clicked on a square where the selected piece can be
             legally moved, then make the move and return. */

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
                    && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
//                doMakeMove(legalMoves[i]); TODO
                return;
            }

            /* If we get to this point, there is a piece selected, and the square where
             the user just clicked is not one where that piece can be legally moved.
             Show an error message. */

        UI.setUiMessage("Click the square you want to move to.");

    }

    /**
     * This is called when the current player has chosen the specified
     * move.  Make the move, and then either end or continue the game
     * appropriately.
     */
    void doMakeMove(Move move) {

        // TODO

    }

    /**
     * Return an array containing all the legal CheckersMoves
     * for the specified player on the current board.  If the player
     * has no legal moves, null is returned.  The value of player
     * should be one of the constants RED or BLACK; if not, null
     * is returned.  If the returned value is non-null, it consists
     * entirely of jump moves or entirely of regular moves, since
     * if the player can jump, only jumps are legal moves.
     */
    Move[] getLegalMoves(int player) {

        // TODO
        return null;

    }

    /**
     * Return a list of the legal jumps that the specified player can
     * make starting from the specified row and column.  If no such
     * jumps are possible, null is returned.  The logic is similar
     * to the logic of the getLegalMoves() method.
     */
    Move[] getLegalJumpsFrom(int player, int row, int col) {

        // TODO
        return null;

    }

    /**
     * This is called by the two previous methods to check whether the
     * player can legally jump from (r1,c1) to (r3,c3).  It is assumed
     * that the player has a piece at (r1,c1), that (r3,c3) is a position
     * that is 2 rows and 2 columns distant from (r1,c1) and that
     * (r2,c2) is the square between (r1,c1) and (r3,c3).
     */
    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {

        // TODO
        return true;

    }

}
