package Threads;

import java.util.ArrayList;

import Setup.AssignRoles;
import Setup.BoardBreakdown;
import Setup.Role;

public class SequentialThread extends ThreadAbstract{
    
    int [][] board;

    public SequentialThread(int [][] board){
        super(board);
    }

    public void divideRoles(){
        BoardBreakdown allRoles = new BoardBreakdown(this.board);
        ArrayList <int[]> rows=allRoles.getRows();
        ArrayList <int[]> columns=allRoles.getColumns();
        ArrayList <int[]> boxes=allRoles.getBoxes();
        AssignRoles boardRows=new AssignRoles(rows, Role.ROW);
        AssignRoles boardColumns=new AssignRoles(columns, Role.COLUMN);
        AssignRoles boardBoxes=new AssignRoles(boxes, Role.BOX);
        ArrayList <AssignRoles> allBoardRoles = new ArrayList<AssignRoles>();
        allBoardRoles.add(boardRows);
        allBoardRoles.add(boardColumns);
        allBoardRoles.add(boardBoxes);
        for (var roleArr : allBoardRoles){
            roleArr.CheckRoleValidity();
        }
    }



}