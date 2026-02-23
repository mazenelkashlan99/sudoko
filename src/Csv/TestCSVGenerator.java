package Csv;

import java.io.IOException;
import java.util.Arrays;

public class TestCSVGenerator {
    public static void main() throws IOException{
        CSVGenerator csv=new CSVGenerator();
        System.out.println(Arrays.deepToString(csv.generateValidBoard()));
        // FileCSVConverter csv=new FileCSVConverter(Difficulty.EASY);
        // System.out.println(Arrays.deepToString(csv.generateGameArray()));
        // csv.givenDataArray_whenConvertToCSV_thenOutputCreated();
    }
}
