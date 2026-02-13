package GUI;

import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {

    private NineSquare[] gridSquares = new NineSquare[9];
    private Color[] bgs = {Color.BLACK, Color.BLACK};

    public Board() {
        setLayout(new GridLayout(3, 3, 3, 3));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < gridSquares.length; i++) {
            gridSquares[i] = new NineSquare(bgs[i % 2], i);
            add(gridSquares[i]);
        }
        setFocusTraversalPolicyProvider(true);
        setFocusCycleRoot(true);
    }

    // Navigation methods
    public void navigateLeft(NineSquare currentSquare, int cellIndex) {
        int squareIndex = currentSquare.getSquareIndex();
        int row = cellIndex / 3;
        int col = cellIndex % 3;

        if (col > 0) {
            // Move left within same square
            currentSquare.focusCell(cellIndex - 1);
        } else {
            // Move to previous square's rightmost column
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
            // Move right within same square
            currentSquare.focusCell(cellIndex + 1);
        } else {
            // Move to next square's leftmost column
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
            // Move up within same square
            currentSquare.focusCell(cellIndex - 3);
        } else {
            // Move to square above
            if (squareIndex / 3 > 0) {
                NineSquare aboveSquare = gridSquares[squareIndex - 3];
                aboveSquare.focusCell(2 * 3 + col); // Bottom row of above square
            }
        }
    }

    public void navigateDown(NineSquare currentSquare, int cellIndex) {
        int squareIndex = currentSquare.getSquareIndex();
        int row = cellIndex / 3;
        int col = cellIndex % 3;

        if (row < 2) {
            // Move down within same square
            currentSquare.focusCell(cellIndex + 3);
        } else {
            // Move to square below
            if (squareIndex / 3 < 2) {
                NineSquare belowSquare = gridSquares[squareIndex + 3];
                belowSquare.focusCell(col); // Top row of below square
            }
        }
    }

    // Helper method to get a specific cell across the entire board
    public JTextField getCell(int squareIndex, int cellIndex) {
        if (squareIndex >= 0 && squareIndex < gridSquares.length) {
            return gridSquares[squareIndex].getCell(cellIndex); // You'll need to add this getter
        }
        return null;
    }
}