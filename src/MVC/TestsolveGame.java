package MVC;

import java.util.Arrays;

import MVC.Exceptions.InvalidGame;
import MVC.Verification.GameState;

public class TestsolveGame {
    public static void main(String[] args) throws InvalidGame {
        
        int [][] sudokuBoard={{5,3,0,6,7,8,0,1,2},
                            {6,7,2,1,9,5,3,4,8},
                            {1,9,8,3,4,2,5,6,7},
                            {8,5,9,7,6,1,4,2,3},
                            {4,2,6,8,5,3,7,9,1},
                            {7,1,3,9,2,4,8,5,6},
                            {9,6,0,5,3,7,2,8,4},
                            {2,8,7,4,1,9,6,3,5},
                            {3,4,5,2,8,0,1,7,0}};
        GameState game=new GameState(sudokuBoard);
        System.out.println(game.getState());
        SudokuController sudokuController=new SudokuController();
        System.out.println(Arrays.toString(sudokuController.solveGame(game)));
    }
}
