package Verification;

import Setup.BoardBreakdown;
import Setup.GameCreation;

import java.util.ArrayList;

public class SourceSolutionVerification {
    private int[][] game;

    SourceSolutionVerification(int[][] game){
        this.game=game;
    }

    public void getGameState(){

        GameState gameStatus=new GameState(this.game);
        System.out.println(gameStatus.getState());

    }

    public static void main(String[] args){
        GameCreation gameCreation=new GameCreation("valid.csv");
        var game=gameCreation.getBoard();
        GameState ssv=new GameState(game);
        System.out.println(ssv.getState());
    }
}
