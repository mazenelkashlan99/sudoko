package Csv;

import java.lang.reflect.Array;
import java.util.Arrays;
import MVC.Verification.GameState;
public class TestRandomValidBoard {
    public static void main(String[] arg){
        int[][] test=CSVGenerator.generateRandomValidBoard();
        System.out.println(Arrays.deepToString(test));
        GameState game=new GameState(test);
        System.out.println(game.getState());
    }
}
