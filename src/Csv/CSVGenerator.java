package Csv;

import java.util.*;

public class CSVGenerator {

    private RandomPairs randomPairs;
    private int[] sudokuNumbers;
    private int[][] sudokuNumbers2d;
    private final int max=9;
    private final int min=1;

    public CSVGenerator(int cellsRemovedCount){
        randomPairs=new RandomPairs();
        sudokuNumbers=new int[81];
        sudokuNumbers2d=new int[9][9];
    }

    public int[] generateNumbers(){
        Random rand = new Random();
        for (int i = 0; i<sudokuNumbers.length; i++){
            int randomNum = rand.nextInt((max - min) + 1) + min;
            sudokuNumbers[i]=randomNum;
        }
        return sudokuNumbers;
    }

    public int[][] convertOneDimensionArray(){
        int z=0;
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                sudokuNumbers2d[i][j]=sudokuNumbers[z];
                z++;
            }
        }
        return sudokuNumbers2d;
    }

    public int[][] replaceRandomPairs(int cellsWanted) {
        int pairsNeeded = (cellsWanted + 1) / 2;

        List<int[]> pairs = randomPairs.generateDistinctPairs(pairsNeeded);
        Set<Integer> uniqueCells = new HashSet<>();

        for (var pair : pairs) {
            uniqueCells.add(pair[0]);
            uniqueCells.add(pair[1]);
        }

        List<Integer> cellsList = new ArrayList<>(uniqueCells);
        Random rand = new Random();

        // Remove extras if we have too many
        while (cellsList.size() > cellsWanted) {
            cellsList.remove(rand.nextInt(cellsList.size()));
        }

        // Add more if we have too few (rare, but just in case)
        while (cellsList.size() < cellsWanted) {
            int newCell = rand.nextInt(81);
            if (!cellsList.contains(newCell)) {
                cellsList.add(newCell);
            }
        }

        for (int cellIndex : cellsList) {
            sudokuNumbers2d[cellIndex/9][cellIndex%9] = 0;
        }

        System.out.println("Requested: " + cellsWanted + ", Removed: " + cellsList.size());
        return sudokuNumbers2d;
    }
}
