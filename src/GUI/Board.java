package Gui;

import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {

    private NineSquare[] gridSquares = new NineSquare[9];
    private boolean[][] fixedCells = new boolean[9][9];
    private Runnable onBoardChange;

    public Board() {
        setLayout(new GridLayout(3, 3, 3, 3));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < gridSquares.length; i++) {
            gridSquares[i] = new NineSquare(Color.BLACK, i);
            add(gridSquares[i]);
        }
        setFocusTraversalPolicyProvider(true);
        setFocusCycleRoot(true);
    }

    // Navigation methods (keep your existing implementations)
    public void navigateLeft(NineSquare currentSquare, int cellIndex) {
        int squareIndex = currentSquare.getSquareIndex();
        int row = cellIndex / 3;
        int col = cellIndex % 3;
        if (col > 0) {
            currentSquare.focusCell(cellIndex - 1);
        } else {
            if (squareIndex % 3 > 0) {
                NineSquare prevSquare = gridSquares[squareIndex - 1];
                prevSquare.focusCell(row * 3 + 2);
            }
        }
    }

    public void navigateRight(NineSquare currentSquare, int cellIndex) {
        int squareIndex = currentSquare.getSquareIndex();
        int row = cellIndex / 3;
        int col = cellIndex % 3;
        if (col < 2) {
            currentSquare.focusCell(cellIndex + 1);
        } else {
            if (squareIndex % 3 < 2) {
                NineSquare nextSquare = gridSquares[squareIndex + 1];
                nextSquare.focusCell(row * 3);
            }
        }
    }

    public void navigateUp(NineSquare currentSquare, int cellIndex) {
        int squareIndex = currentSquare.getSquareIndex();
        int row = cellIndex / 3;
        int col = cellIndex % 3;
        if (row > 0) {
            currentSquare.focusCell(cellIndex - 3);
        } else {
            if (squareIndex / 3 > 0) {
                NineSquare aboveSquare = gridSquares[squareIndex - 3];
                aboveSquare.focusCell(2 * 3 + col);
            }
        }
    }

    public void navigateDown(NineSquare currentSquare, int cellIndex) {
        int squareIndex = currentSquare.getSquareIndex();
        int row = cellIndex / 3;
        int col = cellIndex % 3;
        if (row < 2) {
            currentSquare.focusCell(cellIndex + 3);
        } else {
            if (squareIndex / 3 < 2) {
                NineSquare belowSquare = gridSquares[squareIndex + 3];
                belowSquare.focusCell(col);
            }
        }
    }

    public int[][] getBoardValues() {
        int[][] board = new int[9][9];
        for (int sq = 0; sq < 9; sq++) {
            NineSquare nineSquare = gridSquares[sq];
            for (int cell = 0; cell < 9; cell++) {
                int val = nineSquare.getCellValue(cell);
                int row = (sq / 3) * 3 + (cell / 3);
                int col = (sq % 3) * 3 + (cell % 3);
                board[row][col] = val;
            }
        }
        return board;
    }

    public void setBoardValues(int[][] game) {
        for (int sq = 0; sq < 9; sq++) {
            NineSquare nineSquare = gridSquares[sq];
            for (int cell = 0; cell < 9; cell++) {
                int row = (sq / 3) * 3 + (cell / 3);
                int col = (sq % 3) * 3 + (cell % 3);
                int value = game[row][col];
                nineSquare.setCellValue(cell, value);
                nineSquare.setCellEditable(cell, value == 0);
                fixedCells[row][col] = (value != 0);   // record fixed clue
            }
        }
        // Reset solved cells highlight? Called from outside.
        if (onBoardChange != null) onBoardChange.run();
    }

    public void applyVerification(Boolean[][] result) {
        for (int sq = 0; sq < 9; sq++) {
            NineSquare ns = gridSquares[sq];
            for (int cell = 0; cell < 9; cell++) {
                int row = (sq / 3) * 3 + (cell / 3);
                int col = (sq % 3) * 3 + (cell % 3);
                boolean isValid = result[row][col];
                if (fixedCells[row][col] && isValid) {
                    // Fixed clue that is correct -> white
                    ns.setCellTextColor(cell, Color.WHITE);
                } else {
                    // User-entered cell or fixed clue that is invalid -> green/red based on validity
                    Color color = isValid ? Color.GREEN : Color.RED;
                    ns.setCellTextColor(cell, color);
                }
            }
        }
    }

    // Helper methods for undo and solve highlighting
    public void setCellBackground(int row, int col, Color color) {
        int squareIndex = (row / 3) * 3 + (col / 3);
        int cellIndex = ((row % 3) * 3) + (col % 3);
        gridSquares[squareIndex].setCellBackground(cellIndex, color);
    }

    public void setCellTextColor(int row, int col, Color color) {
        int squareIndex = (row / 3) * 3 + (col / 3);
        int cellIndex = ((row % 3) * 3) + (col % 3);
        gridSquares[squareIndex].setCellTextColor(cellIndex, color);
    }

    public void setCellValueSilent(int row, int col, int value) {
        int squareIndex = (row / 3) * 3 + (col / 3);
        int cellIndex = ((row % 3) * 3) + (col % 3);
        gridSquares[squareIndex].setCellValue(cellIndex, value);
        gridSquares[squareIndex].setCellEditable(cellIndex, value == 0);
        // Do not trigger onBoardChange here – used by undo
    }

    public void setCellEditable(int row, int col, boolean editable) {
        int squareIndex = (row / 3) * 3 + (col / 3);
        int cellIndex = ((row % 3) * 3) + (col % 3);
        gridSquares[squareIndex].setCellEditable(cellIndex, editable);
    }

    public boolean isFixed(int row, int col) {
        return fixedCells[row][col];
    }

    public void setOnBoardChange(Runnable r) {
        this.onBoardChange = r;
    }

    public Runnable getOnBoardChange() {
        return onBoardChange;
    }
}