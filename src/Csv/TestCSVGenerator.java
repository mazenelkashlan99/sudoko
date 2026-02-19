package Csv;

import java.util.Arrays;

public class TestCSVGenerator {
    public static void main(){
        FileCSVConverter csv=new FileCSVConverter(Difficulty.MEDIUM);
        System.out.println(Arrays.deepToString(csv.generateGameArray()));
    }
}
