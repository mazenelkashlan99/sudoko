package Csv;

import java.util.*;

public class CSVGenerator {

    private Csv.RandomPairs randomPairs;
    private int[][] sudokuNumbers2d;

    public CSVGenerator(){
        randomPairs=new Csv.RandomPairs();
        sudokuNumbers2d=new int[9][9];
    }

    public int[][] generateValidBoard() {
        sudokuNumbers2d = generateRandomValidBoard();  
        return sudokuNumbers2d;
    }

    public int[][] generateRandomValidBoard() {
        
        int[][] board = new int[9][9];  // Start empty
        
        // Start recursive filling from cell (0,0)
        fillBoard(board, 0, 0);
        
        return board;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
    // Check row
        for (int c = 0; c < 9; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }
        
        // Check column
        for (int r = 0; r < 9; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }
        
        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[boxRow + r][boxCol + c] == num) {
                    return false;
                }
            }
        }
        
        // If no conflicts found, the number is valid
        return true;
    }

    private boolean fillBoard(int[][] board, int row, int col) {

        if (col == 9) {
            col = 0;
            row++;
        }
        
        if (row == 9) {
            return true;  
        }
        
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) numbers.add(i);
        Collections.shuffle(numbers);  
        
        for (int num : numbers) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;  
                
                if (fillBoard(board, row, col + 1)) {
                    return true; 
                }
                
                board[row][col] = 0;  
            }
        }
        
        return false;  
    }

    public int[][] replaceRandomPairs(int cellsWanted,int [][] board) {
        int pairsNeeded = (cellsWanted + 1) / 2;

        List<int[]> pairs = randomPairs.generateDistinctPairs(pairsNeeded);
        Set<Integer> uniqueCells = new HashSet<>();

        for (var pair : pairs) {
            uniqueCells.add(pair[0]);
            uniqueCells.add(pair[1]);
        }

        List<Integer> cellsList = new ArrayList<>(uniqueCells);
        Random rand = new Random();

        while (cellsList.size() > cellsWanted) {
            cellsList.remove(rand.nextInt(cellsList.size()));
        }

        while (cellsList.size() < cellsWanted) {
            int newCell = rand.nextInt(81);
            if (!cellsList.contains(newCell)) {
                cellsList.add(newCell);
            }
        }

        for (int cellIndex : cellsList) {
            board[cellIndex/9][cellIndex%9] = 0;
        }

        System.out.println("Requested: " + cellsWanted + ", Removed: " + cellsList.size());
        return board;
    }
}
