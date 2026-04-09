package Csv;

import java.io.IOException;
import java.util.Arrays;

public class TestCSVGenerator {
    public static void main(String[] args) throws IOException {  

        FileCSVConverter csv = new FileCSVConverter(Difficulty.HARD);
        
        int[][] initialBoard = csv.getgame2dedited();
        System.out.println("Initial board:");
        System.out.println(Arrays.deepToString(initialBoard));
        
        int[][] gameBoard = csv.generateGameArray();  
        System.out.println("Game board with empty cells:");
        System.out.println(Arrays.deepToString(gameBoard));
        
        csv.givenDataArray_whenConvertToCSV_thenOutputCreated();
        csv.listGameFiles();

    }
}