package Csv;

import java.io.IOException;
import java.util.Arrays;

public class TestCSVGenerator {
    public static void main() throws IOException{
    
        FileCSVConverter csv=new FileCSVConverter(Difficulty.EASY);
        int [][] validBoard=(csv.getgame2dedited());
        System.out.println(Arrays.deepToString(validBoard));
        System.out.println(Arrays.deepToString(csv.generateGameArray()));
        csv.givenDataArray_whenConvertToCSV_thenOutputCreated();
        
    }
}
