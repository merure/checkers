package com.merure.checkers.game;

import com.merure.checkers.CheckersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CheckersConfiguration.class })
class BoardTests {

    @MockBean
    State state;

    @MockBean
    Game game;

    @MockBean
    JFrame frame;

    UI ui;

    @BeforeEach
    void setUp() {
        state = new State();
        game = new Game(state);
        ui = new UI(state);
    }

    @Test
    void checkerBoardCorrectlyPopulated() {
        int[][] grid = this.state.boardState.grid;

        // BLACK checkers set
        int blackChecker = Board.BLACK;
        int redChecker = Board.RED;
        int emptyField = Board.EMPTY;

        // First row - 8 fields
        assertEquals(grid[0][0], blackChecker); // Odd
        assertEquals(grid[0][1], emptyField); // Even
        assertEquals(grid[0][2], blackChecker); // Odd
        assertEquals(grid[0][3], emptyField); // Even
        assertEquals(grid[0][4], blackChecker); // Odd
        assertEquals(grid[0][5], emptyField); // Even
        assertEquals(grid[0][6], blackChecker); // Odd
        assertEquals(grid[0][7], emptyField); // Even

        // Second row - 8 fields
        assertEquals(grid[1][0], emptyField); // Odd
        assertEquals(grid[1][1], blackChecker); // Even
        assertEquals(grid[1][2], emptyField); // Odd
        assertEquals(grid[1][3], blackChecker); // Even
        assertEquals(grid[1][4], emptyField); // Odd
        assertEquals(grid[1][5], blackChecker); // Even
        assertEquals(grid[1][6], emptyField); // Odd
        assertEquals(grid[1][7], blackChecker); // Even

        // Third row - 8 fields
        assertEquals(grid[2][0], blackChecker); // Odd
        assertEquals(grid[2][1], emptyField); // Even
        assertEquals(grid[2][2], blackChecker); // Odd
        assertEquals(grid[2][3], emptyField); // Even
        assertEquals(grid[2][4], blackChecker); // Odd
        assertEquals(grid[2][5], emptyField); // Even
        assertEquals(grid[2][6], blackChecker); // Odd
        assertEquals(grid[2][7], emptyField); // Even

        // Forth row - 8 fields
        assertEquals(grid[3][0], emptyField); // Odd
        assertEquals(grid[3][1], emptyField); // Even
        assertEquals(grid[3][2], emptyField); // Odd
        assertEquals(grid[3][3], emptyField); // Even
        assertEquals(grid[3][4], emptyField); // Odd
        assertEquals(grid[3][5], emptyField); // Even
        assertEquals(grid[3][6], emptyField); // Odd
        assertEquals(grid[3][7], emptyField); // Even

        // Fifth row - 8 fields
        assertEquals(grid[4][0], emptyField); // Odd
        assertEquals(grid[4][1], emptyField); // Even
        assertEquals(grid[4][2], emptyField); // Odd
        assertEquals(grid[4][3], emptyField); // Even
        assertEquals(grid[4][4], emptyField); // Odd
        assertEquals(grid[4][5], emptyField); // Even
        assertEquals(grid[4][6], emptyField); // Odd
        assertEquals(grid[4][7], emptyField); // Even

        // Sixth row - 8 fields
        assertEquals(grid[5][0], emptyField); // Odd
        assertEquals(grid[5][1], redChecker); // Even
        assertEquals(grid[5][2], emptyField); // Odd
        assertEquals(grid[5][3], redChecker); // Even
        assertEquals(grid[5][4], emptyField); // Odd
        assertEquals(grid[5][5], redChecker); // Even
        assertEquals(grid[5][6], emptyField); // Odd
        assertEquals(grid[5][7], redChecker); // Even

        // Seventh row - 8 fields
        assertEquals(grid[6][0], redChecker); // Odd
        assertEquals(grid[6][1], emptyField); // Even
        assertEquals(grid[6][2], redChecker); // Odd
        assertEquals(grid[6][3], emptyField); // Even
        assertEquals(grid[6][4], redChecker); // Odd
        assertEquals(grid[6][5], emptyField); // Even
        assertEquals(grid[6][6], redChecker); // Odd
        assertEquals(grid[6][7], emptyField); // Even

        // Eighth row - 8 fields
        assertEquals(grid[7][0], emptyField); // Odd
        assertEquals(grid[7][1], redChecker); // Even
        assertEquals(grid[7][2], emptyField); // Odd
        assertEquals(grid[7][3], redChecker); // Even
        assertEquals(grid[7][4], emptyField); // Odd
        assertEquals(grid[7][5], redChecker); // Even
        assertEquals(grid[7][6], emptyField); // Odd
        assertEquals(grid[7][7], redChecker); // Even
    }

    @Test
    void checkerBoardInIncorrectOrder() {
        int[][] grid = this.state.boardState.grid;

        // BLACK checkers set
        int blackChecker = Board.BLACK;
        int emptyField = Board.EMPTY;

        // First row - 8 fields - Wrong order
        assertNotEquals(grid[0][0], emptyField); // Odd
        assertNotEquals(grid[0][1], blackChecker); // Even
        assertNotEquals(grid[0][2], emptyField); // Odd
        assertNotEquals(grid[0][3], blackChecker); // Even
        assertNotEquals(grid[0][4], emptyField); // Odd
        assertNotEquals(grid[0][5], blackChecker); // Even
        assertNotEquals(grid[0][6], emptyField); // Odd
        assertNotEquals(grid[0][7], blackChecker); // Even
    }

    @Test
    void checkerBoardInIncorrectColor() {
        int[][] grid = this.state.boardState.grid;

        // BLACK checkers set
        int redChecker = Board.RED;
        int emptyField = Board.EMPTY;

        // First row - 8 fields
        assertNotEquals(grid[0][0], redChecker); // Odd
        assertNotEquals(grid[0][2], redChecker); // Odd
        assertNotEquals(grid[0][4], redChecker); // Odd
        assertNotEquals(grid[0][6], redChecker); // Odd
    }

    @Test
    void correctRandomMoveReturned() {
        Move move1 = new Move(0, 0, 1, 1);
        Move move2 = new Move(2, 2, 1, 1);
        Move[] moves = { move1, move2};

        Move randomMove = this.state.boardState.calculateRandomMove(moves);

        boolean expectedResult = false;
        Move nonMatchedMove = null;
        for (Move move: moves) {
            if (move == randomMove) {
                expectedResult = true;
            } else {
                if (move == move1) {
                    nonMatchedMove = move1;
                } else {
                    nonMatchedMove = move2;
                }
            }
        }
        assertTrue(expectedResult);
        assertNotEquals(randomMove, nonMatchedMove);
    }

    /**
     * legalMoves = {Move[7]@8169}
     *         0 = {Move@8172}
     *         fromRow = 5
     *         fromCol = 1
     *         toRow = 4
     *         toCol = 2
     *         1 = {Move@8173}
     *         fromRow = 5
     *         fromCol = 1
     *         toRow = 4
     *         toCol = 0
     *         2 = {Move@8174}
     *         fromRow = 5
     *         fromCol = 3
     *         toRow = 4
     *         toCol = 4
     *         3 = {Move@8175}
     *         fromRow = 5
     *         fromCol = 3
     *         toRow = 4
     *         toCol = 2
     *         4 = {Move@8176}
     *         fromRow = 5
     *         fromCol = 5
     *         toRow = 4
     *         toCol = 6
     *         5 = {Move@8177}
     *         fromRow = 5
     *         fromCol = 5
     *         toRow = 4
     *         toCol = 4
     *         6 = {Move@8178}
     *         fromRow = 5
     *         fromCol = 7
     *         toRow = 4
     *         toCol = 6
     */
    @Test
    void correctLegalMovesRedCheckerStartGame() {
        Move[] legalMoves = this.state.boardState.getLegalMoves(Board.RED);

        Move[] expectedMoves = {
                new Move(5,1,4,2),
                new Move(5,1,4,0),
                new Move(5,3,4,4),
                new Move(5,3,4,2),
                new Move(5,5,4,6),
                new Move(5,5,4,4),
                new Move(5,7,4,6)
        };

        for(int i = 0; i < expectedMoves.length; i++) {
            assertEquals(expectedMoves[i].fromRow, legalMoves[i].fromRow);
            assertEquals(expectedMoves[i].fromCol, legalMoves[i].fromCol);
            assertEquals(expectedMoves[i].toRow, legalMoves[i].toRow);
            assertEquals(expectedMoves[i].toCol, legalMoves[i].toCol);
        }
    }

    /**
     * legalMoves = {Move[7]@8169}
     *  0 = {Move@8170}
     *   fromRow = 2
     *   fromCol = 0
     *   toRow = 3
     *   toCol = 1
     *  1 = {Move@8171}
     *   fromRow = 2
     *   fromCol = 2
     *   toRow = 3
     *   toCol = 3
     *  2 = {Move@8172}
     *   fromRow = 2
     *   fromCol = 2
     *   toRow = 3
     *   toCol = 1
     *  3 = {Move@8173}
     *   fromRow = 2
     *   fromCol = 4
     *   toRow = 3
     *   toCol = 5
     *  4 = {Move@8174}
     *   fromRow = 2
     *   fromCol = 4
     *   toRow = 3
     *   toCol = 3
     *  5 = {Move@8175}
     *   fromRow = 2
     *   fromCol = 6
     *   toRow = 3
     *   toCol = 7
     *  6 = {Move@8176}
     *   fromRow = 2
     *   fromCol = 6
     *   toRow = 3
     *   toCol = 5
     */
    @Test
    void correctLegalMovesBlackCheckerStartGame() {
        Move[] legalMoves = this.state.boardState.getLegalMoves(Board.BLACK);

        Move[] expectedMoves = {
                new Move(2,0,3,1),
                new Move(2,2,3,3),
                new Move(2,2,3,1),
                new Move(2,4,3,5),
                new Move(2,4,3,3),
                new Move(2,6,3,7),
                new Move(2,6,3,5)
        };

        for(int i = 0; i < expectedMoves.length; i++) {
            assertEquals(expectedMoves[i].fromRow, legalMoves[i].fromRow);
            assertEquals(expectedMoves[i].fromCol, legalMoves[i].fromCol);
            assertEquals(expectedMoves[i].toRow, legalMoves[i].toRow);
            assertEquals(expectedMoves[i].toCol, legalMoves[i].toCol);
        }
    }
}
