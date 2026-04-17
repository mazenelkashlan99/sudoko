package MVC.Verification;

import Setup.BoardBreakdown;
import Setup.ElementsWithRole;

import java.util.ArrayList;

public class GameState {

    private final int[][] game;
    private final BoardBreakdown board;
    private ElementsWithRole elements;
    ArrayList<int[]> allRoles;

    public GameState(int[][] game){
        this.game=game;
        elements=new ElementsWithRole();
        this.board=new BoardBreakdown(this.game);
        allRoles=new ArrayList<int[]>();
        allRoles.addAll(this.board.getRows());
        allRoles.addAll(this.board.getColumns());
        allRoles.addAll(this.board.getBoxes());
    }

    public int[][] getGame(){
        return this.game;
    }

    public boolean checkZeros(int [] arr){
        for (var i: arr){
            if (i==0){
                return true;
            }
        }
        return false;
    }

    public String getState(){

        for (var i : allRoles){
            if (!this.elements.checkElementsVariation(i)) {
                return "Invalid";
            } else if (checkZeros(i)) {
                return "Incomplete";
            }
        }

        return "Valid";
    }

}

