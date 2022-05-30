package com.merure.checkers.game;

import java.util.ArrayList;

public class Board {

    State state;

    // States of fields on the checkers board (grid)
    static final int
            EMPTY = 0,
            RED = 1,
            RED_KING = 2,
            BLACK = 3,
            BLACK_KING = 4;

    // grid[r][c] is the contents of row r, column c.
    int[][] grid;

    /**
     * The grid representing the checkers board.
     */
    Board(State state) {
        this.state = state;
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
                        grid[row][col] = BLACK;
                    else if (row > 4)
                        grid[row][col] = RED;
                    else
                        grid[row][col] = EMPTY;
                } else {
                    grid[row][col] = EMPTY;
                }
            }
        }
    }

    /**
     * Clear entire board, not used at the moment.
     */
    void clearGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                grid[row][col] = EMPTY;
            }
        }
    }

    /**
     * This is called by mousePressed() when a player clicks on the
     * square in the specified row and col.  It has already been checked
     * that a game is, in fact, in progress.
     */
    void doClickSquare(int row, int col) {
        Move[] legalMoves = this.state.legalMoves;

        /* If the player clicked on one of the pieces that the player
         can move, mark this row and col as selected and return.  (This
         might change a previous selection.)  Reset the message, in
         case it was previously displaying an error message. */
        for (Move legalMove : legalMoves)
            if (legalMove.fromRow == row && legalMove.fromCol == col) {
                this.state.setSelectedRow(row);
                this.state.setSelectedCol(col);
                if (this.state.getCurrentPlayer() == RED) {
                    UI.setUiMessage("RED: Make your move!");
                } else {
                    UI.setUiMessage("BLACK: Make your move!");
                }
                return;
            }

        // If no piece has been selected to be moved, the user must first select a piece.  Show an error message and return.
        int selectedRow = this.state.getSelectedRow();
        int selectedCol = this.state.getSelectedCol();

        if (selectedRow < 0) {
            UI.setUiMessage("Click the piece you want to move.");
            return;
        }

        // If the user clicked on a square where the selected piece can be legally moved, then make the move and return.
        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
                    && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
                doMakeMove(legalMoves[i]);
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

        this.state.boardState.makeMove(move);

        // Check for multiple jumps
        if (move.isJump()) {
            this.state.legalMoves = getLegalJumpsFrom(this.state.currentPlayer, move.toRow, move.toCol);
            if (this.state.legalMoves != null) {
                if (this.state.currentPlayer == RED) {
                    UI.setUiMessage("RED: You must continue jumping.");
                } else {
                    UI.setUiMessage("BLACK: You must continue jumping.");
                }
                this.state.selectedRow = move.toRow;  // Since only one piece can be moved, select it.
                this.state.selectedCol = move.toCol;
                return;
            }
        }

        // Switch currently active player when turn has ended and prepare legal moves or end game when none available.
        if (this.state.currentPlayer == RED) {
            this.state.currentPlayer = BLACK;
            this.state.legalMoves = this.state.boardState.getLegalMoves(this.state.currentPlayer);
            if (this.state.legalMoves == null) {
                UI.setUiMessage("BLACK has no moves.  RED wins.");
            } else if (this.state.legalMoves[0].isJump()) {
                UI.setUiMessage("BLACK: Make your move.  You must jump.");
            } else {
                UI.setUiMessage("BLACK: Make your move.");
            }
        } else {
            this.state.currentPlayer = RED;
            this.state.legalMoves = this.state.boardState.getLegalMoves(this.state.currentPlayer);
            if (this.state.legalMoves == null) {
                UI.setUiMessage("RED has no moves.  BLACK wins.");
            } else if (this.state.legalMoves[0].isJump()) {
                UI.setUiMessage("RED: Make your move.  You must jump.");
            } else {
                UI.setUiMessage("RED: Make your move.");
            }
        }

        // Reset piece selection
        this.state.selectedRow = -1;

        // Pre-selected when all legal moves use the same piece.
        if (this.state.legalMoves != null) {
            boolean sameStartSquare = true;
            for (int i = 1; i < this.state.legalMoves.length; i++)
                if (this.state.legalMoves[i].fromRow != this.state.legalMoves[0].fromRow
                        || this.state.legalMoves[i].fromCol != this.state.legalMoves[0].fromCol) {
                    sameStartSquare = false;
                    break;
                }
            if (sameStartSquare) {
                this.state.selectedRow = this.state.legalMoves[0].fromRow;
                this.state.selectedCol = this.state.legalMoves[0].fromCol;
            }
        }
    }

    /**
     * Make the specified move.  It is assumed that move
     * is non-null and that the move it represents is legal.
     */
    void makeMove(Move move) {
        makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    /**
     * Make the move from (fromRow,fromCol) to (toRow,toCol).  It is
     * assumed that this move is legal.  If the move is a jump, the
     * jumped piece is removed from the board.  If a piece moves to
     * the last row on the opponent's side of the board, the
     * piece becomes a king.
     */
    void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        grid[toRow][toCol] = grid[fromRow][fromCol];
        grid[fromRow][fromCol] = EMPTY;
        if (fromRow - toRow == 2 || fromRow - toRow == -2) {
            // The move is a jump.  Remove the jumped piece from the board.
            int jumpRow = (fromRow + toRow) / 2;  // Row of the jumped piece.
            int jumpCol = (fromCol + toCol) / 2;  // Column of the jumped piece.
            grid[jumpRow][jumpCol] = EMPTY;
        }
        if (toRow == 0 && grid[toRow][toCol] == RED)
            grid[toRow][toCol] = RED_KING;
        if (toRow == 7 && grid[toRow][toCol] == BLACK)
            grid[toRow][toCol] = BLACK_KING;
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

        if (player != RED && player != BLACK)
            return null;

        int playerKing;  // The constant representing a King belonging to player.
        if (player == RED)
            playerKing = RED_KING;
        else
            playerKing = BLACK_KING;

        ArrayList<Move> moves = new ArrayList<>();  // Moves will be stored in this list.

            /*  First, check for any possible jumps.  Look at each square on the board.
             If that square contains one of the player's pieces, look at a possible
             jump in each of the four directions from that square.  If there is
             a legal jump in that direction, put it in the moves ArrayList.
             */

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (grid[row][col] == player || grid[row][col] == playerKing) {
                    if (canJump(player, row, col, row + 1, col + 1, row + 2, col + 2))
                        moves.add(new Move(row, col, row + 2, col + 2));
                    if (canJump(player, row, col, row - 1, col + 1, row - 2, col + 2))
                        moves.add(new Move(row, col, row - 2, col + 2));
                    if (canJump(player, row, col, row + 1, col - 1, row + 2, col - 2))
                        moves.add(new Move(row, col, row + 2, col - 2));
                    if (canJump(player, row, col, row - 1, col - 1, row - 2, col - 2))
                        moves.add(new Move(row, col, row - 2, col - 2));
                }
            }
        }

            /*  If any jump moves were found, then the user must jump, so we don't
             add any regular moves.  However, if no jumps were found, check for
             any legal regular moves.  Look at each square on the board.
             If that square contains one of the player's pieces, look at a possible
             move in each of the four directions from that square.  If there is
             a legal move in that direction, put it in the moves ArrayList.
             */

        if (moves.size() == 0) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (grid[row][col] == player || grid[row][col] == playerKing) {
                        if (canMove(player, row, col, row + 1, col + 1))
                            moves.add(new Move(row, col, row + 1, col + 1));
                        if (canMove(player, row, col, row - 1, col + 1))
                            moves.add(new Move(row, col, row - 1, col + 1));
                        if (canMove(player, row, col, row + 1, col - 1))
                            moves.add(new Move(row, col, row + 1, col - 1));
                        if (canMove(player, row, col, row - 1, col - 1))
                            moves.add(new Move(row, col, row - 1, col - 1));
                    }
                }
            }
        }

            /* If no legal moves have been found, return null.  Otherwise, create
             an array just big enough to hold all the legal moves, copy the
             legal moves from the ArrayList into the array, and return the array. */

        if (moves.size() == 0)
            return null;
        else {
            Move[] moveArray = new Move[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                moveArray[i] = moves.get(i);
            return moveArray;
        }

    }

    /**
     * Return a list of the legal jumps that the specified player can
     * make starting from the specified row and column.  If no such
     * jumps are possible, null is returned.  The logic is similar
     * to the logic of the getLegalMoves() method.
     */
    Move[] getLegalJumpsFrom(int player, int row, int col) {

        if (player != RED && player != BLACK)
            return null;
        int playerKing;  // The constant representing a King belonging to player.
        if (player == RED)
            playerKing = RED_KING;
        else
            playerKing = BLACK_KING;
        ArrayList<Move> moves = new ArrayList<Move>();  // The legal jumps will be stored in this list.
        if (grid[row][col] == player || grid[row][col] == playerKing) {
            if (canJump(player, row, col, row + 1, col + 1, row + 2, col + 2))
                moves.add(new Move(row, col, row + 2, col + 2));
            if (canJump(player, row, col, row - 1, col + 1, row - 2, col + 2))
                moves.add(new Move(row, col, row - 2, col + 2));
            if (canJump(player, row, col, row + 1, col - 1, row + 2, col - 2))
                moves.add(new Move(row, col, row + 2, col - 2));
            if (canJump(player, row, col, row - 1, col - 1, row - 2, col - 2))
                moves.add(new Move(row, col, row - 2, col - 2));
        }
        if (moves.size() == 0)
            return null;
        else {
            Move[] moveArray = new Move[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                moveArray[i] = moves.get(i);
            return moveArray;
        }
    }

    /**
     * This is called by the two previous methods to check whether the
     * player can legally jump from (r1,c1) to (r3,c3).  It is assumed
     * that the player has a piece at (r1,c1), that (r3,c3) is a position
     * that is 2 rows and 2 columns distant from (r1,c1) and that
     * (r2,c2) is the square between (r1,c1) and (r3,c3).
     */
    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
            return false;  // (r3,c3) is off the board.

        if (grid[r3][c3] != EMPTY)
            return false;  // (r3,c3) already contains a piece.

        if (player == RED) {
            if (grid[r1][c1] == RED && r3 > r1)
                return false;  // Regular red piece can only move up.
            if (grid[r2][c2] != BLACK && grid[r2][c2] != BLACK_KING)
                return false;  // There is no black piece to jump.
            return true;  // The jump is legal.
        } else {
            if (grid[r1][c1] == BLACK && r3 < r1)
                return false;  // Regular black piece can only move downn.
            if (grid[r2][c2] != RED && grid[r2][c2] != RED_KING)
                return false;  // There is no red piece to jump.
            return true;  // The jump is legal.
        }

    }

    /**
     * This is called by the getLegalMoves() method to determine whether
     * the player can legally move from (r1,c1) to (r2,c2).  It is
     * assumed that (r1,r2) contains one of the player's pieces and
     * that (r2,c2) is a neighboring square.
     */
    private boolean canMove(int player, int r1, int c1, int r2, int c2) {

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
            return false;  // (r2,c2) is off the board.

        if (grid[r2][c2] != EMPTY)
            return false;  // (r2,c2) already contains a piece.

        if (player == RED) {
            if (grid[r1][c1] == RED && r2 > r1)
                return false;  // Regular red piece can only move down.
            return true;  // The move is legal.
        } else {
            if (grid[r1][c1] == BLACK && r2 < r1)
                return false;  // Regular black piece can only move up.
            return true;  // The move is legal.
        }

    }
}
