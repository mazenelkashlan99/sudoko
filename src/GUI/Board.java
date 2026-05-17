package Gui;

import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {
    private NineSquare[] gridSquares = new NineSquare[9];
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

    // Navigation methods (same as before, omitted for brevity)
    public void navigateLeft(NineSquare currentSquare, int cellIndex) { /* as before */ }
    public void navigateRight(NineSquare currentSquare, int cellIndex) { /* as before */ }
    public void navigateUp(NineSquare currentSquare, int cellIndex) { /* as before */ }
    public void navigateDown(NineSquare currentSquare, int cellIndex) { /* as before */ }

    public int[][] getBoardValues() {
        int[][] board = new int[9][9];
        for (int sq = 0; sq < 9; sq++) {
            NineSquare nineSquare = gridSquares[sq];
            for (int cell = 0; cell < 9; cell++) {
                JTextField tf = nineSquare.getCell(cell);
                String text = tf.getText();
                int value = text.isEmpty() ? 0 : Integer.parseInt(text);
                int row = (sq / 3) * 3 + (cell / 3);
                int col = (sq % 3) * 3 + (cell % 3);
                board[row][col] = value;
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
            }
        }
        if (onBoardChange != null) onBoardChange.run();
    }

    public void applyVerification(Boolean[][] result) {
        for (int sq = 0; sq < 9; sq++) {
            NineSquare ns = gridSquares[sq];
            for (int cell = 0; cell < 9; cell++) {
                int row = (sq / 3) * 3 + (cell / 3);
                int col = (sq % 3) * 3 + (cell % 3);
                boolean isValid = result[row][col];
                Color color = isValid ? new Color(144, 238, 144) : new Color(255, 99, 99);
                ns.setCellTextColor(cell, color);
            }
        }
    }

    public void setOnBoardChange(Runnable r){ 
        this.onBoardChange = r; 
    }

    public Runnable getOnBoardChange(){ 
        return onBoardChange; 
    }

    public NineSquare getNineSquare(int index) {
        return gridSquares[index];
    }

    public void setCellTextColor(int row, int col, Color color) {
        int squareIndex = (row / 3) * 3 + (col / 3);
        int cellIndex = ((row % 3) * 3) + (col % 3);
        gridSquares[squareIndex].setCellTextColor(cellIndex, color);
    }

    public void setCellBackground(int row, int col, Color color) {
        int squareIndex = (row / 3) * 3 + (col / 3);
        int cellIndex = ((row % 3) * 3) + (col % 3);
        gridSquares[squareIndex].setCellBackground(cellIndex, color);
    }

}