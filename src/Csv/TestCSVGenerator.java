package Csv;

import java.util.Arrays;
import java.util.List;

public class TestCSVGenerator {
    public static void main(){
        CSVGenerator csv=new CSVGenerator(0);
        int [] nums=csv.generateNumbers();
        System.out.println(Arrays.toString(nums));
        int [][] nums2d= csv.convertOneDimensionArray();
        System.out.println(Arrays.deepToString(nums2d));
        int [][] edited=csv.replaceRandomPairs(10);
        System.out.println(Arrays.deepToString(edited));

        int zeroCount=0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (edited[i][j] == 0){
                    zeroCount++;
                };
            }
        }
        System.out.println("Total zeros in final board: " + zeroCount);
    }
}
